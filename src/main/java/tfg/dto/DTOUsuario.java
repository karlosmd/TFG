package tfg.dto;

import javax.persistence.Column;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import tfg.objetoNegocio.Rol;

public class DTOUsuario {
	private int id;	
	
	@NotEmpty(message = "* Por favor, introduzca su nombre")
	private String nombre;
	
	@NotEmpty(message = "* Por favor, introduzca su apellido")
	private String apellidos;
	
	@Column(nullable=false)
	private Rol rol;
	
	@Email(message = "* Por favor, introduzca un correo electrónico válido")
	@NotEmpty(message = "* Por favor, introduzca un correo electrónico")
	private String email;

	@Length(min = 6, message = "* La contraseña deberá tener al menos 6 caracteres")
	@NotEmpty(message = "* Por favor, introduzca una contraseña")
	private String password;
	
	@NotEmpty(message = "* Por favor, introduzca una contraseña")
	private String confirmarPassword;
	
	public DTOUsuario() {
	}
	
	public DTOUsuario(DTOUsuario dtoUsuario) {
		this.id = dtoUsuario.id;
		this.nombre = dtoUsuario.nombre;
		this.apellidos = dtoUsuario.apellidos;
		this.rol = dtoUsuario.rol;
		this.email = dtoUsuario.email;
		this.password = dtoUsuario.password;
		this.confirmarPassword = dtoUsuario.confirmarPassword;
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

	public String getConfirmarPassword() {
		return confirmarPassword;
	}

	public void setConfirmarPassword(String confirmarPassword) {
		this.confirmarPassword = confirmarPassword;
	}
}
