package tfg.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "alumno_asignatura")
public class AlumnoAsignatura implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "alumno")
	private Alumno alumno;
	@ManyToOne
	@JoinColumn(name = "asignatura")
    private Asignatura asignatura;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	public Asignatura getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}
}