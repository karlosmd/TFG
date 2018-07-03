package tfg.dto;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.modelo.Asignatura;
import tfg.modelo.Reto;

public class DTOReto {
	private int id;
	
	@NotEmpty(message = "* Por favor, introduzca el nombre")
	private String nombre;

	private String enlace;
	
	private boolean disponible;
	
	private Asignatura asignatura;
	
	private boolean activo;
	
	public static DTOReto toDTOReto(Reto reto) {
		DTOReto dtoReto = new DTOReto();
		dtoReto.setId(reto.getId());
		dtoReto.setNombre(reto.getNombre());
		dtoReto.setDisponible(reto.isDisponible());
		dtoReto.setAsignatura(reto.getAsignatura());
		dtoReto.setActivo(reto.isActivo());
		return dtoReto;
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

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
