package tfg.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.dto.DTOAsignatura;
import tfg.objetoNegocio.Asignatura;
import tfg.objetoNegocio.Profesor;
import tfg.repositorio.RepositorioAsignatura;

@Service ("SAAsignatura")
public class SAAsignaturaImp implements SAAsignatura {
	@Autowired
	private RepositorioAsignatura repositorioAsignatura;
	
	// CREATE	
	@Override
	public void crearAsignatura(Asignatura asignatura) {
		repositorioAsignatura.save(asignatura);
	}
	
	// READ
	@Override
	public Asignatura leerPorId(int id) {
		return repositorioAsignatura.findById(id);
	}
	
	@Override
	public List<Asignatura> leerAsignaturasProfesor(int idProfesor){
		return repositorioAsignatura.findAsignaturasProfesor(idProfesor);
	}
	
	@Override
	public List<Asignatura> leerAsignaturasAlumno(int idAlumno){
		return repositorioAsignatura.findAsignaturasAlumno(idAlumno);
	}
	
	// UPDATE
	@Override
	public void actualizarActivo(int id, int activo) {
		repositorioAsignatura.updateActivo(id, activo);
	}
	
	// DELETE
	@Override
	public void borrarPorId(int id) {
		repositorioAsignatura.deleteById(id);
	}
}
