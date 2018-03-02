package tfg.modelo;

public class Mensaje {
	private String cabecera;
	private String contenido;
	private String tipo;
	
	public Mensaje() {}
	public Mensaje(String cabecera, String contenido) {
		this.cabecera = cabecera;
		this.contenido = contenido;
		this.tipo = "success";
	}
	public Mensaje(String cabecera, String contenido, String tipo) {
		this.cabecera = cabecera;
		this.contenido = contenido;
		this.tipo = tipo;
	}
	
	public String getCabecera() {
		return cabecera;
	}
	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
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
}
