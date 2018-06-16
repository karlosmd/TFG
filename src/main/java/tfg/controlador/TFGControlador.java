package tfg.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import tfg.dto.DTOAlumno;
import tfg.dto.DTOAlumnoComp;
import tfg.dto.DTOAsignatura;
import tfg.dto.DTOInsignia;
import tfg.dto.DTOProfesor;
import tfg.dto.DTOReto;
import tfg.dto.DTOUsuario;
import tfg.excepcion.ExcepcionPeticionHTTP;
import tfg.modelo.Alumno;
import tfg.modelo.Asignatura;
import tfg.modelo.Insignia;
import tfg.modelo.Profesor;
import tfg.modelo.Mensaje;
import tfg.modelo.Reto;
import tfg.modelo.Rol;
import tfg.modelo.Usuario;
import tfg.modelo.Variable;
import tfg.servicioAplicacion.SAAlumno;
import tfg.servicioAplicacion.SAAlumnoAsignatura;
import tfg.servicioAplicacion.SAAsignatura;
import tfg.servicioAplicacion.SAGamificacion;
import tfg.servicioAplicacion.SAProfesor;
import tfg.servicioAplicacion.SAReto;
import tfg.servicioAplicacion.SAUsuario;

@Controller
public class TFGControlador {
	
	@Autowired	
	private SAUsuario saUsuario;
	@Autowired	
	private SAAlumno saAlumno;
	@Autowired	
	private SAProfesor saProfesor;
	@Autowired
	private SAAsignatura saAsignatura;
	@Autowired
	private SAReto saReto;
	@Autowired
	private SAAlumnoAsignatura saAlumnoAsignatura;
	@Autowired	
	private SAGamificacion saGamificacion;
	

