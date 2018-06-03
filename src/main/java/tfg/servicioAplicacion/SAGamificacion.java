package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import tfg.excepcion.ExcepcionPeticionREST;
import tfg.modelo.Alumno;
import tfg.modelo.Asignatura;
import tfg.modelo.Insignia;
import tfg.modelo.Reto;

public interface SAGamificacion {
	public void iniciarSesionGamificacion() throws ClientProtocolException, IOException, ExcepcionPeticionREST;
	public void crearUsuario(Alumno alumno) throws ClientProtocolException, IOException, ExcepcionPeticionREST;
	public void crearGrupo(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionREST;
	public void crearJuego(Reto reto) throws ClientProtocolException, IOException, ExcepcionPeticionREST, ExcepcionPeticionREST;
	public void insertarUsuarioEnGrupo(Alumno alumno, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionREST;
	public void exportarResultados(Reto reto, String resultados) throws ClientProtocolException, IOException, ExcepcionPeticionREST;
	public List<Insignia> getInsignias(int idUsuario);
	public int getPuntuacion(int idUsuario);
	public void setVariable(String nombre, int valor, int idUsuario);
	public void eliminarUsuario(int idUsuario);	
}
