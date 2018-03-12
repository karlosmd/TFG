package tfg.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DAOUsuario<T> extends JpaRepository<T, Integer> {
	
	// READ	
	T findByEmail(String email);
	
	T findById(int id);
	
	@Query("SELECT u FROM #{#entityName} as u WHERE u.activo = 1 ORDER BY u.apellidos ASC")
	List<T> findActivos();
}
