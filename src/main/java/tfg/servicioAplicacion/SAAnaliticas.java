package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import tfg.excepcion.ExcepcionPeticionHTTP;
import tfg.modelo.Asignatura;
import tfg.modelo.Resultado;

public interface SAAnaliticas {
	public List<Resultado> obtenerResultados(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
}
