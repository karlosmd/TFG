package tfg.repositorio;


import javax.transaction.Transactional;

import tfg.modelo.Profesor;

@Transactional
public interface RepositorioProfesor extends RepositorioUsuarios<Profesor> {
}
