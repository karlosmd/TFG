package tfg.modelo;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tfg.excepcion.ExcepcionPeticionHTTP;

public class GestorPeticionHTTP {
	private String url;
	
	private int codigoEstado;
	
	private Map<String, String> parametros;
	
	private Map<String, String> cabeceras;
	
	private HttpResponse respuesta;
	
	private JsonObject jsonRespuesta;

	public GestorPeticionHTTP(String url){
		this.url = url;
	}
	
	public GestorPeticionHTTP(String url, Map<String, String> parametros){
		this.url = url;
		this.parametros = parametros;
	}
	
	public void ejecutar() throws ClientProtocolException, IOException, ExcepcionPeticionHTTP {
		Gson jsonParametros = new Gson();
		String parametrosRespuesta;
		
		StringEntity entity = new StringEntity(jsonParametros.toJson(parametros), ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost peticion = new HttpPost(url);
        for (String clave : cabeceras.keySet()) {
        	peticion.addHeader(clave, cabeceras.get(clave));
        }
        peticion.setEntity(entity);
        
        respuesta = httpClient.execute(peticion);
        codigoEstado = respuesta.getStatusLine().getStatusCode();
        if(codigoEstado >= 400) {
        	throw new ExcepcionPeticionHTTP(codigoEstado);
        }
        parametrosRespuesta = EntityUtils.toString(respuesta.getEntity());
        jsonRespuesta = new JsonParser().parse(parametrosRespuesta).getAsJsonObject();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(int codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}

	public Map<String, String> getCabeceras() {
		return cabeceras;
	}

	public void setCabeceras(Map<String, String> cabeceras) {
		this.cabeceras = cabeceras;
	}

	public HttpResponse getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(HttpResponse respuesta) {
		this.respuesta = respuesta;
	}

	public JsonObject getJsonRespuesta() {
		return jsonRespuesta;
	}

	public void setJsonRespuesta(JsonObject jsonRespuesta) {
		this.jsonRespuesta = jsonRespuesta;
	}	
}
