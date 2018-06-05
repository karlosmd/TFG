package tfg.servicioAplicacion;

import java.util.List;

import tfg.modelo.Asignatura;
import tfg.modelo.Reto;

public interface SAReto {
	
	// CREATE
	public void crearReto(Reto Reto);
	
	// READ
	public List<Reto> leerPorAsignatura(Asignatura asignatura);
	public Reto leerPorId(int id);
	
	// UPDATE
	public void modificarReto(Reto reto);

}
