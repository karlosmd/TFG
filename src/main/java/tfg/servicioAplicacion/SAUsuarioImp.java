package tfg.servicioAplicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.objetoNegocio.Usuario;
import tfg.repositorio.RepositorioUsuario;

@Service("saUsuario")
public class SAUsuarioImp implements SAUsuario {
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Override
	public Usuario leer(String email) {
		return repositorioUsuario.findByEmail(email);
	}

	@Override
	public Usuario leer(int id) {
		return repositorioUsuario.findById(id);
	}
}