	@RequestMapping(value={"/", "/iniciar-sesion"}, method = RequestMethod.GET)
	public ModelAndView mostrarInicioSesion() throws InvalidFormatException, IOException{
		ModelAndView modelAndView = new ModelAndView();
		try {
			saGamificacion.iniciarSesionGamificacion();
		} catch (Exception e) {
			Mensaje mensaje = new Mensaje("Error", "No se ha podido conectar con el Motor de Gamificación. "
					+ "La aplicación podría no funcionar correctamente", "rojo");
			mensaje.setIcono("block");
			modelAndView.addObject("mensaje", mensaje);
		}
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping(value="/crear-cuenta", method = RequestMethod.GET)
	public ModelAndView mostrarCrearCuenta(){
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.addObject("dtoUsuario", new DTOUsuario());
		modelAndView.setViewName("crearCuenta");
		return modelAndView;
	}
	
	@RequestMapping(value = "/crear-cuenta", method = RequestMethod.POST)
	public ModelAndView crearAlumno(@Valid @ModelAttribute("dtoUsuario") DTOUsuario dtoUsuario,
			String titulacion,
			String departamento,
			int despacho,
			BindingResult bindingResult,
			final RedirectAttributes redirectAttrs) throws ClientProtocolException, IOException {
		ModelAndView modelAndView = null;
		Usuario usuario = saUsuario.leer(dtoUsuario.getEmail());

		if (!dtoUsuario.getPassword().equals(dtoUsuario.getConfirmarPassword())) {
			bindingResult.rejectValue("password", "error.dtoAlumno", "* Las contraseñas no coinciden");
		}
		if (usuario != null)
			bindingResult.rejectValue("email", "error.dtoAlumno", "* Ya existe un usuario con este e-mail");		
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("crearCuenta", bindingResult.getModel());
			modelAndView.addObject("dtoUsuario", new DTOUsuario());
		}			
		else {
			if(dtoUsuario.getRol()==Rol.Alumno) {
				DTOAlumno dtoAlumno = new DTOAlumno(dtoUsuario, titulacion);
				Alumno alumno = Alumno.toAlumno(dtoAlumno);
				try {					
					saGamificacion.crearUsuario(alumno); // Lo guardamos en el Motor de Gamificación
					saAlumno.crear(alumno); // Lo guardamos en nuestro sistema	
				} catch (Exception e) {
					Mensaje mensaje = new Mensaje("Error", "No se ha podido crear correctamente el alumno. "
							+ "Operación cancelada", "rojo");
					mensaje.setIcono("block");
					redirectAttrs.addFlashAttribute("mensaje", mensaje);
					return new ModelAndView("redirect:/");
				}
			}
			else {
				DTOProfesor dtoProfesor = new DTOProfesor(dtoUsuario, departamento, despacho);
				Profesor profesor = Profesor.toProfesor(dtoProfesor);
				saProfesor.crear(profesor);
			}
			Mensaje mensaje = new Mensaje("Enhorabuena", "Se ha registrado con éxito. Inicie sesión con su correo electrónico", "verde");
			mensaje.setIcono("check_circle");
			redirectAttrs.addFlashAttribute("mensaje", mensaje);
			return new ModelAndView("redirect:/");
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/acerca-de")
	public String mostrarAcercaDe() {
		return "acercaDe";
	}
	
	@RequestMapping(value="/mis-asignaturas", method = RequestMethod.GET)
	public ModelAndView mostrarMisAsignaturas(@ModelAttribute("usuario") Usuario usuario) 
			throws ClientProtocolException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		
		if(usuario.getRol() == Rol.Profesor)
			modelAndView.addObject("asignaturas", saAsignatura.leerAsignaturasProfesor(usuario.getId()));
		else
			modelAndView.addObject("asignaturas", saAsignatura.leerAsignaturasAlumno(usuario.getId()));
		
		modelAndView.addObject("dtoAsignatura", new DTOAsignatura());
		modelAndView.setViewName("misAsignaturas");
		return modelAndView;
	}
	
	@RequestMapping(value = "/asignatura", method = RequestMethod.GET)
	public ModelAndView mostrarAsignatura(@ModelAttribute("usuario") Usuario usuario,
			@RequestParam("idAsignatura") int idAsignatura) 
					throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		ModelAndView modelAndView = new ModelAndView();
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		List<Alumno> alumnosMatriculados = saAlumno.leerMatriculadosAsignatura(idAsignatura);
		List<Reto> retos = saReto.leerPorAsignatura(asignatura);
		List<DTOReto> dtoRetos = new ArrayList<>();
		List<Insignia> listaInsignias = new ArrayList<>();
		List<DTOInsignia> dtoInsignia = new ArrayList<>();
		Set<DTOAlumno> dtoAlumnosMatriculados = new TreeSet<DTOAlumno>(new DTOAlumnoComp());
		modelAndView.addObject("asignatura", asignatura);
		modelAndView.addObject("dtoReto", new DTOReto());
		modelAndView.addObject("dtoInsignia", new DTOInsignia());
		
		for(Alumno alumno : alumnosMatriculados) {
			DTOAlumno dtoAlumno = new DTOAlumno();
			dtoAlumno = alumno.toDTOAlumno();
			listaInsignias = new ArrayList<>();
			saGamificacion.comprobarReward(asignatura, alumno, listaInsignias);
			dtoAlumno.setInsignias(listaInsignias);
			Variable variable[] = Variable.values();//Coge todas las variables en un array
			for (Variable var : variable)  //Crea un Ranking por cada variable que tenemos
	        {
				saGamificacion.cogerRankingVersionMejorada(asignatura, var, alumno);
				if(var == Variable.Puntuacion)
					dtoAlumno.setPuntuacion(alumno.getValor());
				else if(var == Variable.TiempoMedio)
					dtoAlumno.setTiempomedio(alumno.getValor());
				else if(var == Variable.PorcentajeAciertos)
					dtoAlumno.setPorcentaje(alumno.getValor());
				alumno.setValor(0);
	        }
			dtoAlumnosMatriculados.add(dtoAlumno);
		}
		
		for(int i = 0; i < retos.size(); i++) {
			DTOReto dtoReto = DTOReto.toDTOReto(retos.get(i));
			String enlace = retos.get(i).generarEnlace();
			dtoReto.setEnlace(enlace);
			if(usuario.getRol() == Rol.Alumno && retos.get(i).isDisponible()) {
				dtoReto.setEnlace(enlace + "/insertar-nick?idUsuario=" + usuario.getId() +
						"&token=" + usuario.getToken());
			}
			else if(usuario.getRol() == Rol.Profesor && retos.get(i).isDisponible()) {
				dtoReto.setEnlace(enlace + "/sala-de-espera");
			}
			
			dtoRetos.add(dtoReto);
		}
		
		DTOInsignia dtoInsignias = new DTOInsignia();
		listaInsignias = new ArrayList<>();
		saGamificacion.cogerAchievement(listaInsignias, asignatura);
		dtoInsignias.setInsignias(listaInsignias);
		dtoInsignia.add(dtoInsignias);
	
		modelAndView.addObject("alumnosMatriculados", dtoAlumnosMatriculados);
		modelAndView.addObject("alumnosNoMatriculados", saAlumno.leerNoMatriculadosAsignatura(idAsignatura));
		modelAndView.addObject("retos", dtoRetos);
		modelAndView.addObject("insignias", dtoInsignia);
		
		modelAndView.setViewName("asignatura");
		return modelAndView;
	}
	
