package tfg.servicioAplicacion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tfg.modelo.Alumno;
import tfg.repositorio.RepositorioAlumno;

@Service("saAlumno")
public class SAAlumnoImp implements SAAlumno{
	@Autowired
	private RepositorioAlumno repositorioAlumno;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	@Override
	public void crear(Alumno alumno) {
	    alumno.setPassword(bCryptPasswordEncoder.encode(alumno.getPassword()));
	    repositorioAlumno.save(alumno);
	}
	
	@Override
    public void sobrescribir(Alumno alumno) {
    	repositorioAlumno.save(alumno);
    }

	@Override
	public Alumno leer(String email) {
		return repositorioAlumno.findByEmail(email);
	}

	@Override
	public Alumno leer(int id) {
		return repositorioAlumno.findById(id);
	}

	@Override
	public List<Alumno> leerTodos() {
		return repositorioAlumno.findAll();
	}
	
	@Override
	public List<Alumno> leerMatriculadosAsignatura(int idAsignatura){
		return repositorioAlumno.findByAsignatura(idAsignatura);
	}
	
	@Override
	public List<Alumno> leerNoMatriculadosAsignatura(int idAsignatura){
		return repositorioAlumno.findByNotAsignatura(idAsignatura);
	}
}
