package tfg.dto;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.modelo.Asignatura;
import tfg.modelo.Logro;

public class DTOLogro {
private int id;
	
	@NotEmpty(message = "* Por favor, introduzca el nombre")
	private String nombre;
	private String variable;
	private String descripcion;
	private int valor;
	private Asignatura asignatura;
	
	
	public static DTOLogro toDTOLogro(Logro Logro) {
		DTOLogro dtoLogro = new DTOLogro();
		dtoLogro.setId(Logro.getId());
		dtoLogro.setNombre(Logro.getNombre());
		dtoLogro.setVariable(Logro.getVariable());
		dtoLogro.setValor(Logro.getValor());
		dtoLogro.setAsignatura(Logro.getAsignatura());
		return dtoLogro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public String getVariable(){
		return variable;
	}
	public void setVariable(String Variable){
		this.variable = variable;
	}
	public String getDescripcion(){
		return descripcion;
	}
	public void setDescripcion(String Descripcion){
		this.descripcion = descripcion;
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
}
