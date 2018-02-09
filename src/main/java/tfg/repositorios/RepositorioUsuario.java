package tfg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import tfg.entidades.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
}
