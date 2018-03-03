package tfg.servicioAplicacion;

import java.util.List;

import tfg.DTO.DTOAsignatura;
import tfg.modelo.Asignatura;

public interface SAAsignatura {
	public List<Asignatura> findAll();
	public List<Asignatura> buscarActivos();
	public void guardarAsignatura(DTOAsignatura dtoAsignatura);
	public void deleteById(int id);
	public Asignatura findById(int id);
	public void actualizarActivo(int id, int activo);
}
