package tfg.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tfg.DTO.DTOUsuario;
import tfg.modelo.Usuario;
import tfg.servicio.ServicioRol;
import tfg.servicio.ServicioUsuario;

@Controller
public class ControladorPrincipal {
	
	@Autowired	
	private ServicioUsuario servicioUsuario;
	@Autowired
	private ServicioRol servicioRol;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registro", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		DTOUsuario dtoUsuario = new DTOUsuario();
		modelAndView.addObject("dtoUsuario", dtoUsuario);		
		modelAndView.addObject("roles", servicioRol.findAll());
		modelAndView.addObject("usuarioRegistrado", false);
		modelAndView.setViewName("registro");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid DTOUsuario dtoUsuario, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Usuario userExists = servicioUsuario.findUserByEmail(dtoUsuario.getEmail());

		if (!dtoUsuario.getPassword().equals(dtoUsuario.getConfirmarPassword())) {
			bindingResult.rejectValue("password", "error.dtoUsuario", "Las contraseñas no coinciden");
		}
		if (userExists != null)
			bindingResult.rejectValue("email", "error.dtoUsuario", "Ya existe un usuario con este e-mail");		
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("dtoUsuario", dtoUsuario);
		}			
		else {
			servicioUsuario.guardarUsuario(dtoUsuario);
			modelAndView.addObject("successMessage", "Usuario has been registered successfully");
			modelAndView.addObject("dtoUsuario", new DTOUsuario());
			modelAndView.addObject("usuarioRegistrado", true);
		}
		
		modelAndView.addObject("roles", servicioRol.findAll());
		modelAndView.setViewName("registro");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = servicioUsuario.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + usuario.getNombre() + " " + usuario.getApellidos() + " (" + usuario.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
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
	
	@ModelAttribute
	public void addAttributes(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = servicioUsuario.findUserByEmail(auth.getName());
		model.addAttribute("usuario", usuario);
	}
}
