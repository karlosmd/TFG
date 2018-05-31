package tfg.objetoNegocio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.dto.DTOAsignatura;

@Entity
@Table(name = "asignaturas")
public class Asignatura {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String grupo;
	@NotEmpty
	private String curso;
	
	@OneToMany(mappedBy = "asignatura")
	private Set<AlumnoAsignatura> alumnosAsignaturas;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor")
    private Profesor profesor;
	
	@OneToMany(
		mappedBy = "asignatura", 
		cascade = CascadeType.ALL, 
		orphanRemoval = true
	)
	private Set<Reto> retos;
	
	@Column(nullable = false)
	private int idGamificacion;
	
	private int activo;
	
	public Asignatura(){
		activo = 1;
		alumnosAsignaturas = new HashSet<>();
		retos = new HashSet<>();
	}
	
	public static Asignatura toObjetoNegocio(DTOAsignatura dtoAsignatura) {
		Asignatura asignatura = new Asignatura();    
		asignatura.setNombre(dtoAsignatura.getNombre());
		asignatura.setGrupo(dtoAsignatura.getGrupo());
		asignatura.setCurso(dtoAsignatura.getCurso());
		
		return asignatura;
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

	public Set<AlumnoAsignatura> getAlumnosAsignaturas() {
		return alumnosAsignaturas;
	}

	public void setAlumnosAsignaturas(Set<AlumnoAsignatura> alumnosAsignaturas) {
		this.alumnosAsignaturas = alumnosAsignaturas;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Set<Reto> getRetos() {
		return retos;
	}

	public void setRetos(Set<Reto> retos) {
		this.retos = retos;
	}

	public int getIdGamificacion() {
		return idGamificacion;
	}

	public void setIdGamificacion(int idGamificacion) {
		this.idGamificacion = idGamificacion;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
}