	@RequestMapping(value = "/asignatura/insertar", method = RequestMethod.POST)
	public ModelAndView insertarAsignatura(@Valid @ModelAttribute("dtoAsignatura") DTOAsignatura dtoAsignatura,
			BindingResult bindingResult,
			int idProfesor,
			final RedirectAttributes redirectAttrs) throws ClientProtocolException, IOException {
		
		if (!bindingResult.hasErrors()) {
			Asignatura asignatura = Asignatura.toAsignatura(dtoAsignatura);
			asignatura.setProfesor(saProfesor.leer(idProfesor));
			try {					
				saGamificacion.crearJuego(asignatura); // Lo guardamos como juego  en el Motor de Gamificación
				saAsignatura.crearAsignatura(asignatura); // Lo guardamos en nuestro sistema
				Variable variable[] = Variable.values();//Coge todas las variables en un array
				for (Variable var : variable)  //Crea un Ranking por cada variable que tenemos
		        {
					saGamificacion.crearRanking(asignatura, var); 
		        }
			} catch (Exception e) {
				Mensaje mensaje = new Mensaje("Error", "No se ha podido crear correctamente la asignatura. "
							+ "Operación cancelada", "rojo");
				
				mensaje.setIcono("block");
				redirectAttrs.addFlashAttribute("mensaje", mensaje);
				return new ModelAndView("redirect:/mis-asignaturas");
			}
			
			Mensaje mensaje = new Mensaje("Enhorabuena", "la asignatura " + dtoAsignatura.getNombre() +
										" se ha añadido con éxito", "verde");
			
			mensaje.setIcono("check_circle");
			redirectAttrs.addFlashAttribute("mensaje", mensaje);
		}
		
		return new ModelAndView("redirect:/mis-asignaturas");
	}
	
	@RequestMapping(value="/asignatura/eliminar", method = RequestMethod.POST)
	public ModelAndView eliminarAsignatura(int id, final RedirectAttributes redirectAttrs) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		Asignatura asignatura = saAsignatura.leerPorId(id);
		saAsignatura.actualizarActivo(asignatura.getId(), 0);
		Mensaje mensaje = new Mensaje("Atención", "la asignatura " + asignatura.getNombre() + " se ha eliminado", "rojo");
		mensaje.setIcono("reply");
		
