package tfg.servicioAplicacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tfg.objetoNegocio.Categoria;
import tfg.objetoNegocio.Insignia;

@Service("saGamificacion")
public class SAGamificacionRESTImp implements SAGamificacionREST{

	@Override
	public void crearUsuario(int idUsuario) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String url = "http://localhost:8081/add_or_update_user/";

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		//map.add("email", "first.last@example.com");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity( url + idUsuario, request , String.class );
		System.out.println(response);
	}

	@Override
	public List<Insignia> getInsignias(int idAsignatura, int idUsuario) {
		List<Insignia> insignias = new ArrayList<Insignia>();

		Map<String, String> vars = new HashMap<String, String>();
		vars.put("userId", Integer.toString(idUsuario));
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("http://localhost:8081/progress/{userId}", String.class, vars);
		System.out.println(result);
		
		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
		JsonArray logros = jsonObject.get("achievements").getAsJsonArray();
		boolean logrado;
		String meta;
		JsonObject logro = new JsonObject();
		Categoria categoria;
		for(int i=0; i<logros.size(); i++) {
			logro = logros.get(i).getAsJsonObject();
			meta = (String) logro.getAsJsonObject("goals").keySet().toArray()[0];
			logrado = logro.getAsJsonObject("goals").getAsJsonObject(meta).get("achieved").getAsBoolean();
			
			switch(logro.get("achievementcategory").getAsString()) {
			case "Platino":
				categoria = Categoria.Platino;
				break;
			case "Oro":
				categoria = Categoria.Oro;
				break;
			case "Plata":
				categoria = Categoria.Plata;
				break;
			default:
				categoria = Categoria.Bronce;
				break;
			}
			if (logrado)
				insignias.add(new Insignia(logro.get("internal_name").getAsString(), categoria));
		}
		
		return insignias;
	}
}
