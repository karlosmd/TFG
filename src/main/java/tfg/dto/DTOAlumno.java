package tfg.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.modelo.Asignatura;
import tfg.modelo.Insignia;

public class DTOAlumno extends DTOUsuario {
	private Set<Asignatura> asignaturas;
	
	@NotEmpty(message = "* Por favor, introduzca su titulacion")
	private String titulacion;
	
	private long puntuacion;
	
	private List<Insignia> insignias;
	
	private List<Integer> aciertos;
	
	private int certitud;
	
	private long tiempo;	
	
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

	public long getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(long puntuacion) {
		this.puntuacion = puntuacion;
	}

	public List<Insignia> getInsignias() {
		return insignias;
	}

	public void setInsignias(List<Insignia> insignias) {
		this.insignias = insignias;
	}

	public List<Integer> getAciertos() {
		return aciertos;
	}

	public void setAciertos(List<Integer> aciertos) {
		this.aciertos = aciertos;
	}

	public int getCertitud() {
		return certitud;
	}

	public void setCertitud(int certitud) {
		this.certitud = certitud;
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
}
