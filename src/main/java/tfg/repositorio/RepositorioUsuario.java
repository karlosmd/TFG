package tfg.repositorio;

import javax.transaction.Transactional;

import tfg.modelo.Usuario;

@Transactional
public interface RepositorioUsuario extends RepositorioUsuarios<Usuario> {
}
