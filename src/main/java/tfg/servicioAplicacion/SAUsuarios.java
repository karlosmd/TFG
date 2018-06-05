package tfg.servicioAplicacion;

public interface SAUsuarios<T> {
	public T leer(String email);
	public T leer(int id);	
}
