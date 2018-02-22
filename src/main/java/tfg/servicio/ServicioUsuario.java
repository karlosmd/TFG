package tfg.servicio;

import tfg.DTO.DTOUsuario;
import tfg.modelo.Usuario;

public interface ServicioUsuario {
	public Usuario findUserByEmail(String email);
	public void guardarUsuario(DTOUsuario dtoUsuario);
}
