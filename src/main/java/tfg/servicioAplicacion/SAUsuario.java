package tfg.servicioAplicacion;

import java.util.List;

import tfg.DTO.DTOUsuario;
import tfg.modelo.Usuario;

public interface SAUsuario {
	
	// CREATE
	public void crearUsuario(DTOUsuario dtoUsuario);
	public void sobrescribirUsuario(Usuario usuario);
	
	// READ
	public Usuario leerPorEmail(String email);
	public Usuario leerPorId(int id);
	public List<Usuario> leerAlumnosActivos();
	public List<Usuario> leerPorIdAsignatura(int idAsignatura);
	public List<Usuario> leerPorNoIdAsignatura(int idAsignatura);
}
