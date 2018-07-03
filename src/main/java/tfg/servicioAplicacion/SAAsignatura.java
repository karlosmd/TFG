<<<<<<< HEAD
package tfg.servicioAplicacion;

import java.util.List;

import tfg.modelo.Asignatura;

public interface SAAsignatura {
	
	// CREATE
	public void crearAsignatura(Asignatura asignatura);
	
	// READ
	public Asignatura leerPorId(int id);
	public List<Asignatura> leerAsignaturasProfesor(int idProfesor);
	public List<Asignatura> leerAsignaturasAlumno(int idAlumno);
	
	// UPDATE
	public void actualizarActivo(int id, int activo);
	
	// DELETE
	public void borrarPorId(int id);	
}
=======
package tfg.servicioAplicacion;

import java.util.List;

import tfg.modelo.Asignatura;

public interface SAAsignatura {
	
	// CREATE
	public void crearAsignatura(Asignatura asignatura);
	
	// READ
	public Asignatura leerPorId(int id);
	public List<Asignatura> leerAsignaturasProfesor(int idProfesor);
	public List<Asignatura> leerAsignaturasAlumno(int idAlumno);
	
	// UPDATE
	public void actualizarActivo(int id, int activo);
	
	// DELETE
	public void borrarPorId(int id);	
}
>>>>>>> pr/4
