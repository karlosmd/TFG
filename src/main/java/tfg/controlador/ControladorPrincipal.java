package tfg.controlador;

import javax.validation.Valid;

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

import tfg.DTO.DTOAsignatura;
import tfg.DTO.DTOUsuario;
import tfg.modelo.Alumno;
import tfg.modelo.Asignatura;
import tfg.modelo.Mensaje;
import tfg.modelo.Profesor;
import tfg.modelo.Rol;
import tfg.servicioAplicacion.SAAsignatura;
import tfg.servicioAplicacion.SAUsuario;

@Controller
public class ControladorPrincipal {
	
	@Autowired	
	private SAUsuario saUsuario;
	@Autowired
	private SAAsignatura saAsignatura;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView mostrarLogin(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping(value="/registro", method = RequestMethod.GET)
	public ModelAndView mostrarRegistro(){
		ModelAndView modelAndView = new ModelAndView();
		DTOUsuario dtoUsuario = new DTOUsuario();
		
		modelAndView.addObject("dtoUsuario", dtoUsuario);		
		modelAndView.addObject("roles", Rol.values());
		modelAndView.setViewName("registro");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public ModelAndView registrarUsuario(@Valid @ModelAttribute("dtoUsuario") DTOUsuario dtoUsuario,
			BindingResult bindingResult,
			final RedirectAttributes redirectAttrs) {
		ModelAndView modelAndView = null;
		DTOUsuario userExists = saUsuario.leerUsuario(dtoUsuario.getEmail());

		if (!dtoUsuario.getPassword().equals(dtoUsuario.getConfirmarPassword())) {
			bindingResult.rejectValue("password", "error.dtoUsuario", "* Las contraseñas no coinciden");
		}
		if (userExists != null)
			bindingResult.rejectValue("email", "error.dtoUsuario", "* Ya existe un usuario con este e-mail");		
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("registro", bindingResult.getModel());
			modelAndView.addObject("dtoUsuario", dtoUsuario);
		}			
		else {
			saUsuario.crear(dtoUsuario);
			Mensaje mensaje = new Mensaje("Enhorabuena", "Se ha registrado con éxito. Inicie sesión con su correo electrónico", "verde");
			mensaje.setIcono("check_circle");
			redirectAttrs.addFlashAttribute("mensaje", mensaje);
			return new ModelAndView("redirect:/");
		}
		
		modelAndView.addObject("roles", Rol.values());
		
		return modelAndView;
	}
	
	@RequestMapping(value="/menu", method = RequestMethod.GET)
	public ModelAndView mostrarMenu(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("menu");
		return modelAndView;
	}
	
	@RequestMapping("/informacion")
    public String mostrarInformacion() {
        return "informacion";
    }
	
	@RequestMapping(value="/asignaturas", method = RequestMethod.GET)
	public ModelAndView mostrarAsignaturas(@ModelAttribute("usuario") DTOUsuario dtoUsuario){
		ModelAndView modelAndView = new ModelAndView();
		if(dtoUsuario.getRol() == Rol.Profesor)
			modelAndView.addObject("asignaturas", saAsignatura.leerAsignaturasProfesor(dtoUsuario.getId()));
		else
			modelAndView.addObject("asignaturas", saAsignatura.leerAsignaturasAlumno(dtoUsuario.getId()));
		modelAndView.addObject("dtoAsignatura", new DTOAsignatura());
		modelAndView.setViewName("asignaturas");
		return modelAndView;
	}
	
	@RequestMapping(value="/asignaturas/eliminar", method = RequestMethod.GET)
	public ModelAndView eliminarAsignatura(int id, final RedirectAttributes redirectAttrs){
		Asignatura asignatura = saAsignatura.leerPorId(id);
		saAsignatura.actualizarActivo(asignatura.getId(), 0);
		Mensaje mensaje = new Mensaje("Atención", "la asignatura " + asignatura.getNombre() + " se ha eliminado", "rojo");
		mensaje.setIcono("reply");
		mensaje.setEnlace("/asignaturas/deshacer-baja?id=" + asignatura.getId());
		mensaje.setTextoEnlace("Pulse aquí para Deshacer");
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		return new ModelAndView("redirect:/asignaturas");
	}
	
	@RequestMapping(value="/asignaturas/deshacer-baja", method = RequestMethod.GET)
	public ModelAndView deshacerBajaAsignatura(int id, final RedirectAttributes redirectAttrs){
		Asignatura asignatura = saAsignatura.leerPorId(id);
		saAsignatura.actualizarActivo(asignatura.getId(), 1);
		return new ModelAndView("redirect:/asignaturas");
	}
	
	@RequestMapping(value = "/asignaturas/insertar", method = RequestMethod.POST)
	public ModelAndView insertarAsignatura(@Valid @ModelAttribute("dtoAsignatura") DTOAsignatura dtoAsignatura,
			BindingResult bindingResult,
			@ModelAttribute("usuario") DTOUsuario dtoUsuario,
			final RedirectAttributes redirectAttrs) {
		
		if (!bindingResult.hasErrors()) {
			Profesor profesor = saUsuario.leerProfesor(dtoUsuario.getId());
			saAsignatura.crearAsignatura(dtoAsignatura, profesor);
			Mensaje mensaje = new Mensaje("Enhorabuena", "la asignatura " + dtoAsignatura.getNombre() + " se ha añadido con éxito", "verde");
			mensaje.setIcono("check_circle");
			redirectAttrs.addFlashAttribute("mensaje", mensaje);
		}
		
		return new ModelAndView("redirect:/asignaturas");
	}
	
	@RequestMapping(value = "/asignatura", method = RequestMethod.GET)
	public ModelAndView modificarAsignatura(int id) {
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("asignatura", saAsignatura.leerPorId(id));
		modelAndView.addObject("alumnosMatriculados", saUsuario.leerMatriculadosAsignatura(id));
		modelAndView.addObject("alumnosNoMatriculados", saUsuario.leerNoMatriculadosAsignatura(id));
		modelAndView.setViewName("asignatura");
		return modelAndView;
	}
	
	@RequestMapping(value = "/asignatura/{idAsignatura}/alta-alumno", method = RequestMethod.POST)
	public ModelAndView asignaturaAltaAlumno(@PathVariable("idAsignatura") int idAsignatura,
			@ModelAttribute("dtoUsuario") DTOUsuario dtoUsuario,
			final RedirectAttributes redirectAttrs) {
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Alumno alumno = saUsuario.leerAlumno(dtoUsuario.getEmail());		
		alumno.insertarAsignatura(asignatura);		
		saUsuario.sobrescribir(alumno);
		
		Mensaje mensaje = new Mensaje("Enhorabuena", "se ha añadido a " + alumno.getNombre() + " " + alumno.getApellidos() +
				" en la asignatura " + asignatura.getNombre(), "verde");
		mensaje.setIcono("check_circle");
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		
		return new ModelAndView("redirect:/asignatura?id=" + asignatura.getId());
	}
	
	@RequestMapping(value="/asignatura/baja-alumno", method = RequestMethod.GET)
	public ModelAndView AsignaturaBajaAlumno(int idAsignatura, int idAlumno, final RedirectAttributes redirectAttrs){
		Asignatura asignatura = saAsignatura.leerPorId(idAsignatura);
		Alumno alumno = saUsuario.leerAlumno(idAlumno);
		alumno.eliminarAsignatura(asignatura);
		saUsuario.sobrescribir(alumno);
		
		Mensaje mensaje = new Mensaje("Atención", "se ha eliminado a " + alumno.getNombre() + " " + alumno.getApellidos() +
				" de la asignatura " + asignatura.getNombre(), "rojo");
		mensaje.setIcono("reply");
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		
		return new ModelAndView("redirect:/asignatura?id=" + idAsignatura);
	}
	
	@ModelAttribute("usuario")
	public void insertarAtributos(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DTOUsuario dtoUsuario = saUsuario.leerUsuario(auth.getName());
		model.addAttribute("usuario", dtoUsuario);
	}
}
