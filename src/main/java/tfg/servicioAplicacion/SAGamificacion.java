package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import tfg.objetoNegocio.Alumno;
import tfg.objetoNegocio.Asignatura;
import tfg.objetoNegocio.Insignia;
import tfg.objetoNegocio.Reto;

public interface SAGamificacion {
	public void iniciarSesionGamificacion() throws ClientProtocolException, IOException;
	public void crearUsuario(Alumno alumno) throws ClientProtocolException, IOException;
	public void crearGrupo(Asignatura asignatura) throws ClientProtocolException, IOException;
	public void crearJuego(Reto reto) throws ClientProtocolException, IOException;
	public void insertarUsuarioEnGrupo(Alumno alumno, Asignatura asignatura) throws ClientProtocolException, IOException;
	public void exportarResultados(Reto reto, String resultados) throws ClientProtocolException, IOException;
	public List<Insignia> getInsignias(int idUsuario);
	public int getPuntuacion(int idUsuario);
	public void setVariable(String nombre, int valor, int idUsuario);
	public void eliminarUsuario(int idUsuario);	
}
