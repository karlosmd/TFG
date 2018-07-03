<<<<<<< HEAD
package tfg.controlador;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import tfg.modelo.Reto;
import tfg.modelo.Usuario;
import tfg.servicioAplicacion.SAGamificacion;
import tfg.servicioAplicacion.SAReto;
import tfg.servicioAplicacion.SAUsuario;

@Controller
@RequestMapping("/api")
public class TFGControladorAPI {
	@Autowired
	private SAUsuario saUsuario;
	@Autowired
	private SAReto saReto;
	@Autowired
	private SAGamificacion saGamificacion;

	@RequestMapping(value = "/reto/{idReto}/activar-disponibilidad", method = RequestMethod.POST)
	@ResponseBody
	public void activarDisponibilidad(@PathVariable("idReto") int idReto) {
		Reto reto = saReto.leerPorId(idReto);
		reto.setDisponible(true);
		saReto.modificarReto(reto);
	}
	
	@RequestMapping(value = "/reto/{idReto}/cancelar-disponibilidad", method = RequestMethod.POST)
	@ResponseBody
	public void cancelarDisponibilidad(@PathVariable("idReto") int idReto) {	
		Reto reto = saReto.leerPorId(idReto);
		reto.setDisponible(false);
		saReto.modificarReto(reto);
	}
	
	@RequestMapping(value = "/reto/{idReto}/importar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> importar(@PathVariable("idReto") int idReto, @RequestBody String peticion) {
		Reto reto = saReto.leerPorId(idReto);
		try {
			saGamificacion.exportarResultados(reto, peticion);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.SC_OK).body(null);
	}
	
	@RequestMapping(value = "/reto/{idReto}/ir-a-asignatura", method = RequestMethod.GET)
	public ModelAndView irAAsignatura(@PathVariable("idReto") int idReto) {	
		Reto reto = saReto.leerPorId(idReto);		
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + reto.getAsignatura().getId());
	}
	
	@RequestMapping(value = "/comprobar-usuario", method = RequestMethod.GET)
	@ResponseBody
	public String comprobarUsuario(int idUsuario, String token) {
		Map<String, String> respuesta = new HashMap<>();
		Gson gson = new Gson(); 
		boolean usuarioVerificado = false;
		Usuario usuario = saUsuario.leer(idUsuario);
		
		if(usuario != null) {
			if(usuario.getToken().equals(token)) {
				usuarioVerificado = true;
			}
		}
		
		respuesta.put("verificado", Boolean.toString(usuarioVerificado));
		respuesta.put("rol", usuario.getRol().toString());
		
		return gson.toJson(respuesta);
	}
}
=======
package tfg.controlador;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tfg.modelo.Reto;
import tfg.modelo.Usuario;
import tfg.servicioAplicacion.SAGamificacion;
import tfg.servicioAplicacion.SAReto;
import tfg.servicioAplicacion.SAUsuario;

@Controller
@RequestMapping("/api")
public class TFGControladorAPI {
	@Autowired
	private SAUsuario saUsuario;
	@Autowired
	private SAReto saReto;
	@Autowired
	private SAGamificacion saGamificacion;

	@RequestMapping(value = "/reto/{idReto}/activar-disponibilidad", method = RequestMethod.POST)
	@ResponseBody
	public void activarDisponibilidad(@PathVariable("idReto") int idReto) {
		Reto reto = saReto.leerPorId(idReto);
		reto.setDisponible(true);
		saReto.modificarReto(reto);
	}
	
	@RequestMapping(value = "/reto/{idReto}/cancelar-disponibilidad", method = RequestMethod.POST)
	@ResponseBody
	public void cancelarDisponibilidad(@PathVariable("idReto") int idReto) {	
		Reto reto = saReto.leerPorId(idReto);
		reto.setDisponible(false);
		saReto.modificarReto(reto);
	}
	
	@RequestMapping(value = "/reto/{idReto}/importar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> importar(@PathVariable("idReto") int idReto, @RequestBody String peticion) {
		Reto reto = saReto.leerPorId(idReto);
		try {
			saGamificacion.exportarResultados(reto, peticion);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.SC_OK).body(null);
	}
	
	@RequestMapping(value = "/reto/{idReto}/ir-a-asignatura", method = RequestMethod.GET)
	public ModelAndView irAAsignatura(@PathVariable("idReto") int idReto) {	
		Reto reto = saReto.leerPorId(idReto);		
		return new ModelAndView("redirect:/asignatura?idAsignatura=" + reto.getAsignatura().getId());
	}
	
	@RequestMapping(value = "/comprobar-usuario", method = RequestMethod.GET)
	@ResponseBody
	public String comprobarUsuario(int idUsuario, String token) {		
		boolean usuarioVerificado = false;
		Usuario usuario = saUsuario.leer(idUsuario);
		
		if(usuario != null) {
			if(usuario.getToken().equals(token)) {
				usuarioVerificado = true;
			}
		}
		return Boolean.toString(usuarioVerificado);
	}
}
>>>>>>> pr/4