		Map<String, Integer> campos = new HashMap<String, Integer>();
		campos.put("id", asignatura.getId());
		mensaje.definirPeticion("/asignatura/deshacer-eliminar", campos, "Pulse aquí para Deshacer");

		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		return new ModelAndView("redirect:/mis-asignaturas");
	}
	
	@RequestMapping(value="/asignatura/deshacer-eliminar", method = RequestMethod.POST)
	public ModelAndView deshacerEliminarAsignatura(int id, final RedirectAttributes redirectAttrs) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		Asignatura asignatura = saAsignatura.leerPorId(id);
		saAsignatura.actualizarActivo(asignatura.getId(), 1);
		return new ModelAndView("redirect:/mis-asignaturas");
	}
	
	@RequestMapping(value = "/asignatura/{idAsignatura}/alta-alumno", method = RequestMethod.POST)
	public ModelAndView asignaturaAltaAlumno(@PathVariable("idAsignatura") int idAsignatura,
			int idAlumno,
			final RedirectAttributes redirectAttrs) throws Exception {
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Alumno alumno = saAlumno.leer(idAlumno);		
		alumno.insertarAsignatura(asignatura);
		
		try {		
			Variable variable[] = Variable.values();//Coge todas las variables en un array
			for (Variable var : variable)  //Crea un Ranking por cada variable que tenemos
	        {
				saGamificacion.inicializarRanking(asignatura, alumno, var, 0);				
	        }
			saAlumno.sobrescribir(alumno); // Lo guardamos en nuestro sistema
		} catch (Exception e) {
			Mensaje mensaje = new Mensaje("Error", "No se ha podido insertar correctamente a " + alumno.getNombre() + " " + alumno.getApellidos() + 
					" en la asignatura " + asignatura.getNombre() + ". Operación cancelada", "rojo");
			mensaje.setIcono("block");
			redirectAttrs.addFlashAttribute("mensaje", mensaje);
			return new ModelAndView("redirect:/asignatura?idAsignatura=" + asignatura.getId());
		}		
		
		Mensaje mensaje = new Mensaje("Enhorabuena", "se ha añadido a " + alumno.getNombre() + " " + alumno.getApellidos() +
				" en la asignatura " + asignatura.getNombre(), "verde");
		mensaje.setIcono("check_circle");
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + asignatura.getId());
	}
	
	@RequestMapping(value="/asignatura/baja-alumno", method = RequestMethod.POST)
	public ModelAndView AsignaturaBajaAlumno(int idAsignatura, int idAlumno, final RedirectAttributes redirectAttrs){
		//Y después de la aplicación
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Alumno alumno = saAlumno.leer(idAlumno);
		alumno.eliminarAsignatura(asignatura);
		saAlumno.sobrescribir(alumno);		
		
		Mensaje mensaje = new Mensaje("Atención", "se ha eliminado a " + alumno.getNombre() + " " + alumno.getApellidos() +
				" de la asignatura " + asignatura.getNombre(), "rojo");
		mensaje.setIcono("reply");
		
		Map<String, Integer> campos = new HashMap<String, Integer>();
		campos.put("idAsignatura", asignatura.getId());
		campos.put("idAlumno", alumno.getId());
		mensaje.definirPeticion("/asignatura/deshacer-baja-alumno", campos, "Pulse aquí para Deshacer");
		
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + idAsignatura);
	}
	
	@RequestMapping(value="/asignatura/deshacer-baja-alumno", method = RequestMethod.POST)
	public ModelAndView AsignaturaDeshacerBajaAlumno(int idAsignatura, int idAlumno) throws Exception{
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Alumno alumno = saAlumno.leer(idAlumno);
		alumno.insertarAsignatura(asignatura);
		saAlumno.sobrescribir(alumno);
		
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + idAsignatura);
	}
	
	@RequestMapping(value = "/asignatura/{idAsignatura}/insertar-reto", method = RequestMethod.POST)
	public ModelAndView asignaturaInsertarReto(@PathVariable("idAsignatura") int idAsignatura,
			@ModelAttribute("dtoReto") DTOReto dtoReto,
			final RedirectAttributes redirectAttrs) throws ClientProtocolException, IOException {
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Reto reto = Reto.toReto(dtoReto);
		reto.setAsignatura(asignatura);
		
		try {					
			saReto.crearReto(reto); // Lo guardamos en nuestro sistema
		} catch (Exception e) {
			Mensaje mensaje = new Mensaje("Error", "No se ha podido crear correctamente el reto " +
								reto.getNombre() + ". Operación cancelada", "rojo");
			mensaje.setIcono("block");
			redirectAttrs.addFlashAttribute("mensaje", mensaje);
			return new ModelAndView("redirect:/asignatura?idAsignatura=" + asignatura.getId());
		}
		
		Mensaje mensaje = new Mensaje("Enhorabuena", "se ha añadido el reto '" + reto.getNombre() +
				"' a la asignatura " + asignatura.getNombre(), "verde");
		mensaje.setIcono("check_circle");
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + asignatura.getId());
	}
	
	
	@RequestMapping(value = "/asignatura/{idAsignatura}/insertar-insignia", method = RequestMethod.POST)
	public ModelAndView asignaturaInsertarinsignia(@PathVariable("idAsignatura") int idAsignatura,
			@ModelAttribute("dtoInsignia") DTOInsignia dtoInsignia, BindingResult bindingResult,
			final RedirectAttributes redirectAttrs) throws ClientProtocolException, IOException {
		
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Insignia insignia = Insignia.toInsignia(dtoInsignia);
		if (!bindingResult.hasErrors()) {
			try {					
				saGamificacion.crearAchievement(insignia, asignatura); // Lo guardamos en el Motor de Gamificación
			} catch (Exception e) {
				Mensaje mensaje = new Mensaje("Error", "No se ha podido crear correctamente el insignia " +
						insignia.getNombre() + ". Operación cancelada", "rojo");
				
				mensaje.setIcono("block");
				redirectAttrs.addFlashAttribute("mensaje", mensaje);
				return new ModelAndView("redirect:/asignatura?idAsignatura=" + asignatura.getId());
			}
			
			Mensaje mensaje = new Mensaje("Enhorabuena", "se ha añadido la insignia '" + insignia.getNombre() +
					"' a la asignatura " + asignatura.getNombre(), "verde");
			mensaje.setIcono("check_circle");
			redirectAttrs.addFlashAttribute("mensaje", mensaje);
		}
		
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + asignatura.getId());
	}
	
	@RequestMapping(value="/asignatura/eliminar-reto", method = RequestMethod.POST)
	public ModelAndView AsignaturaEliminarReto(int idReto, int idAsignatura, final RedirectAttributes redirectAttrs) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		Reto reto = saReto.leerPorId(idReto);
		reto.setActivo(false);
		saReto.modificarReto(reto);
		Mensaje mensaje = new Mensaje("Atención", "el reto '" + reto.getNombre() + "' se ha eliminado", "rojo");
		mensaje.setIcono("reply");
		
		Map<String, Integer> campos = new HashMap<String, Integer>();
		campos.put("idAsignatura", idAsignatura);
		campos.put("idReto", reto.getId());
		mensaje.definirPeticion("/asignatura/deshacer-eliminar-reto", campos, "Pulse aquí para Deshacer");
		
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + idAsignatura);
	}
	
	@RequestMapping(value="/asignatura/deshacer-eliminar-reto", method = RequestMethod.POST)
	public ModelAndView AsignaturaDeshacerEliminarReto(int idReto, int idAsignatura){
		Reto reto = saReto.leerPorId(idReto);
		reto.setActivo(true);
		saReto.modificarReto(reto);
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + idAsignatura);
	}
	
	
	@ModelAttribute("usuario")
	public void atributosDelModelo(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = saUsuario.leer(auth.getName());
		model.addAttribute("usuario", usuario);
	}
}
