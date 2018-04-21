package tfg.objetoNegocio;

public class Insignia {
	private String nombre;
	private Categoria categoria;
	
	public Insignia(){
	}
	
	public Insignia(String nombre, Categoria categoria){
		this.nombre = nombre;
		this.categoria = categoria;
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
}
