package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
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
	
	@Override
	public void crearGrupo(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear grupo
		//guardar el id de la respuesta del motor de gamificacion en asignatura.idGamificacion
		try
		{ 
		GestorPeticionHTTP gestorPeticion;
		int idGamificacion;
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("Name", String.valueOf(asignatura.getNombre()+asignatura.getGrupo()+asignatura.getCurso()));
		
		gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/group", parametros);
		
		Map<String, Object> cabeceras = new HashMap<>();
		cabeceras.put("APIVersion", apiVersion);
		cabeceras.put("Authorization", autorizacion);
		gestorPeticion.setCabeceras(cabeceras);
		
		gestorPeticion.ejecutar();
        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
        idGamificacion = jsonRespuesta.getAsJsonObject("response").get("id").getAsInt();
        asignatura.setIdGamificacionGrupo(idGamificacion);
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando grupo en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	//Crea el tablero al mismo tiempo que la asignatura, crea un tablero por cada variable(Ver en TFGControlador)
	@Override
	public void crearTablero(Asignatura asignatura, Variable variable) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			Map<String, Object> parametros = new HashMap<>();
			
			parametros.put("Token", String.valueOf(asignatura.getNombreJuego()+"-"+ variable));
			parametros.put("GameId", String.valueOf(asignatura.getIdGamificacionJuego()));
			parametros.put("Name", String.valueOf(asignatura.getNombreJuego()+"-"+ variable));
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
	        asignatura.setNombreTablero(jsonRespuesta.getAsJsonObject("response").get("name").getAsString());     
		}
		catch(Exception ex)
	    {
			System.out.println("Error creando juego en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	
	
	//El id del juego ya n tiene nada que ver con el reto, seria solo con la asignatura
	@Override
	public void crearJuego(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			int idGamificacion;
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("Name", String.valueOf(asignatura.getIdGamificacionGrupo()+"-"+asignatura.getNombre()));
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Game", parametros);
			
			Map<String, Object> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.ejecutar();
	        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        idGamificacion = jsonRespuesta.getAsJsonObject("response").get("id").getAsInt();
	        asignatura.setIdGamificacionJuego(idGamificacion);	        
	        //asignatura.setIdGamificacionJuego(jsonRespuesta.getAsJsonObject("response").get("id").getAsInt());
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
	
	
	//He puesto de esta forma para ve si sirve para coger el array
	public void crearAchievement(Insignia insignia, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			int idGamificacion;
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("Token", insignia.getNombre());
			parametros.put("GameId", String.valueOf(asignatura.getIdGamificacionJuego()));
			parametros.put("Name", insignia.getNombre());
			parametros.put("Description", insignia.getDescripcion());
			parametros.put("ActorType", "User");
			
			List<Map<String, Object>> criterias = new ArrayList<>();
			Map<String, Object> criteria = new HashMap<>();
			
			criteria.put("EvaluationDataKey", String.valueOf(insignia.getVariable()));
			criteria.put("EvaluationDataType", "Long");
			criteria.put("EvaluationDataCategory", "GameData");
			criteria.put("CriteriaQueryType", "Any");
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
	        idGamificacion = jsonRespuesta.getAsJsonObject("response").getAsJsonObject("evaluationCriterias").get("id").getAsInt();
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
	
	
	//Seria en el menu, que elijan la variable que quieren ver l leaderboard y se cogerian sus datos, no se si crear otro modelo Tablero
	//SOLO DEVUELVE NOMBRE Y VARIABLE
	@Override
	public void cogerTablero(Asignatura asignatura, Variable variable) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
			
	
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Leaderboards/"+asignatura.getNombreJuego()+"-"+variable+"/"+asignatura.getIdGamificacionJuego());
			
			Map<String, Object> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			gestorPeticion.conseguir();
			
	        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        asignatura.setNombreTablero(jsonRespuesta.getAsJsonObject("response").get("name").getAsString());
	        switch(jsonRespuesta.getAsJsonObject("response").get("Key").getAsString()) {
			case "Puntuacion":
				asignatura.setVariable(Variable.Puntuacion);
				break;
			case "Tiempo":
				asignatura.setVariable(Variable.Tiempo);
				break;
			case "TiempoMedio":
				asignatura.setVariable(Variable.TiempoMedio);
				break;
			case "PorcentajeAciertos":
				asignatura.setVariable(Variable.PorcentajeAciertos);
				break;
			default:
				asignatura.setVariable(Variable.Posicion);
				break;
			}
	            
		}
		catch(Exception ex)
	    {
			System.out.println("Error cogiendo datos logro en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	
	//Seria en el menu, que elijan la variable que quieren ver l leaderboard y se cogerian sus datos, no se si crear otro modelo Tablero, 
	//Este devuelve el dato total de la variable que elijas pero debes meter uno a uno todos los usuarios que esten en el juego para que los muestre
		@Override
		public void cogerTableroVersionMejorada(Asignatura asignatura, Variable variable, Alumno alumno) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
			//Mandar peticion para crear juego
			//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
			try{   
				GestorPeticionHTTP gestorPeticion;
				
				gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/GameData/leaderboardType/"+alumno.getIdGamificacion()+"/"+asignatura.getIdGamificacionJuego()+"/"+variable+"/Long/2");
				
				Map<String, Object> cabeceras = new HashMap<>();
				cabeceras.put("APIVersion", apiVersion);
				cabeceras.put("Authorization", autorizacion);
				gestorPeticion.setCabeceras(cabeceras);
				gestorPeticion.conseguir();
				
		        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
		        alumno.setValor(jsonRespuesta.getAsJsonObject("response").get("value").getAsInt());
		        switch(jsonRespuesta.getAsJsonObject("response").get("Key").getAsString()) {
				case "Puntuacion":
					asignatura.setVariable(Variable.Puntuacion);
					break;
				case "Tiempo":
					asignatura.setVariable(Variable.Tiempo);
					break;
				case "TiempoMedio":
					asignatura.setVariable(Variable.TiempoMedio);
					break;
				case "PorcentajeAciertos":
					asignatura.setVariable(Variable.PorcentajeAciertos);
					break;
				default:
					asignatura.setVariable(Variable.Posicion);
					break;
				}
		            
			}
			catch(Exception ex)
		    {
				System.out.println("Error cogiendo datos logro en Sugar...");
				System.out.println(ex.getMessage());
				System.out.println(ex.getStackTrace());
				return;
		    }
		}
	
	//Devuelve el logro
	@Override
	public void cogerAchievement(Insignia insignia, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para crear juego
		//guardar el id de la respuesta del motor de gamificacion en reto.idGamificacion
		try{   
			GestorPeticionHTTP gestorPeticion;
				
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Achievements/game/"+ asignatura.getIdGamificacionJuego() +"/list");
			
			Map<String, Object> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			gestorPeticion.conseguir();
			
	        JsonObject jsonRespuesta = gestorPeticion.getJsonRespuesta();
	        insignia.setNombre(jsonRespuesta.getAsJsonObject("response").get("Name").getAsString());
	        insignia.setDescripcion(jsonRespuesta.getAsJsonObject("response").get("Description").getAsString());
	        switch(jsonRespuesta.getAsJsonObject("response").getAsJsonObject("evaluationCriterias").get("evaluationDataKey").getAsString()) {
			case "Puntuacion":
				insignia.setVariable(Variable.Puntuacion);
				break;
			case "Tiempo":
				insignia.setVariable(Variable.Tiempo);
				break;
			case "TiempoMedio":
				insignia.setVariable(Variable.TiempoMedio);
				break;
			case "PorcentajeAciertos":
				insignia.setVariable(Variable.PorcentajeAciertos);
				break;
			default:
				insignia.setVariable(Variable.Posicion);
				break;
			}
	        switch(jsonRespuesta.getAsJsonObject("response").getAsJsonObject("evaluationCriterias").get("comparisonType").getAsString()) {
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
	        
	        insignia.setValor(jsonRespuesta.getAsJsonObject("response").getAsJsonObject("evaluationCriterias").get("value").getAsInt());
	        insignia.setIdGamificacion(jsonRespuesta.getAsJsonObject("response").get("id").getAsInt());  
		}
		catch(Exception ex)
	    {
			System.out.println("Error cogiendo datos logro en Sugar...");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
	    }
	}
	
	//Inserta usuario en un grupo, pero no vale para nada, porque he intentado sacar inf por grupo y siempre sale como que no hay nada
	@Override
	public void insertarUsuarioEnGrupo(Alumno alumno, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		//Mandar peticion para integrar usuario en grupo en el motor de gamificacion
		try{
			GestorPeticionHTTP gestorPeticion;
			int idGamificacion;
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("RequestorId", String.valueOf(alumno.getIdGamificacion()));
			parametros.put("AcceptorId", String.valueOf(asignatura.getIdGamificacionGrupo()));
			parametros.put("AutoAccept", "true");
			
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/GroupMember", parametros);
			
			Map<String, Object> cabeceras = new HashMap<>();
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
	
	//Se pasa la asignatura ya que es el nuevo Juego
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
			Asignatura asignatura = reto.getAsignatura();
			
			Alumno alumno = saAlumno.leer(idUsuario);
			mandarResultado(asignatura, alumno, "tiempoTotal", tiempoTotal);
			mandarResultado(asignatura, alumno, "tiempoMedio", tiempoMedio);
			mandarResultado(asignatura, alumno, "puntos", puntos);
			mandarResultado(asignatura, alumno, "porcentajeAciertos", porcentajeAciertos);
		}
	}
	
	//Se mandan los datos al idGamificacionJuego de la asignatura
	public void mandarResultado(Asignatura asignatura, Alumno alumno, String nombre, int valor) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		GestorPeticionHTTP gestorPeticion;
		
		
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
	/*
	//Motor de gamificacion antiguo (de aqui para abajo)
	
	@Override
	public List<Insignia> getInsignias(int idUsuario) {
		List<Insignia> insignias = new ArrayList<Insignia>();

		Map<String, Object> vars = new HashMap<String, Object>();
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
			//if (logrado)
				//insignias.add(new Insignia(logro.get("internal_name").getAsString(), categoria));
		}
		
		return insignias;
	}
	
	@Override
	public int getPuntuacion(int idUsuario) {
		
		
		int puntuacion = 0;

		Map<String, Object> vars = new HashMap<String, Object>();
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

		MultiValueMap<String, Object> map= new LinkedMultiValueMap<String, Object>();
		map.add("value", Integer.toString(valor));
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);

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
			
			Map<String, Object> cabeceras = new HashMap<>();
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
	
	//Elimina asignatura como juego del motor
	public void eliminarJuego(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		try
		{        	
			GestorPeticionHTTP gestorPeticion;
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/game/"+asignatura.getIdGamificacionJuego());
			
			Map<String, Object> cabeceras = new HashMap<>();
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
	
	//Elimina el logro
	public void eliminarAchievement(Insignia insignia, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		try
		{        	
			GestorPeticionHTTP gestorPeticion;
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/Achievements/"+insignia.getNombre()+"/"+asignatura.getIdGamificacionJuego());
			
			Map<String, Object> cabeceras = new HashMap<>();
			cabeceras.put("APIVersion", apiVersion);
			cabeceras.put("Authorization", autorizacion);
			gestorPeticion.setCabeceras(cabeceras);
			
			gestorPeticion.eliminar();
			
		}catch(Exception ex)
		{
			System.out.println("Error eliminando logro");
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			return;
		}
		
	}
	//Elimina asignatura del motor
	public void eliminarAsignatura(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP{
		try
		{        	
			GestorPeticionHTTP gestorPeticion;
			
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/api/group/"+asignatura.getIdGamificacionGrupo());
			
			Map<String, Object> cabeceras = new HashMap<>();
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
		
	}*/
}
