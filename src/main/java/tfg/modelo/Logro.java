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

import tfg.dto.DTOLogro;
import tfg.dto.DTOReto;
import tfg.servicioAplicacion.SARetoImp;

@Entity
@Table(name = "logros")
public class Logro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	private String nombre;
	private String variable;
	private String descripcion;
	private int valor;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura")
	private Asignatura asignatura;
	
	@Column(nullable = false)
	private int idGamificacion;
	
	
	
	public Logro() {
		this.id = id;
		this.nombre = nombre;
	}
	


	public static Logro toLogro(DTOLogro dtoLogro) {
		Logro logro = new Logro();
		logro.setNombre(dtoLogro.getNombre());
		logro.setVariable(dtoLogro.getVariable());
		logro.setValor(dtoLogro.getValor());
		return logro;
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

	public int getIdGamificacion() {
		return idGamificacion;
	}

	public void setIdGamificacion(int idGamificacion) {
		this.idGamificacion = idGamificacion;
	}

	public String getVariable() {
		return variable;
	}
	
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
