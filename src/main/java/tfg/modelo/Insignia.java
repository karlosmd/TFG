package tfg.modelo;

import tfg.dto.DTOAsignatura;
import tfg.dto.DTOInsignia;

public class Insignia {
	private String nombre;
	private String descripcion;
	private int valor;
	private Variable variable;
	private int idGamificacion;
	private Comparacion comparacion;
	private Variable premiopal;
	private int premionum;
	
	public Insignia(){
	}
	
	public Insignia(String nombre, String descripcion, Variable variable, int valor, Comparacion comparacion, Variable premiopal, int premionum){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.variable = variable;
		this.valor = valor;
		this.comparacion = comparacion;
		this.premiopal = premiopal;
		this.premionum = premionum;
	}
	
	public static Insignia toInsignia (DTOInsignia dtoInsignia) {
		Insignia insignia = new Insignia();    
		insignia.setNombre(dtoInsignia.getNombre());
		insignia.setDescripcion(dtoInsignia.getDescripcion());
		insignia.setVariable(dtoInsignia.getVariable());
		insignia.setValor(dtoInsignia.getValor());
		insignia.setComparacion(dtoInsignia.getComparacion());
		insignia.setPremiopal(dtoInsignia.getPremiopal());
		insignia.setPremionum(dtoInsignia.getPremionum());
		
		return insignia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/*public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}*/
	public Comparacion getComparacion() {
		return comparacion;
	}

	public void setComparacion(Comparacion comparacion) {
		this.comparacion = comparacion;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	
	public int getIdGamificacion() {
		return idGamificacion;
	}

	public void setIdGamificacion(int idGamificacion) {
		this.idGamificacion = idGamificacion;
	}
	
	public Variable getPremiopal() {
		return premiopal;
	}

	public void setPremiopal(Variable premiopal) {
		this.premiopal = premiopal;
	}
	
	public int getPremionum() {
		return premionum;
	}

	public void setPremionum(int premionum) {
		this.premionum = premionum;
	}
}
