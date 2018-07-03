package tfg.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import tfg.modelo.Variable;
import tfg.modelo.Categoria;
import tfg.modelo.Comparacion;
import tfg.modelo.Insignia;

public class DTOInsignia {
//private int id;
	private List<Insignia> insignias;
	@NotEmpty(message = "* Por favor, introduzca el nombre")
	private String nombre;
	@NotEmpty
	private Categoria categoria;
	@NotEmpty
	private Variable variable;
	@NotEmpty
	private int valor;
	@NotEmpty
	private Comparacion comparacion;
	
	@NotEmpty
	private Variable premiopal;
	@NotEmpty
	private int premionum;

	
	
	public static DTOInsignia toInsignia(Insignia Insignia) {
		DTOInsignia dtoInsignia = new DTOInsignia();
		dtoInsignia.setNombre(Insignia.getNombre());
		dtoInsignia.setCategoria(Insignia.getCategoria());
		dtoInsignia.setVariable(Insignia.getVariable());
		dtoInsignia.setValor(Insignia.getValor());
		dtoInsignia.setComparacion(Insignia.getComparacion());
		dtoInsignia.setPremiopal(Insignia.getPremiopal());
		dtoInsignia.setPremionum(Insignia.getPremionum());
		return dtoInsignia;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public Variable getVariable(){
		return variable;
	}
	public void setVariable(Variable variable){
		this.variable = variable;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Comparacion getComparacion() {
		return comparacion;
	}

	public void setComparacion(Comparacion comparacion) {
		this.comparacion = comparacion;
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
	public List<Insignia> getInsignias() {
		return insignias;
	}

	public void setInsignias(List<Insignia> insignias) {
		this.insignias = insignias;
	}
}
