package tfg.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import tfg.modelo.Asignatura;
import tfg.modelo.Logro;

@Repository("repositorioLogros")
public interface RepositorioLogros extends JpaRepository<Logro, Integer> {
	
	// READ	
	@Query("SELECT logros FROM Logro logros WHERE logros.asignatura = :asignatura")
	List<Logro> findPorAsignatura(@Param("asignatura") Asignatura asignatura);
	
	Logro findById(int id);
}
