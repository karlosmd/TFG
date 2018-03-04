package tfg.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfg.modelo.Asignatura;

@Repository("repositorioAsignatura")
public interface RepositorioAsignatura extends JpaRepository<Asignatura, Integer>{
	
	// READ	
	@Query("SELECT a FROM Asignatura a WHERE a.activo = 1")
	List<Asignatura> findActivos();
	
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
