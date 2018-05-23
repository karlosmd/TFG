package tfg.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tfg.objetoNegocio.Reto;
import tfg.servicioAplicacion.SAReto;

@Controller
@RequestMapping("/api")
public class TFGControladorAPI {

	@Autowired
	private SAReto saReto;

	@RequestMapping(value = "/reto/{idReto}/cambiar-disponibilidad", method = RequestMethod.POST)
	@ResponseBody
	public void cambiarDisponibilidad(@PathVariable("idReto") int idReto) {
		Reto reto = saReto.leerPorId(idReto);
		reto.setDisponible(!reto.isDisponible());
		saReto.modificarReto(reto);
	}
	
	@RequestMapping(value = "/reto/{idReto}/ir-a-asignatura", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView irAAsignatura(@PathVariable("idReto") int idReto) {	
		Reto reto = saReto.leerPorId(idReto);
		reto.setDisponible(!reto.isDisponible());
		saReto.modificarReto(reto);
		
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + reto.getAsignatura().getId());
	}
}
