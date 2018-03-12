package tfg.DAO;


import javax.transaction.Transactional;

import tfg.modelo.Profesor;

@Transactional
public interface DAOProfesor extends DAOUsuario<Profesor> {
}
