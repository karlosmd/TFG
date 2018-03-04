package tfg.servicioAplicacion;

import java.util.List;

import tfg.DTO.DTOAsignatura;
import tfg.modelo.Asignatura;

public interface SAAsignatura {
	
	// CREATE
	public void crearAsignatura(DTOAsignatura dtoAsignatura);
	
	// READ
	public Asignatura leerPorId(int id);
	public List<Asignatura> leerActivos();
	
	// UPDATE
	public void actualizarActivo(int id, int activo);
	
	// DELETE
	public void borrarPorId(int id);	
}
