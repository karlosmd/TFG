package tfg.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class DTOAsignatura {	
	@NotEmpty(message = "* Por favor, introduzca el nombre")
	private String nombre;
	@NotEmpty(message = "* Por favor, introduzca el grupo")
	private String grupo;
	@NotEmpty(message = "* Por favor, introduzca el curso")
	private String curso;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
}
