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
	
	@Override
	public List<Asignatura> findAll() {
		return repositorioAsignatura.findAll();
	}

	@Override
	public void guardarAsignatura(DTOAsignatura dtoAsignatura) {
		Asignatura asignatura = new Asignatura();    
		asignatura.setNombre(dtoAsignatura.getNombre());
		asignatura.setGrupo(dtoAsignatura.getGrupo());
		asignatura.setCurso(dtoAsignatura.getCurso());
	    asignatura.setActivo(1);
	    repositorioAsignatura.save(asignatura);
	}
	
	@Override
	public void deleteById(int id) {
		repositorioAsignatura.deleteById(id);
	}
}
