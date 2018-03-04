package tfg.servicioAplicacion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tfg.DTO.DTOUsuario;
import tfg.modelo.Asignatura;
import tfg.modelo.Usuario;
import tfg.repositorio.RepositorioUsuario;

@Service("saUsuario")
public class SAUsuarioImp implements SAUsuario{

	@Autowired
	private RepositorioUsuario repositorioUsuario;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    // CREATE
    @Transactional
    @Override
    public void crearUsuario(DTOUsuario dtoUsuario) {
        Usuario usuario = new Usuario();    
	    usuario.setNombre(dtoUsuario.getNombre());
	    usuario.setApellidos(dtoUsuario.getApellidos());
	    usuario.setEmail(dtoUsuario.getEmail());
	    usuario.setPassword(bCryptPasswordEncoder.encode(dtoUsuario.getPassword()));
	    usuario.setRol(dtoUsuario.getRol());
		usuario.setActivo(1);
        repositorioUsuario.save(usuario);
    }
    
    public void sobrescribirUsuario(Usuario usuario) {
    	repositorioUsuario.save(usuario);
    }
    
    // READ
	@Override
	public Usuario leerPorEmail(String email) {
		return repositorioUsuario.findByEmail(email);
	}
	
	public Usuario leerPorId(int id) {
		return repositorioUsuario.findById(id);
	}
	
	public List<Usuario> leerAlumnosActivos(){
		return repositorioUsuario.findAlumnosActivos();
	}
	
	public List<Usuario> leerPorIdAsignatura(int idAsignatura){
		return repositorioUsuario.findByAsignatura(idAsignatura);
	}
	
	public List<Usuario> leerPorNoIdAsignatura(int idAsignatura){
		return repositorioUsuario.findByNotAsignatura(idAsignatura);
	}
}
