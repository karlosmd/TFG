package tfg.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tfg.modelo.Usuario;

@Repository("repositorioUsuario")
public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {
	
	// READ	
	Usuario findByEmail(String email);
	
	Usuario findById(int id);
	
	@Query("SELECT u FROM Usuario u WHERE u.activo = 1 AND u.rol = 0 ORDER BY u.apellidos ASC")
	List<Usuario> findAlumnosActivos();
	
	//Consulta de Entidades con relaciones ManyToMany
	@Query("SELECT u FROM Usuario u JOIN u.asignaturas a WHERE u.activo = 1 AND u.rol = 0 AND a.id = :idAsignatura ORDER BY u.apellidos ASC")
	List<Usuario> findByAsignatura(@Param("idAsignatura") int idAsignatura);
	
	//Consulta de Entidades con relaciones ManyToMany
	@Query("SELECT u FROM Usuario u JOIN u.asignaturas a WHERE u.activo = 1 AND u.rol = 0 AND a.id <> :idAsignatura ORDER BY u.apellidos ASC")
	List<Usuario> findByNotAsignatura(@Param("idAsignatura") int idAsignatura);
}
