package tfg.objetoNegocio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "alumnos")
public class Alumno extends Usuario {	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "alumno_asignatura",
	    joinColumns = @JoinColumn(name = "alumno"),
	    inverseJoinColumns = @JoinColumn(name = "asignatura")
	)
	private Set<Asignatura> asignaturas;
	
	@NotEmpty
	private String titulacion;
	
	public Alumno(){
		super();
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
