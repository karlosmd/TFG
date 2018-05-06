package tfg.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.objetoNegocio.Asignatura;
import tfg.objetoNegocio.Reto;
import tfg.repositorio.RepositorioReto;

@Service("saReto")
public class SARetoImp implements SAReto{
	@Autowired
	private RepositorioReto repositorioReto;
	
	// CREATE
	@Override
	public void crearReto(Reto reto, Asignatura asignatura) {
		reto.setAsignatura(asignatura);
		repositorioReto.save(reto);
	}
	
	// READ
	@Override
	public List<Reto> leerPorAsignatura(Asignatura asignatura) {
		return repositorioReto.findPorAsignatura(asignatura);
	}
	@Override
	public Reto leerPorId(int id) {
		return repositorioReto.findById(id);
	}
	
	// UPDATE
	@Override
	public void actualizarActivo(int id, int activo) {
		repositorioReto.updateActivo(id, activo);		
	}
}
