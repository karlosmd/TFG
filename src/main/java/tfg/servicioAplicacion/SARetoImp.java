package tfg.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.DAO.DAOReto;
import tfg.DTO.DTOReto;
import tfg.modelo.Asignatura;
import tfg.modelo.Reto;

@Service("saReto")
public class SARetoImp implements SAReto{
	@Autowired
	private DAOReto daoReto;
	
	// CREATE
	@Override
	public void crearReto(DTOReto dtoReto, Asignatura asignatura) {
		Reto reto = new Reto();
		reto.setNombre(dtoReto.getNombre());
		reto.setEnlace(dtoReto.getEnlace());
		reto.setAsignatura(asignatura);
		daoReto.save(reto);
	}
	
	// READ
	@Override
	public List<Reto> leerPorAsignatura(Asignatura asignatura) {
		return daoReto.findPorAsignatura(asignatura);
	}
	@Override
	public Reto leerPorId(int id) {
		return daoReto.findById(id);
	}
	
	// UPDATE
	@Override
	public void actualizarActivo(int id, int activo) {
		daoReto.updateActivo(id, activo);		
	}
}
