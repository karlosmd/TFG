package tfg.DTO;

import org.hibernate.validator.constraints.NotEmpty;

public class DTOReto {
	@NotEmpty(message = "* Por favor, introduzca el nombre")
	private String nombre;
	@NotEmpty(message = "* Por favor, introduzca el enlace")
	private String enlace;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEnlace() {
		return enlace;
	}
	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}
}
