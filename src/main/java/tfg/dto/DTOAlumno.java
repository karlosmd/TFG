package tfg.dto;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.objetoNegocio.Asignatura;

public class DTOAlumno extends DTOUsuario {
	private Set<Asignatura> asignaturas;
	
	@NotEmpty(message = "* Por favor, introduzca su titulacion")
	private String titulacion;
	
	public DTOAlumno(){
		super();
		asignaturas = new HashSet<>();
	}
	
	public DTOAlumno(DTOUsuario dtoUsuario, String titulacion) {
		super(dtoUsuario);
		asignaturas = new HashSet<>();
		this.titulacion = titulacion;	
	}

	public Set<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(Set<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(String titulacion) {
		this.titulacion = titulacion;
	}
}
