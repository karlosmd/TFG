package tfg.objetoNegocio;

import java.util.Map;

public class Mensaje {
	private String titulo;
	private String contenido;
	private String tipo;
	private String enlace;
	private Map<String, Integer> campos;
	private String textoEnlace;
	private String icono;
	
	public Mensaje() {}
	public Mensaje(String titulo, String contenido, String tipo) {
		this.titulo = titulo;
		this.contenido = contenido;
		this.tipo = tipo;
		this.enlace = null;
		this.campos = null;
		this.textoEnlace = null;
		this.icono = null;
	}
	
	public void definirPeticion(String enlace, Map<String, Integer> campos, String textoEnlace) {
		this.enlace = enlace;
		this.campos = campos;
		this.textoEnlace = textoEnlace;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEnlace() {
		return enlace;
	}
	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}
	public Map<String, Integer> getCampos() {
		return campos;
	}
	public void setCampos(Map<String, Integer> campos) {
		this.campos = campos;
	}
	public String getTextoEnlace() {
		return textoEnlace;
	}
	public void setTextoEnlace(String textoEnlace) {
		this.textoEnlace = textoEnlace;
	}
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
}
