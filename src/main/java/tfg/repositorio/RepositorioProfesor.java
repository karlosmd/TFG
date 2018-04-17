package tfg.repositorio;


import javax.transaction.Transactional;

import tfg.objetoNegocio.Profesor;

@Transactional
public interface RepositorioProfesor extends RepositorioUsuarios<Profesor> {
}
