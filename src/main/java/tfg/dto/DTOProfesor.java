<<<<<<< HEAD
package tfg.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.modelo.Asignatura;

public class DTOProfesor extends DTOUsuario {
	private Set<Asignatura> asignaturas;
	
	@NotEmpty(message = "* Por favor, introduzca su departamento")
	private String departamento;
	
	@Column(nullable=false)
	private int despacho;
	
	public DTOProfesor(){
		super();
		asignaturas = new HashSet<>();
	}
	
	public DTOProfesor(DTOUsuario dtoUsuario, String departamento, int despacho) {
		super(dtoUsuario);
		asignaturas = new HashSet<>();
		this.departamento = departamento;
		this.despacho = despacho;		
	}

	public Set<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(Set<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public int getDespacho() {
		return despacho;
	}

	public void setDespacho(int despacho) {
		this.despacho = despacho;
	}
}
=======
package tfg.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.modelo.Asignatura;

public class DTOProfesor extends DTOUsuario {
	private Set<Asignatura> asignaturas;
	
	@NotEmpty(message = "* Por favor, introduzca su departamento")
	private String departamento;
	
	@Column(nullable=false)
	private int despacho;
	
	public DTOProfesor(){
		super();
		asignaturas = new HashSet<>();
	}
	
	public DTOProfesor(DTOUsuario dtoUsuario, String departamento, int despacho) {
		super(dtoUsuario);
		asignaturas = new HashSet<>();
		this.departamento = departamento;
		this.despacho = despacho;		
	}

	public Set<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(Set<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public int getDespacho() {
		return despacho;
	}

	public void setDespacho(int despacho) {
		this.despacho = despacho;
	}
}
>>>>>>> pr/4
