package tfg.servicioAplicacion;

import java.util.List;

import tfg.DTO.DTOAsignatura;
import tfg.modelo.Asignatura;
import tfg.modelo.Profesor;

public interface SAAsignatura {
	
	// CREATE
	public void crearAsignatura(DTOAsignatura dtoAsignatura, Profesor profesor);
	
	// READ
	public Asignatura leerPorId(int id);
	public List<Asignatura> leerAsignaturasProfesor(int idProfesor);
	public List<Asignatura> leerAsignaturasAlumno(int idAlumno);
	
	// UPDATE
	public void actualizarActivo(int id, int activo);
	
	// DELETE
	public void borrarPorId(int id);	
}
