package tfg.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import tfg.modelo.Asignatura;
import tfg.modelo.Logro;
import tfg.repositorio.RepositorioLogros;

@Service("saLogro")
public class SALogroImp implements SALogro{
	@Autowired
	private RepositorioLogros RepositorioLogros;
	
	
	// CREATE
	@Override
	public void crearLogro(Logro Logro) {		
		RepositorioLogros.save(Logro);
	}
	
	// READ
	@Override
	public List<Logro> leerPorAsignatura(Asignatura asignatura) {
		return RepositorioLogros.findPorAsignatura(asignatura);
	}
	@Override
	public Logro leerPorId(int id) {
		return RepositorioLogros.findById(id);
	}
	
	// UPDATE
	@Override
	public void modificarLogro(Logro Logro) {
		RepositorioLogros.save(Logro);
	}
}