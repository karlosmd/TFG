package tfg.servicioAplicacion;

import java.util.List;

import tfg.objetoNegocio.Insignia;

public interface SAGamificacionREST {
	public void crearUsuario(int idUsuario);
	public List<Insignia> getInsignias(int idAsignatura, int idUsuario);
}
