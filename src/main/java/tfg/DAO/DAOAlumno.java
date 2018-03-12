package tfg.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tfg.modelo.Alumno;

@Transactional
public interface DAOAlumno extends DAOUsuario<Alumno> {
	
	// READ	
	//Consulta de Entidades con relaciones ManyToMany
	@Query("SELECT alumno FROM Alumno alumno JOIN alumno.asignaturas asignaturas WHERE alumno.activo = 1 AND asignaturas.id = :idAsignatura ORDER BY alumno.apellidos ASC")
	List<Alumno> findByAsignatura(@Param("idAsignatura") int idAsignatura);
	
	//Consulta de Entidades con relaciones ManyToMany
	@Query("SELECT alumno FROM Alumno alumno JOIN alumno.asignaturas asignaturas WHERE alumno.activo = 1 AND asignaturas.id <> :idAsignatura ORDER BY alumno.apellidos ASC")
	List<Alumno> findByNotAsignatura(@Param("idAsignatura") int idAsignatura);
}