package tfg.servicioAplicacion;

import java.util.List;

import tfg.objetoNegocio.Asignatura;
import tfg.objetoNegocio.Reto;

public interface SAReto {
	
	// CREATE
	public void crearReto(Reto Reto, Asignatura asignatura);
	
	// READ
	public List<Reto> leerPorAsignatura(Asignatura asignatura);
	public Reto leerPorId(int id);
	
	// UPDATE
	public void actualizarActivo(int id, int activo);

}
