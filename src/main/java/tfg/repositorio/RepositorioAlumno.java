<<<<<<< HEAD
package tfg.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tfg.modelo.Alumno;

@Transactional
public interface RepositorioAlumno extends RepositorioUsuarios<Alumno> {
	
	// READ	
	//Consulta de Entidades con relaciones ManyToMany
	@Query("SELECT alumno FROM Alumno alumno JOIN alumno.alumnosAsignaturas r JOIN r.asignatura asignatura WHERE asignatura.id = :idAsignatura ORDER BY alumno.apellidos ASC")
	List<Alumno> findByAsignatura(@Param("idAsignatura") int idAsignatura);
	
	//Consulta de Entidades con relaciones ManyToMany
	@Query(value = "SELECT alumnoNoMatriculado FROM Alumno alumnoNoMatriculado WHERE alumnoNoMatriculado.id NOT IN (SELECT alumno FROM Alumno alumno JOIN alumno.alumnosAsignaturas r JOIN r.asignatura asignatura WHERE asignatura.id = :idAsignatura ORDER BY alumno.apellidos ASC)")
	List<Alumno> findByNotAsignatura(@Param("idAsignatura") int idAsignatura);
=======
package tfg.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tfg.modelo.Alumno;

@Transactional
public interface RepositorioAlumno extends RepositorioUsuarios<Alumno> {
	
	// READ	
	//Consulta de Entidades con relaciones ManyToMany
	@Query("SELECT alumno FROM Alumno alumno JOIN alumno.alumnosAsignaturas r JOIN r.asignatura asignatura WHERE asignatura.id = :idAsignatura ORDER BY alumno.apellidos ASC")
	List<Alumno> findByAsignatura(@Param("idAsignatura") int idAsignatura);
	
	//Consulta de Entidades con relaciones ManyToMany
	@Query(value = "SELECT alumnoNoMatriculado FROM Alumno alumnoNoMatriculado WHERE alumnoNoMatriculado.id NOT IN (SELECT alumno FROM Alumno alumno JOIN alumno.alumnosAsignaturas r JOIN r.asignatura asignatura WHERE asignatura.id = :idAsignatura ORDER BY alumno.apellidos ASC)")
	List<Alumno> findByNotAsignatura(@Param("idAsignatura") int idAsignatura);
>>>>>>> pr/4
}