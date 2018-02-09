package tfg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import tfg.entidades.Password;

public interface RepositorioPassword extends JpaRepository<Password, Long> {

}
