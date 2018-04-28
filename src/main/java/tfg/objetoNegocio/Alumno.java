package tfg.objetoNegocio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.dto.DTOAlumno;

@Entity
@Table(name = "alumnos")
public class Alumno extends Usuario {	
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AlumnoAsignatura> alumnosAsignaturas;
	
	@NotEmpty
	private String titulacion;
	
	public Alumno(){
		super(Rol.Alumno);
		alumnosAsignaturas = new HashSet<>();
	}
	
	public void insertarAsignatura(Asignatura asignatura) {
		AlumnoAsignatura alumnoAsignatura = new AlumnoAsignatura();
		alumnoAsignatura.setAlumno(this);
		alumnoAsignatura.setAsignatura(asignatura);
		alumnosAsignaturas.add(alumnoAsignatura);
	}
	
	public void eliminarAsignatura(Asignatura asignatura) {
		AlumnoAsignatura alumnoAsignatura = new AlumnoAsignatura();
		for(AlumnoAsignatura a : alumnosAsignaturas) {
			if(a.getAlumno().equals(this) && a.getAsignatura().equals(asignatura)) {
				alumnoAsignatura = a;				
			}
		}
		alumnosAsignaturas.remove(alumnoAsignatura);
	}
	
	public DTOAlumno toDTOAlumno() {
		DTOAlumno dtoAlumno = new DTOAlumno();
		dtoAlumno.setId(this.getId());
		dtoAlumno.setNombre(this.getNombre());
		dtoAlumno.setApellidos(this.getApellidos());
		dtoAlumno.setEmail(this.getEmail());
		dtoAlumno.setRol(this.getRol());
		dtoAlumno.setPassword(this.getPassword());
		dtoAlumno.setTitulacion(this.getTitulacion());

		return dtoAlumno;
	}

	public Set<AlumnoAsignatura> getAlumnosAsignaturas() {
		return alumnosAsignaturas;
	}

	public void setAlumnosAsignaturas(Set<AlumnoAsignatura> alumnosAsignaturas) {
		this.alumnosAsignaturas = alumnosAsignaturas;
	}

	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(String titulacion) {
		this.titulacion = titulacion;
	}
}
