package tfg.servicioAplicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.repositorio.RepositorioAlumnoAsignatura;

@Service("saAlumnoAsignatura")
public class SAAlumnoAsignaturaImp implements SAAlumnoAsignatura{
	@Autowired
	private RepositorioAlumnoAsignatura repositorioAlumnoAsignatura;
	
	@Override
	public int leerId(int idAsignatura, int idAlumno) {
		return repositorioAlumnoAsignatura.findId(idAsignatura, idAlumno);
	}

}
