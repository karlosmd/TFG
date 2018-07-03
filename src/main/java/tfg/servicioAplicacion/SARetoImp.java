package tfg.servicioAplicacion;

import java.util.List;

import javax.transaction.Transactional;

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
import tfg.modelo.Reto;
import tfg.repositorio.RepositorioReto;

@Service("saReto")
@Transactional
public class SARetoImp implements SAReto{
	@Autowired
	private RepositorioReto repositorioReto;
	
	public static final String baseUrl = "http://localhost:8000";
	
	// CREATE
	@Override
	public void crearReto(Reto reto) {
		repositorioReto.save(reto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String url = baseUrl + "/api/reto/" + reto.getId() + "/crear";

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("nombre", reto.getNombre());
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity( url, request, String.class );
		System.out.println(response);		
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
	public void modificarReto(Reto reto) {
		repositorioReto.save(reto);
	}
}
