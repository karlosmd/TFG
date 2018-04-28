package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import tfg.dto.DTOAlumno;

public interface SAParseExcel {
	public List<DTOAlumno> getResultadosAlumnos() throws IOException, InvalidFormatException;
}
