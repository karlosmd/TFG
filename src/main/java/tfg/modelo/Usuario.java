package tfg.modelo;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.InheritanceType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "usuarios")
@Inheritance(
    strategy = InheritanceType.JOINED
)
public abstract class Usuario {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellidos;
	
	@Column(nullable=false)
	private Rol rol;
	
	@Email
	@NotEmpty
	@Column(unique=true)
	private String email;
	
	@Length(min = 6)
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String token;
	
	private boolean activo;
	
	public Usuario (){
		token = generarToken();
		activo = true;
	}
	
	public Usuario (Rol rol){
		this.rol = rol;
		token = generarToken();
		activo = true;
	}
	
	public abstract void insertarAsignatura(Asignatura asignatura);
	
	public abstract void eliminarAsignatura(Asignatura asignatura);
	
	public String generarToken() {
		UUID uuid = UUID.randomUUID();
	    String token = uuid.toString().replace("-", "");
	    return token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
