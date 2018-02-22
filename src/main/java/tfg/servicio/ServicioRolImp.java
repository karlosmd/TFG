package tfg.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.modelo.Rol;
import tfg.repositorio.RepositorioRol;

@Service("servicioRol")
public class ServicioRolImp implements ServicioRol {
	@Autowired
	private RepositorioRol repoRol;
	
	@Override
	public List<Rol> findAll(){
		return repoRol.findAll();
	}
}
