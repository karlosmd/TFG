package tfg.repositorio;

import javax.transaction.Transactional;

import tfg.objetoNegocio.Usuario;

@Transactional
public interface RepositorioUsuario extends RepositorioUsuarios<Usuario> {
}
