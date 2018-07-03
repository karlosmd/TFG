<<<<<<< HEAD
package tfg.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tfg.modelo.Asignatura;
import tfg.modelo.Reto;

@Repository("repositorioReto")
public interface RepositorioReto extends JpaRepository<Reto, Integer> {
	
	// READ	
	@Query("SELECT retos FROM Reto retos WHERE retos.activo = 1 AND retos.asignatura = :asignatura")
	List<Reto> findPorAsignatura(@Param("asignatura") Asignatura asignatura);
	
	Reto findById(int id);
	
	// UPDATE	
	@Transactional
	@Modifying
	@Query("UPDATE Reto SET activo = ?2 WHERE id = ?1")
	void updateActivo(int id, int activo);
}
=======
package tfg.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tfg.modelo.Asignatura;
import tfg.modelo.Reto;

@Repository("repositorioReto")
public interface RepositorioReto extends JpaRepository<Reto, Integer> {
	
	// READ	
	@Query("SELECT retos FROM Reto retos WHERE retos.activo = 1 AND retos.asignatura = :asignatura")
	List<Reto> findPorAsignatura(@Param("asignatura") Asignatura asignatura);
	
	Reto findById(int id);
	
	// UPDATE	
	@Transactional
	@Modifying
	@Query("UPDATE Reto SET activo = ?2 WHERE id = ?1")
	void updateActivo(int id, int activo);
}
>>>>>>> pr/4
