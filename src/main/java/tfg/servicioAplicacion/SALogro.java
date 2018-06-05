package tfg.servicioAplicacion;

import java.util.List;

import tfg.modelo.Asignatura;
import tfg.modelo.Logro;

public interface SALogro {
	
	// CREATE
	public void crearLogro(Logro Logro);
	
	// READ
	public List<Logro> leerPorAsignatura(Asignatura asignatura);
	public Logro leerPorId(int id);
	
	// UPDATE
	public void modificarLogro(Logro Logro);

}