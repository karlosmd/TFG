package tfg.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.DAO.DAOAsignatura;
import tfg.DTO.DTOAsignatura;
import tfg.modelo.Asignatura;
import tfg.modelo.Profesor;

@Service ("SAAsignatura")
public class SAAsignaturaImp implements SAAsignatura {
	@Autowired
	private DAOAsignatura daoAsignatura;
	
	// CREATE	
	@Override
	public void crearAsignatura(DTOAsignatura dtoAsignatura, Profesor profesor) {
		Asignatura asignatura = new Asignatura();    
		asignatura.setNombre(dtoAsignatura.getNombre());
		asignatura.setGrupo(dtoAsignatura.getGrupo());
		asignatura.setCurso(dtoAsignatura.getCurso());
		asignatura.setProfesor(profesor);
	    asignatura.setActivo(1);
	    daoAsignatura.save(asignatura);
	}
	
	// READ
	@Override
	public Asignatura leerPorId(int id) {
		return daoAsignatura.findById(id);
	}
	
	@Override
	public List<Asignatura> leerAsignaturasProfesor(int idProfesor){
		return daoAsignatura.findAsignaturasProfesor(idProfesor);
	}
	
	@Override
	public List<Asignatura> leerAsignaturasAlumno(int idAlumno){
		return daoAsignatura.findAsignaturasAlumno(idAlumno);
	}
	
	// UPDATE
	@Override
	public void actualizarActivo(int id, int activo) {
		daoAsignatura.updateActivo(id, activo);
	}
	
	// DELETE
	@Override
	public void borrarPorId(int id) {
		daoAsignatura.deleteById(id);
	}
}
