package tfg.servicioAplicacion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tfg.DAO.DAOAlumno;
import tfg.DAO.DAOProfesor;
import tfg.DTO.DTOUsuario;
import tfg.modelo.Alumno;
import tfg.modelo.Profesor;
import tfg.modelo.Rol;

@Service("saUsuario")
public class SAUsuarioImp implements SAUsuario {
	@Autowired
	private DAOAlumno daoAlumno;
	@Autowired
	private DAOProfesor daoProfesor;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    // CREATE
    @Transactional
    @Override
    public void crear(DTOUsuario dtoUsuario) {
    	if(dtoUsuario.getRol() == Rol.Alumno) {
    		Alumno alumno = new Alumno();    
            alumno.setNombre(dtoUsuario.getNombre());
    	    alumno.setApellidos(dtoUsuario.getApellidos());
    	    alumno.setEmail(dtoUsuario.getEmail());
    	    alumno.setPassword(bCryptPasswordEncoder.encode(dtoUsuario.getPassword()));
    	    alumno.setActivo(1);
    	    daoAlumno.save(alumno);
    	}
    	else {
			Profesor profesor = new Profesor();    
			profesor.setNombre(dtoUsuario.getNombre());
			profesor.setApellidos(dtoUsuario.getApellidos());
			profesor.setEmail(dtoUsuario.getEmail());
			profesor.setPassword(bCryptPasswordEncoder.encode(dtoUsuario.getPassword()));
			profesor.setActivo(1);
			daoProfesor.save(profesor);
    	}    	
    }
    
    @Override
    public void sobrescribir(Alumno alumno) {
    	daoAlumno.save(alumno);
    }
    
    @Override
    public void sobrescribir(Profesor profesor) {
    	daoProfesor.save(profesor);
    }
    
    // READ
    @Override
    public DTOUsuario leerUsuario(String email) {
    	Alumno alumno = leerAlumno(email);
    	Profesor profesor = leerProfesor(email);
    	if(alumno != null) {
    		DTOUsuario dtoUsuario = new DTOUsuario(); 
    		dtoUsuario.setId(alumno.getId());
        	dtoUsuario.setNombre(alumno.getNombre());
        	dtoUsuario.setApellidos(alumno.getApellidos());
        	dtoUsuario.setEmail(alumno.getEmail());
        	dtoUsuario.setPassword(alumno.getPassword());
        	dtoUsuario.setRol(Rol.Alumno);
        	dtoUsuario.setAsignaturas(alumno.getAsignaturas());
        	return dtoUsuario;
    	}
    	else if(profesor != null) {
    		DTOUsuario dtoUsuario = new DTOUsuario();
    		dtoUsuario.setId(profesor.getId());
        	dtoUsuario.setNombre(profesor.getNombre());
        	dtoUsuario.setApellidos(profesor.getApellidos());
        	dtoUsuario.setEmail(profesor.getEmail());
        	dtoUsuario.setPassword(profesor.getPassword());
        	dtoUsuario.setRol(Rol.Profesor);
        	dtoUsuario.setAsignaturas(profesor.getAsignaturas());
        	return dtoUsuario;
    	}
    	else
    		return null;    	
    }
    
    @Override
    public DTOUsuario leerUsuario(int id) {
    	Alumno alumno = leerAlumno(id);
    	Profesor profesor = leerProfesor(id);
    	if(alumno != null) {
    		DTOUsuario dtoUsuario = new DTOUsuario(); 
    		dtoUsuario.setId(alumno.getId());
        	dtoUsuario.setNombre(alumno.getNombre());
        	dtoUsuario.setApellidos(alumno.getApellidos());
        	dtoUsuario.setEmail(alumno.getEmail());
        	dtoUsuario.setPassword(alumno.getPassword());
        	dtoUsuario.setRol(Rol.Alumno);
        	dtoUsuario.setAsignaturas(alumno.getAsignaturas());
        	return dtoUsuario;
    	}
    	else if(profesor != null) {
    		DTOUsuario dtoUsuario = new DTOUsuario();
    		dtoUsuario.setId(profesor.getId());
        	dtoUsuario.setNombre(profesor.getNombre());
        	dtoUsuario.setApellidos(profesor.getApellidos());
        	dtoUsuario.setEmail(profesor.getEmail());
        	dtoUsuario.setPassword(profesor.getPassword());
        	dtoUsuario.setRol(Rol.Profesor);
        	dtoUsuario.setAsignaturas(profesor.getAsignaturas());
        	return dtoUsuario;
    	}
    	else
    		return null;
    }
	
    @Override
	public Alumno leerAlumno(String email) {
		return daoAlumno.findByEmail(email);
	}
	
    @Override
	public Alumno leerAlumno(int id) {
		return daoAlumno.findById(id);
	}
    
    @Override
	public Profesor leerProfesor(String email) {
		return daoProfesor.findByEmail(email);
	}
	
    @Override
	public Profesor leerProfesor(int id) {
		return daoProfesor.findById(id);
	}
    
    @Override
	public List<Alumno> leerAlumnosActivos(){
		return daoAlumno.findActivos();
	}
	
	@Override
	public List<Alumno> leerMatriculadosAsignatura(int idAsignatura){
		return daoAlumno.findByAsignatura(idAsignatura);
	}
	
	@Override
	public List<Alumno> leerNoMatriculadosAsignatura(int idAsignatura){
		return daoAlumno.findByNotAsignatura(idAsignatura);
	}
}
