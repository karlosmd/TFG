package tfg.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "profesores")
public class Profesor extends Usuario {
	@OneToMany(
		mappedBy = "profesor", 
		cascade = CascadeType.ALL, 
		orphanRemoval = true
	)
	private Set<Asignatura> asignaturas;
	
	public Profesor(){
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
}
