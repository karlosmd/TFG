package tfg.servicioAplicacion;


import tfg.modelo.Profesor;

public interface SAProfesor extends SAUsuarios<Profesor>{
	public void crear(Profesor profesor);
	public void sobrescribir(Profesor profesor);
}
