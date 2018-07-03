<<<<<<< HEAD
package tfg.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositorioUsuarios<T> extends JpaRepository<T, Integer> {
	
	// READ
	T findByEmail(String email);
	
	T findById(int id);
	
	@Query("SELECT u FROM #{#entityName} as u ORDER BY u.apellidos ASC")
	List<T> findAll();
}
=======
package tfg.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositorioUsuarios<T> extends JpaRepository<T, Integer> {
	
	// READ
	T findByEmail(String email);
	
	T findById(int id);
	
	@Query("SELECT u FROM #{#entityName} as u ORDER BY u.apellidos ASC")
	List<T> findAll();
}
>>>>>>> pr/4
