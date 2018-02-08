package tfg.repositorios;

import tfg.modelos.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioPassword extends JpaRepository<Password, Long> {

}
