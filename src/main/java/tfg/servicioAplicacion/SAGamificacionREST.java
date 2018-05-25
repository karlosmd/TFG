package tfg.servicioAplicacion;

import java.util.List;

import tfg.objetoNegocio.Insignia;

public interface SAGamificacionREST {
	public void crearUsuario(int idUsuario) throws Exception;
	public List<Insignia> getInsignias(int idUsuario);
	public int getPuntuacion(int idUsuario);
	public void setVariable(String nombre, int valor, int idUsuario);
	public void eliminarUsuario(int idUsuario);	
}
