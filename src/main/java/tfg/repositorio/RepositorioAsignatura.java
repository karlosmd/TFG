package tfg.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import tfg.modelo.Asignatura;

@Repository("repositorioAsignatura")
public interface RepositorioAsignatura extends JpaRepository<Asignatura, Integer>{
	Asignatura findById(int id);
	List<Asignatura> findAll();
	@Transactional
    @Modifying
	void deleteById(int id);
}
