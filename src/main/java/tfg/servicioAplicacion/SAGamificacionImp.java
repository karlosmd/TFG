<<<<<<< HEAD
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
	
	@Override
	public void crearGrupo(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear grupo
		//guardar el id de la respuesta del motor de gamificacion en asignatura.idGamificacion
	}
	
	@Override
	public void crearJuego(Reto reto) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
	}
	
	@Override
	public void insertarUsuarioEnGrupo(Alumno alumno, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para integrar usuario en grupo en el motor de gamificacion
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
	
	@Override
	public void eliminarUsuario(int idUsuario) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String url = "http://localhost:8081/delete_user/";
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		//map.add("email", "first.last@example.com");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete( url + idUsuario, request , String.class );
	}	
}
=======
package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.http.client.ClientProtocolException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import tfg.excepcion.ExcepcionPeticionHTTP;
import tfg.modelo.Alumno;
import tfg.modelo.Asignatura;
import tfg.modelo.Categoria;
import tfg.modelo.Comparacion;
import tfg.modelo.GestorPeticionHTTP;
import tfg.modelo.Insignia;
import tfg.modelo.Reto;
import tfg.modelo.Variable;

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
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("Name", "admin");
		parametros.put("Password", "admin");
		parametros.put("SourceToken", "SUGAR");
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/loginplatform", parametros);
		
		Map<String, Object> cabeceras = new HashMap<>();
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
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("Name", alumno.getEmail());
		parametros.put("Password", alumno.getPassword());
		parametros.put("SourceToken", "SUGAR");
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/account/create", parametros);
		
		Map<String, Object> cabeceras = new HashMap<>();
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
		
	//Crea el Ranking al mismo tiempo que la asignatura, crea un Ranking por cada variable
	@Override
	public void crearRanking(Asignatura asignatura, Variable variable)
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			Map<String, Object> parametros = new HashMap<>();
			
			parametros.put("Token", String.valueOf(asignatura.getNombreJuego() + "-" +  variable));
			parametros.put("GameId", String.valueOf(asignatura.getIdGamificacionJuego()));
			parametros.put("Name", String.valueOf(asignatura.getNombreJuego() + "-" +  variable));
			parametros.put("EvaluationDataCategory", "GameData");
			parametros.put("Key", String.valueOf(variable));
			parametros.put("ActorType", "User");
			parametros.put("EvaluationDataType", "Long");
			parametros.put("CriteriaScope", "Actor");
			parametros.put("LeaderboardType", "Highest");
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Leaderboards/create", parametros);
			
			Map<String, Object> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.ejecutar();
	        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        asignatura.setNombreRanking(jsonRespuesta.getAsJsonObject("response").get("name").getAsString());     
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando tablero en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	
	@Override
	public void crearJuego(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			int idGamificacion;
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("Name", String.valueOf(asignatura.getNombre() + asignatura.hashCode()));
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Game", parametros);
			
			Map<String, Object> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.ejecutar();
	        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        idGamificacion = jsonRespuesta.getAsJsonObject("response").get("id").getAsInt();
	        asignatura.setIdGamificacionJuego(idGamificacion);	        
	        asignatura.setNombreJuego(jsonRespuesta.getAsJsonObject("response").get("name").getAsString());
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando juego en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	@Override
	public void inicializarRanking(Asignatura asignatura, Alumno alumno, Variable variable, int valor) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		GestorPeticionHTTP gestorPeticion;
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("creatingActorId", Integer.toString(alumno.getIdGamificacion()));
		parametros.put("GameId", Integer.toString(asignatura.getIdGamificacionJuego()));
		parametros.put("key", variable);
		parametros.put("value", Integer.toString(valor));
		parametros.put("evaluationDataType", "long");
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/gamedata", parametros);
		
		Map<String, Object> cabeceras = new HashMap<>();
		cabeceras.put("APIVersion", apiVersion);
		cabeceras.put("Authorization", autorizacion);
		gestorPeticion.setCabeceras(cabeceras);
		
		gestorPeticion.ejecutar();
	}
	
	//Crear la el logro/insignia
	public void crearAchievement(Insignia insignia, Asignatura asignatura) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			int idGamificacion;
			
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("Token", String.valueOf(insignia.getNombre()).replace(" ", ""));//No acepta espacios
			parametros.put("GameId", String.valueOf(asignatura.getIdGamificacionJuego()));
			parametros.put("Name", String.valueOf(insignia.getNombre()));
			parametros.put("Description", insignia.getCategoria());
			parametros.put("ActorType", "User");
			
			List<Map<String, Object>> criterias = new ArrayList<>();
			Map<String, Object> criteria = new HashMap<>();
			
			criteria.put("EvaluationDataKey", String.valueOf(insignia.getVariable()));
			criteria.put("EvaluationDataType", "Long");
			criteria.put("EvaluationDataCategory", "GameData");
			criteria.put("CriteriaQueryType", "Sum");
			criteria.put("ComparisonType", String.valueOf(insignia.getComparacion()));
			criteria.put("Scope", "Actor");
			criteria.put("Value", String.valueOf(insignia.getValor()));
			
			criterias.add(criteria);
			
			parametros.put("EvaluationCriterias", criterias);
			
			List<Map<String, Object>> premios = new ArrayList<>();
			Map<String, Object> premio = new HashMap<>();
			
			premio.put("EvaluationDataKey", String.valueOf(insignia.getPremiopal()));
			premio.put("EvaluationDataCategory", "GameData");
			premio.put("EvaluationDataType", "Long");
			premio.put("Value", String.valueOf(insignia.getPremionum()));
			
			
			premios.add(premio);
			
			parametros.put("Rewards", premios);
						
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Achievements/create", parametros);
			
			Map<String, Object> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.ejecutar();
			JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        idGamificacion = jsonRespuesta.getAsJsonObject("response").get("id").getAsInt();
	        insignia.setIdGamificacion(idGamificacion);     
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando insignia en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	
	//Inicializa al usuario con valores a 0 en el leaderboard
	@Override
	public void comprobarReward(Asignatura asignatura, Alumno alumno, List<Insignia> listaInsignias) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		try{
			Insignia insignia = new Insignia();
			GestorPeticionHTTP gestorPeticionReward;
			gestorPeticionReward = new GestorPeticionHTTP(baseUrl + "/api/achievements/game/" +
			asignatura.getIdGamificacionJuego() + "/evaluate/" + alumno.getIdGamificacion());
			
			Map<String, Object> cabecerasReward = new HashMap<>();
			cabecerasReward.put("APIVersion", apiVersion);
			cabecerasReward.put("Authorization", autorizacion);
			gestorPeticionReward.setCabeceras(cabecerasReward);
			gestorPeticionReward.conseguir();
			JsonObject jsonRespuestaReward = gestorPeticionReward.getJsonRespuesta();
			JsonArray response = jsonRespuestaReward.get("response").getAsJsonArray();
			JsonObject logro = new JsonObject();
			for(int i=0; i<response.size(); i++) {
				insignia = new Insignia();
				logro = response.get(i).getAsJsonObject();
				if(logro.get("progress").getAsFloat() == 1.0){
					insignia.setNombre(logro.get("name").getAsString());
					switch(logro.get("description").getAsString()) {
			        case "Oro":
						insignia.setCategoria(Categoria.Oro);
						break;
					case "Plata":
						insignia.setCategoria(Categoria.Plata);
						break;
					default:
						insignia.setCategoria(Categoria.Bronce);
						break;
					}
					
					listaInsignias.add(insignia);
				}
			}
		}
		catch(Exception ex)
	    {
			System.out.println("Error cogiendo reward en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
		
	}
	//Devuelve la suma de las variables
		@Override
		public void cogerRankingVersionMejorada(Asignatura asignatura, Variable variable, Alumno alumno) 
				throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
			//Mandar peticion para crear juego
			//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
			
			
			try{
				
				GestorPeticionHTTP gestorPeticion;
				
				gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/GameData/leaderboardType/" +
				alumno.getIdGamificacion() + "/" + asignatura.getIdGamificacionJuego() + "/" + variable + "/Long/2");
				
				Map<String, Object> cabeceras = new HashMap<>();
				cabeceras.put("APIVersion", apiVersion);
				cabeceras.put("Authorization", autorizacion);
				gestorPeticion.setCabeceras(cabeceras);
				gestorPeticion.conseguir();
				
		        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
		        alumno.setValor(jsonRespuesta.getAsJsonObject("response").get("value").getAsInt());
		        switch(jsonRespuesta.getAsJsonObject("response").get("key").getAsString()) {
				case "Puntuacion":
					asignatura.setVariable(Variable.Puntuacion);
					break;
				case "TiempoMedio":
					asignatura.setVariable(Variable.TiempoMedio);
					break;
				default:
					asignatura.setVariable(Variable.PorcentajeAciertos);
					break;
		        }
					            
			}
			catch(Exception ex)
		    {
				System.out.println("Error cogiendo datos cogiendoRanking en Sugar...");
				System.out.println(ex.getMessage());
				System.out.println(ex.getStackTrace());
				return;
		    }
		}
	
	//Devuelve el logro
	@Override
	public void cogerAchievement(List<Insignia> listaInsignias, Asignatura asignatura) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			Insignia insignia = new Insignia();	
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Achievements/game/" +
			asignatura.getIdGamificacionJuego() + "/list");
			
			Map<String, Object> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			gestorPeticion.conseguir();
			
	        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        
        	JsonArray response = jsonRespuesta.get("response").getAsJsonArray();
				
			JsonObject logro = new JsonObject();
			JsonObject logro2 = new JsonObject();
			JsonObject logro3 = new JsonObject();
			for(int i=0; i<response.size(); i++) {
				insignia = new Insignia();
				logro = response.get(i).getAsJsonObject();
				logro2 = response.get(i).getAsJsonObject();
				logro3 = response.get(i).getAsJsonObject();
				insignia.setIdGamificacion(logro.get("id").getAsInt());
				insignia.setNombre(logro.get("name").getAsString());
				switch(logro.get("description").getAsString()) {
		        case "Oro":
					insignia.setCategoria(Categoria.Oro);
					break;
				case "Plata":
					insignia.setCategoria(Categoria.Plata);
					break;
				default:
					insignia.setCategoria(Categoria.Bronce);
					break;
				}
				
				JsonArray evaluationCriterias = logro2.get("evaluationCriterias").getAsJsonArray();
				
				logro2 = evaluationCriterias.get(0).getAsJsonObject();
				
				switch(logro2.get("evaluationDataKey").getAsString()) {
				case "Puntuacion":
					insignia.setVariable(Variable.Puntuacion);
					break;
				case "TiempoMedio":
					insignia.setVariable(Variable.TiempoMedio);
					break;
				default:
					insignia.setVariable(Variable.PorcentajeAciertos);
					break;
				}
		        switch(logro2.get("comparisonType").getAsString()) {
				case "Equals":
					insignia.setComparacion(Comparacion.Equals);
					break;
				case "Greater":
					insignia.setComparacion(Comparacion.Greater);
					break;
				case "GreaterOrEqual":
					insignia.setComparacion(Comparacion.GreaterOrEqual);
					break;
				case "Less":
					insignia.setComparacion(Comparacion.Less);
					break;
				case "LessOrEqual":
					insignia.setComparacion(Comparacion.LessOrEqual);
					break;
				default:
					insignia.setComparacion(Comparacion.NotEqual);
					break;
				}
		        
		        insignia.setValor(logro2.get("value").getAsInt());  
		        
		        JsonArray rewards = logro3.get("rewards").getAsJsonArray();
				
				logro3 = rewards.get(0).getAsJsonObject();
		        
		        switch(logro3.get("evaluationDataKey").getAsString()) {
		        case "Puntuacion":
					insignia.setPremiopal(Variable.Puntuacion);
					break;
				case "TiempoMedio":
					insignia.setPremiopal(Variable.TiempoMedio);
					break;
				default:
					insignia.setPremiopal(Variable.PorcentajeAciertos);
					break;
				}
		        insignia.setPremionum(logro3.get("value").getAsInt());
			
		        listaInsignias.add(insignia);
			}
		}
		catch(Exception ex)
	    {
			System.out.println("Error cogiendo datos cogerAchievement en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
		
	//Se pasan los datos del reto
	@Override
	public void exportarResultados(Reto reto, String resultados) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		JsonObject jsonObject = new JsonParser().parse(resultados).getAsJsonObject();
		JsonArray jsonUsuarios = jsonObject.get("usuarios").getAsJsonArray();
		int idUsuario, tiempoMedio, puntos, porcentajeAciertos;
		JsonObject jsonUsuario = new JsonObject();
		for(int i=0; i<jsonUsuarios.size(); i++) {
			jsonUsuario = jsonUsuarios.get(i).getAsJsonObject();
			idUsuario = jsonUsuario.get("usuarioId").getAsInt();
			tiempoMedio = jsonUsuario.get("tiempoMedio").getAsInt();
			puntos = jsonUsuario.get("puntos").getAsInt();
			porcentajeAciertos = jsonUsuario.get("porcentajeAciertos").getAsInt();
			
			Alumno alumno = saAlumno.leer(idUsuario);
			mandarResultado(reto, alumno, "TiempoMedio", tiempoMedio);
			mandarResultado(reto, alumno, "Puntuacion", puntos);
			mandarResultado(reto, alumno, "PorcentajeAciertos", porcentajeAciertos);
		}
	}
	
	//Se mandan los datos al idGamificacionJuego de la asignatura
	public void mandarResultado(Reto reto, Alumno alumno, String nombre, int valor) 
			throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		GestorPeticionHTTP gestorPeticion;
		
		Asignatura asignatura = reto.getAsignatura();
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("creatingActorId", Integer.toString(alumno.getIdGamificacion()));
		parametros.put("GameId", Integer.toString(asignatura.getIdGamificacionJuego()));
		parametros.put("key", nombre);
		parametros.put("value", Integer.toString(valor));
		parametros.put("evaluationDataType", "long");
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/gamedata", parametros);
		
		Map<String, Object> cabeceras = new HashMap<>();
		cabeceras.put("APIVersion", apiVersion);
		cabeceras.put("Authorization", autorizacion);
		gestorPeticion.setCabeceras(cabeceras);
		
		gestorPeticion.ejecutar();
	}
	
	
}
>>>>>>> pr/4
