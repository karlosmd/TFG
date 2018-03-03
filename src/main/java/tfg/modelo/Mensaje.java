package tfg.modelo;

public class Mensaje {
	private String titulo;
	private String contenido;
	private String tipo;
	private String enlace;
	private String textoEnlace;
	private String icono;
	
	public Mensaje() {}
	public Mensaje(String titulo, String contenido, String tipo) {
		this.titulo = titulo;
		this.contenido = contenido;
		this.tipo = tipo;
		this.enlace = null;
		this.textoEnlace = null;
		this.icono = null;
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
