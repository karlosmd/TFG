package tfg.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfg.modelo.Usuario;

@Repository("repositorioUsuario")
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
	 Usuario findByEmail(String email);
}