package tfg.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tfg.modelo.Asignatura;

@Repository("daoAsignatura")
public interface DAOAsignatura extends JpaRepository<Asignatura, Integer>{
	
	// READ	
	@Query("SELECT a FROM Asignatura a WHERE a.activo = 1")
	List<Asignatura> findActivos();

	@Query("SELECT p.asignaturas FROM Profesor p WHERE p.activo = 1 AND p.id = :idProfesor")
	List<Asignatura> findAsignaturasProfesor(@Param("idProfesor")int idProfesor);
	
	@Query("SELECT a.asignaturas FROM Alumno a WHERE a.activo = 1 AND a.id = :idProfesor")
	List<Asignatura> findAsignaturasAlumno(@Param("idProfesor")int idAlumno);
	
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
