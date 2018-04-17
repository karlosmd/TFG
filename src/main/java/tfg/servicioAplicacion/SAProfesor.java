package tfg.servicioAplicacion;

import tfg.dto.DTOProfesor;
import tfg.objetoNegocio.Profesor;

public interface SAProfesor extends SAUsuarios<Profesor>{
	public void crear(DTOProfesor dtoProfesor);
	public void sobrescribir(Profesor profesor);
}
