package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
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

import tfg.excepcion.ExcepcionPeticionHTTP;
import tfg.modelo.Alumno;
import tfg.modelo.Asignatura;
import tfg.modelo.Categoria;
import tfg.modelo.GestorPeticionHTTP;
import tfg.modelo.Insignia;
import tfg.modelo.Logro;
import tfg.modelo.Reto;

@Service("saGamificacion")
public class SAGamificacionImp implements SAGamificacion{
	public static final String baseUrl = "http://localhost:8081";
	
	@Autowired
	private SAAlumno saAlumno;
	
	private String apiVersion = "1.0.0";
	private String autorizacion;
	
	@Override
	public void iniciarSesionGamificacion() throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		GestorPeticionHTTP gestorPeticion;
		Map<String, String> parametros = new HashMap<>();
		parametros.put("Name", "admin");
		parametros.put("Password", "admin");
		parametros.put("SourceToken", "SUGAR");
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/loginplatform", parametros);
		
		Map<String, String> cabeceras = new HashMap<>();
		cabeceras.put("APIVersion", apiVersion);
		gestorPeticion.setCabeceras(cabeceras);
		
		gestorPeticion.ejecutar();
		
        autorizacion = gestorPeticion.getRespuesta().getFirstHeader("Authorization").getValue();
	}

	@Override
	public void crearUsuario(Alumno alumno) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {		
		try
		{
		GestorPeticionHTTP gestorPeticion;
		int idGamificacion;
		Map<String, String> parametros = new HashMap<>();
		parametros.put("Name", alumno.getEmail());
		parametros.put("Password", alumno.getPassword());
		parametros.put("SourceToken", "SUGAR");
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/account/create", parametros);
		
		Map<String, String> cabeceras = new HashMap<>();
		cabeceras.put("APIVersion", apiVersion);
		cabeceras.put("Authorization", autorizacion);
		gestorPeticion.setCabeceras(cabeceras);
		
		gestorPeticion.ejecutar();
        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
        idGamificacion = jsonRespuesta.getAsJsonObject("response").getAsJsonObject("user").get("id").getAsInt();
        alumno.setIdGamificacion(idGamificacion);
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando usuario en Sugar para alumno...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
    }
	
	@Override
	public void crearGrupo(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear grupo
		//guardar el id de la respuesta del motor de gamificacion en asignatura.idGamificacion
		try
		{ 
		GestorPeticionHTTP gestorPeticion;
		int idGamificacion;
		Map<String, String> parametros = new HashMap<>();
		parametros.put("Name", String.valueOf(asignatura.getNombre()+asignatura.getGrupo()+asignatura.getCurso()));
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/group", parametros);
		
		Map<String, String> cabeceras = new HashMap<>();
		cabeceras.put("APIVersion", apiVersion);
		cabeceras.put("Authorization", autorizacion);
		gestorPeticion.setCabeceras(cabeceras);
		
		gestorPeticion.ejecutar();
        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
        idGamificacion = jsonRespuesta.getAsJsonObject("response").get("id").getAsInt();
        asignatura.setIdGamificacion(idGamificacion);
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando grupo en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	@Override
	public void crearJuego(Reto reto) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			int idGamificacion;
			Map<String, String> parametros = new HashMap<>();
			parametros.put("Name", String.valueOf(reto.getId()+"-"+reto.getNombre()));
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Game", parametros);
			
			Map<String, String> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.ejecutar();
	        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        idGamificacion = jsonRespuesta.getAsJsonObject("response").get("id").getAsInt();
	        reto.setIdGamificacion(idGamificacion);     
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando juego en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	public void crearLogro(Logro logro, Reto reto) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			int idGamificacion;
			Map<String, String> parametros = new HashMap<>();
			parametros.put("Token", logro.getNombre());
			parametros.put("GameId", String.valueOf(reto.getIdGamificacion()));
			parametros.put("Name", logro.getNombre());
			parametros.put("Description", logro.getDescripcion());
			parametros.put("ActorType", "User");
			
			/*{ EvaluationCriterias : [{ EvaluationDataKey : "Criteria Key", EvaluationDataType : "Long", 
			 * EvaluationDataCategory:"GameData", CriteriaQueryType : "Any",ComparisonType : "Equals", 
			 * Scope : "Actor", Value : logro.getValor() }], Reward : [{ Key : "GameData", DataType : "Long", Value : "10.5" }] }
			
			*/
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Achievements/create", parametros);
			
			Map<String, String> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.ejecutar();
	        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        idGamificacion = jsonRespuesta.getAsJsonObject("response").getAsJsonObject("evaluationCriterias").get("id").getAsInt();;
	        logro.setIdGamificacion(idGamificacion);     
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando logro en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	@Override
	public void insertarUsuarioEnGrupo(Alumno alumno, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para integrar usuario en grupo en el motor de gamificacion
		try{
			GestorPeticionHTTP gestorPeticion;
			int idGamificacion;
			Map<String, String> parametros = new HashMap<>();
			parametros.put("RequestorId", String.valueOf(alumno.getIdGamificacion()));
			parametros.put("AcceptorId", String.valueOf(asignatura.getIdGamificacion()));
			parametros.put("AutoAccept", "true");
			
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/GroupMember", parametros);
			
			Map<String, String> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.ejecutar();
		
		}catch(Exception ex)
	    {
			System.out.println("Error insertando un usuario a un grupo en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	@Override
	public void exportarResultados(Reto reto, String resultados) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		JsonObject jsonObject = new JsonParser().parse(resultados).getAsJsonObject();
		JsonArray jsonUsuarios = jsonObject.get("usuarios").getAsJsonArray();
		int idUsuario, tiempoTotal, tiempoMedio, puntos, porcentajeAciertos;
		JsonObject jsonUsuario = new JsonObject();
		for(int i=0; i<jsonUsuarios.size(); i++) {
			jsonUsuario = jsonUsuarios.get(i).getAsJsonObject();
			idUsuario = jsonUsuario.get("usuarioId").getAsInt();
			tiempoTotal = jsonUsuario.get("tiempoTotal").getAsInt();
			tiempoMedio = jsonUsuario.get("tiempoMedio").getAsInt();
			puntos = jsonUsuario.get("puntos").getAsInt();
			porcentajeAciertos = jsonUsuario.get("porcentajeAciertos").getAsInt();
			
			Alumno alumno = saAlumno.leer(idUsuario);
			mandarResultado(reto, alumno, "tiempoTotal", tiempoTotal);
			mandarResultado(reto, alumno, "tiempoMedio", tiempoMedio);
			mandarResultado(reto, alumno, "puntos", puntos);
			mandarResultado(reto, alumno, "porcentajeAciertos", porcentajeAciertos);
		}
	}
	
	public void mandarResultado(Reto reto, Alumno alumno, String nombre, int valor) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		GestorPeticionHTTP gestorPeticion;
		
		Map<String, String> parametros = new HashMap<>();
		parametros.put("creatingActorId", Integer.toString(alumno.getIdGamificacion()));
		parametros.put("evaluationDataType", "long");
		parametros.put("gameId", Integer.toString(reto.getIdGamificacion()));
		parametros.put("key", nombre);
		parametros.put("value", Integer.toString(valor));
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/actordata", parametros);
		
		Map<String, String> cabeceras = new HashMap<>();
		cabeceras.put("APIVersion", apiVersion);
		cabeceras.put("Authorization", autorizacion);
		gestorPeticion.setCabeceras(cabeceras);
		
		gestorPeticion.ejecutar();
	}
	
	//Motor de gamificacion antiguo (de aqui para abajo)
	
	@Override
	public List<Insignia> getInsignias(int idUsuario) {
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
	
	@Override
	public int getPuntuacion(int idUsuario) {
		
		
		int puntuacion = 0;

		Map<String, String> vars = new HashMap<String, String>();
		vars.put("userId", Integer.toString(idUsuario));
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("http://localhost:8081/progress/{userId}", String.class, vars);
		System.out.println(result);
		
		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
		JsonArray logros = jsonObject.get("achievements").getAsJsonArray();
		String meta, nombreLogro;
		JsonObject logro = new JsonObject();
		for(int i=0; i<logros.size(); i++) {
			logro = logros.get(i).getAsJsonObject();
			nombreLogro = logro.get("internal_name").getAsString();
			if(nombreLogro.equals("Leyenda - Consigue la puntuación maxima del juego (1.000.000 pts)")) {
				meta = (String) logro.getAsJsonObject("goals").keySet().toArray()[0];
				puntuacion = logro.getAsJsonObject("goals").getAsJsonObject(meta).get("value").getAsInt();
				break;
			}
		}
		
		return puntuacion;
	}
	
	@Override
	public void setVariable(String nombre, int valor, int idUsuario) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String url = "http://localhost:8081/increase_value/";

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("value", Integer.toString(valor));
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity( url + "/" + nombre + "/" + idUsuario, request , String.class );
		System.out.println(response);
	}
	
	
	//Elimina usuario de todo el motor, no lo separa del grupo
	@Override
	public void eliminarUsuario(Alumno alumno) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		try
		{        	
			GestorPeticionHTTP gestorPeticion;
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/user/"+String.valueOf(alumno.getIdGamificacion()));
			
			Map<String, String> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.eliminar();
			
		}catch(Exception ex)
		{
			System.out.println("Error eliminando usuario");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
		}
		
	}
	public void eliminarJuego(Reto reto) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		try
		{        	
			GestorPeticionHTTP gestorPeticion;
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/game/"+reto.getIdGamificacion());
			
			Map<String, String> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.eliminar();
			
		}catch(Exception ex)
		{
			System.out.println("Error eliminando juego");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
		}
		
	}
	
	public void eliminarAsigntura(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		try
		{        	
			GestorPeticionHTTP gestorPeticion;
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/group/"+asignatura.getIdGamificacion());
			
			Map<String, String> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.eliminar();
			
		}catch(Exception ex)
		{
			System.out.println("Error eliminando asignatura");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
		}
		
	}
}
