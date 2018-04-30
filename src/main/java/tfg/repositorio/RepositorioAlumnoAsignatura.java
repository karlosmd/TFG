package tfg.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tfg.objetoNegocio.AlumnoAsignatura;

@Repository("repositorioAlumnoAsignatura")
public interface RepositorioAlumnoAsignatura  extends JpaRepository<AlumnoAsignatura, Integer>{
	
	// READ	
	@Query("SELECT id FROM AlumnoAsignatura r WHERE r.asignatura.id = :idAsignatura AND r.alumno.id = :idAlumno")
	int findId(@Param("idAsignatura")int idAsignatura, @Param("idAlumno")int idAlumno);

}
