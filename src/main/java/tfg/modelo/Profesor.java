package tfg.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.dto.DTOProfesor;

@Entity
@Table(name = "profesores")
public class Profesor extends Usuario {
	@OneToMany(
		mappedBy = "profesor", 
		cascade = CascadeType.ALL, 
		orphanRemoval = true
	)
	private Set<Asignatura> asignaturas;
	
	@NotEmpty
	private String departamento;
	
	@Column(nullable=false)
	private int despacho;
	
	public Profesor(){
		super(Rol.Profesor);
		asignaturas = new HashSet<>();
	}
	
	public void insertarAsignatura(Asignatura asignatura) {
		asignaturas.add(asignatura);
	}
	
	public void eliminarAsignatura(Asignatura asignatura) {
		asignaturas.remove(asignatura);
	}

	public Set<Asignatura> getAsignaturas() {
		return asignaturas;
	}
	public static Profesor toProfesor (DTOProfesor dtoProfesor) {
				Profesor profesor = new Profesor();   
				profesor.setId(dtoProfesor.getId());
				profesor.setNombre(dtoProfesor.getNombre());
				profesor.setApellidos(dtoProfesor.getApellidos());
				profesor.setRol(dtoProfesor.getRol());
				profesor.setEmail(dtoProfesor.getEmail());
				profesor.setPassword(dtoProfesor.getPassword());
				profesor.setDepartamento(dtoProfesor.getDepartamento());
				profesor.setDespacho(dtoProfesor.getDespacho());		
				
				return profesor;
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
