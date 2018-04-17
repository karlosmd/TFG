package tfg.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tfg.objetoNegocio.Asignatura;

@Repository("repositorioAsignatura")
public interface RepositorioAsignatura extends JpaRepository<Asignatura, Integer>{
	
	// READ	
	@Query("SELECT asignaturas FROM Profesor profesor JOIN profesor.asignaturas asignaturas WHERE asignaturas.activo = 1 AND profesor.id = :idProfesor")
	List<Asignatura> findAsignaturasProfesor(@Param("idProfesor")int idProfesor);
	
	@Query("SELECT asignaturas FROM Alumno alumno JOIN alumno.asignaturas asignaturas WHERE asignaturas.activo = 1 AND alumno.id = :idAlumno")
	List<Asignatura> findAsignaturasAlumno(@Param("idAlumno")int idAlumno);
	
	Asignatura findById(int id);
	
	// UPDATE	
	@Transactional
	@Modifying
	@Query("UPDATE Asignatura SET activo = ?2 WHERE id = ?1")
	void updateActivo(int id, int activo);
	
	// DELETE	
	@Transactional
    @Modifying
	void deleteById(int id);
}
