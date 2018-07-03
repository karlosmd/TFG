<<<<<<< HEAD
package tfg.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	
	@Column(nullable = false)
	private int idGamificacion;
	
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
	
	public static Alumno toAlumno (DTOAlumno dtoAlumno) {
		Alumno alumno = new Alumno();
		alumno.setId(dtoAlumno.getId());
		alumno.setNombre(dtoAlumno.getNombre());
		alumno.setApellidos(dtoAlumno.getApellidos());
		alumno.setRol(dtoAlumno.getRol());
		alumno.setEmail(dtoAlumno.getEmail());
		alumno.setPassword(dtoAlumno.getPassword());
		alumno.setTitulacion(dtoAlumno.getTitulacion());
		
		return alumno;
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
		dtoAlumno.setToken(this.getToken());

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
	
	public int getIdGamificacion() {
		return idGamificacion;
	}

	public void setIdGamificacion(int idGamificacion) {
		this.idGamificacion = idGamificacion;
	}
}
=======
package tfg.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	
	@Column(nullable = false)
	private int idGamificacion;
	
	private int valor;
	
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
	
	public static Alumno toAlumno (DTOAlumno dtoAlumno) {
		Alumno alumno = new Alumno();
		alumno.setId(dtoAlumno.getId());
		alumno.setNombre(dtoAlumno.getNombre());
		alumno.setApellidos(dtoAlumno.getApellidos());
		alumno.setRol(dtoAlumno.getRol());
		alumno.setEmail(dtoAlumno.getEmail());
		alumno.setPassword(dtoAlumno.getPassword());
		alumno.setTitulacion(dtoAlumno.getTitulacion());
		
		return alumno;
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
		dtoAlumno.setToken(this.getToken());

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
	
	public int getIdGamificacion() {
		return idGamificacion;
	}

	public void setIdGamificacion(int idGamificacion) {
		this.idGamificacion = idGamificacion;
	}
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
}
>>>>>>> pr/4
