package tfg.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tfg.dto.DTOProfesor;
import tfg.objetoNegocio.Profesor;
import tfg.repositorio.RepositorioProfesor;

@Service("saProfesor")
public class SAProfesorImp implements SAProfesor{
	@Autowired
	private RepositorioProfesor repositorioProfesor;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Transactional
	@Override
	public void crear(DTOProfesor dtoProfesor) {
		Profesor profesor = new Profesor();    
		profesor.setNombre(dtoProfesor.getNombre());
		profesor.setApellidos(dtoProfesor.getApellidos());
		profesor.setDepartamento(dtoProfesor.getDepartamento());
		profesor.setDespacho(dtoProfesor.getDespacho());
		profesor.setEmail(dtoProfesor.getEmail());
		profesor.setPassword(bCryptPasswordEncoder.encode(dtoProfesor.getPassword()));
		repositorioProfesor.save(profesor);
	}
	
	@Override
    public void sobrescribir(Profesor profesor) {
    	repositorioProfesor.save(profesor);
    }

	@Override
	public Profesor leer(String email) {
		return repositorioProfesor.findByEmail(email);
	}

	@Override
	public Profesor leer(int id) {
		return repositorioProfesor.findById(id);
	}
}
