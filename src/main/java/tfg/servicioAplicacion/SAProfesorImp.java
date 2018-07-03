<<<<<<< HEAD
package tfg.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tfg.modelo.Profesor;
import tfg.repositorio.RepositorioProfesor;

@Service("saProfesor")
public class SAProfesorImp implements SAProfesor{
	@Autowired
	private RepositorioProfesor repositorioProfesor;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Transactional
	@Override
	public void crear(Profesor profesor) {
    	profesor.setPassword(bCryptPasswordEncoder.encode(profesor.getPassword()));
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
=======
package tfg.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tfg.modelo.Profesor;
import tfg.repositorio.RepositorioProfesor;

@Service("saProfesor")
public class SAProfesorImp implements SAProfesor{
	@Autowired
	private RepositorioProfesor repositorioProfesor;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Transactional
	@Override
	public void crear(Profesor profesor) {
		   	profesor.setPassword(bCryptPasswordEncoder.encode(profesor.getPassword()));
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
>>>>>>> pr/4
