package tfg.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.DTO.DTOAsignatura;
import tfg.modelo.Asignatura;
import tfg.repositorio.RepositorioAsignatura;

@Service ("SAAsignatura")
public class SAAsignaturaImp implements SAAsignatura {
	@Autowired
	private RepositorioAsignatura repositorioAsignatura;
	
	// CREATE	
	@Override
	public void crearAsignatura(DTOAsignatura dtoAsignatura) {
		Asignatura asignatura = new Asignatura();    
		asignatura.setNombre(dtoAsignatura.getNombre());
		asignatura.setGrupo(dtoAsignatura.getGrupo());
		asignatura.setCurso(dtoAsignatura.getCurso());
	    asignatura.setActivo(1);
	    repositorioAsignatura.save(asignatura);
	}
	
	// READ
	@Override
	public Asignatura leerPorId(int id) {
		return repositorioAsignatura.findById(id);
	}
	
	@Override
	public List<Asignatura> leerActivos() {
		return repositorioAsignatura.findActivos();
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
