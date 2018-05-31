package tfg.servicioAplicacion;

import java.util.List;

import tfg.dto.DTOAlumno;
import tfg.objetoNegocio.Alumno;

public interface SAAlumno extends SAUsuarios<Alumno> {
	public void crear(Alumno alumno);
	public void sobrescribir(Alumno alumno);
	public List<Alumno> leerTodos();
	public List<Alumno> leerMatriculadosAsignatura(int idAsignatura);
	public List<Alumno> leerNoMatriculadosAsignatura(int idAsignatura);
}
