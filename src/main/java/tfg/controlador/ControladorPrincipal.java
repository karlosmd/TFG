package tfg.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tfg.dto.DTOAlumno;
import tfg.dto.DTOAsignatura;
import tfg.dto.DTOProfesor;
import tfg.dto.DTOReto;
import tfg.dto.DTOUsuario;
import tfg.objetoNegocio.Alumno;
import tfg.objetoNegocio.Asignatura;
import tfg.objetoNegocio.Mensaje;
import tfg.objetoNegocio.Reto;
import tfg.objetoNegocio.Rol;
import tfg.objetoNegocio.Usuario;
import tfg.servicioAplicacion.SAAlumno;
import tfg.servicioAplicacion.SAAsignatura;
import tfg.servicioAplicacion.SAGamificacionREST;
import tfg.servicioAplicacion.SAParseExcel;
import tfg.servicioAplicacion.SAProfesor;
import tfg.servicioAplicacion.SAReto;
import tfg.servicioAplicacion.SAUsuario;

@Controller
public class ControladorPrincipal {
	
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
	private SAGamificacionREST saGamificacion;
	@Autowired	
	private SAParseExcel saParseExcel;

	@RequestMapping(value={"/", "/iniciar-sesion"}, method = RequestMethod.GET)
	public ModelAndView mostrarInicioSesion() throws InvalidFormatException, IOException{
		//List<DTOAlumno> listaAlumnos = saParseExcel.getResultadosAlumnos();
		ModelAndView modelAndView = new ModelAndView();
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
			final RedirectAttributes redirectAttrs) {
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
				saAlumno.crear(dtoAlumno);
				saGamificacion.crearUsuario(saAlumno.leer(dtoUsuario.getEmail()).getId());
			}
			else {
				DTOProfesor dtoProfesor = new DTOProfesor(dtoUsuario, departamento, despacho);
				saProfesor.crear(dtoProfesor);
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
	public ModelAndView mostrarMisAsignaturas(@ModelAttribute("usuario") Usuario usuario){
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
	public ModelAndView mostrarAsignatura(int id) {
		ModelAndView modelAndView = new ModelAndView();
		Asignatura asignatura = saAsignatura.leerPorId(id);
		List<Alumno> alumnosMatriculados = saAlumno.leerMatriculadosAsignatura(id);
		List<DTOAlumno> dtoAlumnosMatriculados = new ArrayList<DTOAlumno>();
		modelAndView.addObject("asignatura", asignatura);
		modelAndView.addObject("dtoReto", new DTOReto());
		
		for(Alumno alumno : alumnosMatriculados) {
			DTOAlumno dtoAlumno = new DTOAlumno();
			dtoAlumno = alumno.toDTOAlumno();
			dtoAlumno.setInsignias(saGamificacion.getInsignias(id, alumno.getId()));
			dtoAlumnosMatriculados.add(dtoAlumno);
		}		
		modelAndView.addObject("alumnosMatriculados", dtoAlumnosMatriculados);
		modelAndView.addObject("alumnosNoMatriculados", saAlumno.leerNoMatriculadosAsignatura(id));
		modelAndView.addObject("retos", saReto.leerPorAsignatura(asignatura));
		
		modelAndView.setViewName("asignatura");
		return modelAndView;
	}
	
	@RequestMapping(value = "/asignatura/insertar", method = RequestMethod.POST)
	public ModelAndView insertarAsignatura(@Valid @ModelAttribute("dtoAsignatura") DTOAsignatura dtoAsignatura,
			BindingResult bindingResult,
			int idProfesor,
			final RedirectAttributes redirectAttrs) {
		
		if (!bindingResult.hasErrors()) {
			saAsignatura.crearAsignatura(dtoAsignatura, saProfesor.leer(idProfesor));
			Mensaje mensaje = new Mensaje("Enhorabuena", "la asignatura " + dtoAsignatura.getNombre() + " se ha añadido con éxito", "verde");
			mensaje.setIcono("check_circle");
			redirectAttrs.addFlashAttribute("mensaje", mensaje);
		}
		
		return new ModelAndView("redirect:/mis-asignaturas");
	}
	
	@RequestMapping(value="/asignatura/eliminar", method = RequestMethod.POST)
	public ModelAndView eliminarAsignatura(int id, final RedirectAttributes redirectAttrs){
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
	public ModelAndView deshacerEliminarAsignatura(int id, final RedirectAttributes redirectAttrs){
		Asignatura asignatura = saAsignatura.leerPorId(id);
		saAsignatura.actualizarActivo(asignatura.getId(), 1);
		return new ModelAndView("redirect:/mis-asignaturas");
	}
	
	@RequestMapping(value = "/asignatura/{idAsignatura}/alta-alumno", method = RequestMethod.POST)
	public ModelAndView asignaturaAltaAlumno(@PathVariable("idAsignatura") int idAsignatura,
			int idAlumno,
			final RedirectAttributes redirectAttrs) {
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Alumno alumno = saAlumno.leer(idAlumno);		
		alumno.insertarAsignatura(asignatura);		
		saAlumno.sobrescribir(alumno);
		
		Mensaje mensaje = new Mensaje("Enhorabuena", "se ha añadido a " + alumno.getNombre() + " " + alumno.getApellidos() +
				" en la asignatura " + asignatura.getNombre(), "verde");
		mensaje.setIcono("check_circle");
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		
		return new ModelAndView("redirect:/asignatura?id=" + asignatura.getId());
	}
	
	@RequestMapping(value="/asignatura/baja-alumno", method = RequestMethod.POST)
	public ModelAndView AsignaturaBajaAlumno(int idAsignatura, int idAlumno, final RedirectAttributes redirectAttrs){
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
		
		return new ModelAndView("redirect:/asignatura?id=" + idAsignatura);
	}
	
	@RequestMapping(value="/asignatura/deshacer-baja-alumno", method = RequestMethod.POST)
	public ModelAndView AsignaturaDeshacerBajaAlumno(int idAsignatura, int idAlumno){
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Alumno alumno = saAlumno.leer(idAlumno);
		alumno.insertarAsignatura(asignatura);
		saAlumno.sobrescribir(alumno);
		
		return new ModelAndView("redirect:/asignatura?id=" + idAsignatura);
	}
	
	@RequestMapping(value = "/asignatura/{idAsignatura}/insertar-reto", method = RequestMethod.POST)
	public ModelAndView asignaturaInsertarReto(@PathVariable("idAsignatura") int idAsignatura,
			@ModelAttribute("dtoReto") DTOReto dtoReto,
			final RedirectAttributes redirectAttrs) {
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);	
		saReto.crearReto(dtoReto, asignatura);
		
		Mensaje mensaje = new Mensaje("Enhorabuena", "se ha añadido el reto '" + dtoReto.getNombre() +
				"' a la asignatura " + asignatura.getNombre(), "verde");
		mensaje.setIcono("check_circle");
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		
		return new ModelAndView("redirect:/asignatura?id=" + asignatura.getId());
	}
	
	@RequestMapping(value="/asignatura/eliminar-reto", method = RequestMethod.POST)
	public ModelAndView AsignaturaEliminarReto(int idReto, int idAsignatura, final RedirectAttributes redirectAttrs){
		Reto reto = saReto.leerPorId(idReto);
		saReto.actualizarActivo(reto.getId(), 0);
		Mensaje mensaje = new Mensaje("Atención", "el reto '" + reto.getNombre() + "' se ha eliminado", "rojo");
		mensaje.setIcono("reply");
		
		Map<String, Integer> campos = new HashMap<String, Integer>();
		campos.put("idAsignatura", idAsignatura);
		campos.put("idReto", reto.getId());
		mensaje.definirPeticion("/asignatura/deshacer-eliminar-reto", campos, "Pulse aquí para Deshacer");
		
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		return new ModelAndView("redirect:/asignatura?id=" + idAsignatura);
	}
	
	@RequestMapping(value="/asignatura/deshacer-eliminar-reto", method = RequestMethod.POST)
	public ModelAndView AsignaturaDeshacerEliminarReto(int idReto, int idAsignatura){
		Reto reto = saReto.leerPorId(idReto);
		saReto.actualizarActivo(reto.getId(), 1);
		return new ModelAndView("redirect:/asignatura?id=" + idAsignatura);
	}
	
	@ModelAttribute("usuario")
	public void atributosDelModelo(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = saUsuario.leer(auth.getName());
		model.addAttribute("usuario", usuario);
	}
}
