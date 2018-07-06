package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tfg.excepcion.ExcepcionPeticionHTTP;
import tfg.modelo.Alumno;
import tfg.modelo.Asignatura;
import tfg.modelo.GestorPeticionHTTP;
import tfg.modelo.Resultado;
import tfg.modelo.Reto;
import tfg.repositorio.RepositorioAlumno;
import tfg.repositorio.RepositorioAsignatura;

@Service("saAnaliticas")
public class SAAnaliticasImp implements SAAnaliticas{
	
	public static final String baseUrl = "http://localhost:8000/api";
	
	@Autowired
	private RepositorioAlumno repositorioAlumno;

	@Override
	public List<Resultado> obtenerResultados(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		GestorPeticionHTTP gestorPeticion;
		Set<Reto> retos = asignatura.getRetos();
		List<Resultado> resultados = new ArrayList<>();
		Resultado resultado = new Resultado();
		
		for(Reto reto : retos) {
			gestorPeticion = new GestorPeticionHTTP(baseUrl + "/reto/" + reto.getId() + "/resultados");
			
			gestorPeticion.conseguir();
			JsonObject jsonObject = gestorPeticion.getJsonRespuesta();
			JsonArray jsonUsuarios = jsonObject.get("usuarios").getAsJsonArray();
			JsonArray jsonPreguntas;
			int idUsuario, tiempoMedio, puntos, porcentajeAciertos;
			JsonObject jsonUsuario = new JsonObject();
			JsonObject jsonPregunta;
			for(int i = 0; i < jsonUsuarios.size(); i++) {
				jsonUsuario = jsonUsuarios.get(i).getAsJsonObject();
				jsonPreguntas = jsonUsuario.get("preguntas").getAsJsonArray();
				
				idUsuario = jsonUsuario.get("usuarioId").getAsInt();
				tiempoMedio = jsonUsuario.get("tiempoMedio").getAsInt();
				puntos = jsonUsuario.get("puntos").getAsInt();
				porcentajeAciertos = jsonUsuario.get("porcentajeAciertos").getAsInt();
				
				Alumno alumno = repositorioAlumno.findById(idUsuario);

				for(int j = 0; j < jsonPreguntas.size(); j++) {
					jsonPregunta = jsonPreguntas.get(j).getAsJsonObject();
					
					resultado = new Resultado();
					resultado.setAlumno(alumno.getNombre() + " " + alumno.getApellidos());
					resultado.setReto(reto.getNombre());
					resultado.setPregunta(jsonPregunta.get("Pregunta").getAsString());
					resultado.setRespuesta(jsonPregunta.get("OpcionMarcada").getAsString());
					resultado.setCorrecta(jsonPregunta.get("Correcta").getAsBoolean());
					resultado.setTiempo(jsonPregunta.get("Tiempo").getAsInt() / 1000);
					resultados.add(resultado);
				}
			}			
		}
		
		return resultados;
	}
}
