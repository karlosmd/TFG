package tfg.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.dto.DTOReto;
import tfg.servicioAplicacion.SARetoImp;

@Entity
@Table(name = "retos")
public class Reto {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	private String nombre;
	
	private boolean disponible;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura")
	private Asignatura asignatura;
	
	@Column(nullable = false)
	private int idGamificacion;
	
	private boolean activo;
	
	public Reto() {
		disponible = false;
		activo = true;
	}
	
	public Reto(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.disponible = false;
		this.activo = true;
	}
	
	public static Reto toModeloDelDominio(DTOReto dtoReto) {
		Reto reto = new Reto();
		reto.setNombre(dtoReto.getNombre());
		return reto;
	}
	
	public String generarEnlace() {
		return SARetoImp.baseUrl + "/reto/" + this.id;
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

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public int getIdGamificacion() {
		return idGamificacion;
	}

	public void setIdGamificacion(int idGamificacion) {
		this.idGamificacion = idGamificacion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
