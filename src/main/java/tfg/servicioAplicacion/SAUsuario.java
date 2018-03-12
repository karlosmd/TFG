package tfg.servicioAplicacion;

import java.util.List;

import tfg.DTO.DTOUsuario;
import tfg.modelo.Alumno;
import tfg.modelo.Profesor;

public interface SAUsuario {
	
	// CREATE
	public void crear(DTOUsuario dtoUsuario);
	public void sobrescribir(Alumno alumno);
	public void sobrescribir(Profesor profesor);
	
	// READ
	public DTOUsuario leerUsuario(String email);
	public DTOUsuario leerUsuario(int id);
	public Alumno leerAlumno(String email);
	public Alumno leerAlumno(int id);
	public Profesor leerProfesor(String email);
	public Profesor leerProfesor(int id);
	public List<Alumno> leerAlumnosActivos();	
	public List<Alumno> leerMatriculadosAsignatura(int idAsignatura);
	public List<Alumno> leerNoMatriculadosAsignatura(int idAsignatura);
}
