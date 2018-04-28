package tfg.servicioAplicacion;

import java.util.List;

import tfg.dto.DTOAlumno;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

@Service("saParseExcel")
public class SAParseExcelImp implements SAParseExcel {
    public static final String SAMPLE_XLSX_FILE_PATH = "C:/Users/Jorge/Downloads/Export-Preguntas Variadas-5_4_2018-15_21.xlsx"; 
	
	@Override
	public List<DTOAlumno> getResultadosAlumnos() throws IOException, InvalidFormatException {	
		List<DTOAlumno> listaAlumnos = new ArrayList<DTOAlumno>();
		DTOAlumno dtoAlumno = new DTOAlumno();
		Workbook libro = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
		
		int numeroHojas = libro.getNumberOfSheets();
		int numeroRetos = numeroHojas - 1;
		
		Sheet hoja = libro.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		Row fila = null;
		Cell celda;
		String valorCelda = "";
		
		Iterator<Row> rowIterator = hoja.rowIterator();
        while (rowIterator.hasNext() && !valorCelda.equals("Todos los participantes")) {
            fila = rowIterator.next();
            celda = fila.getCell(0);
            valorCelda = dataFormatter.formatCellValue(celda);
            if(valorCelda.equals("Todos los participantes")) {
            	for(int i = 0; i < 2; i++) {
            		fila = rowIterator.next();
            	}
            }
        }
		
     // Now let's iterate over the columns of the current row
        while (rowIterator.hasNext()) {
        	fila = rowIterator.next();
        	
        	Iterator<Cell> cellIterator = fila.cellIterator();
        	celda = cellIterator.next();
        	valorCelda = dataFormatter.formatCellValue(celda);
        	dtoAlumno.setEmail(valorCelda);
        	celda = cellIterator.next();
        	valorCelda = dataFormatter.formatCellValue(celda);
        	List<String> listaAciertos = new ArrayList<String>(Arrays.asList(valorCelda.split(", ")));
        	List<Integer> listaAciertosInt = new ArrayList<Integer>();
        	for(String acierto : listaAciertos) {
        		listaAciertosInt.add(Integer.parseInt(acierto));
        	}
            dtoAlumno.setAciertos(listaAciertosInt);
        	celda = cellIterator.next();
        	valorCelda = dataFormatter.formatCellValue(celda);
            dtoAlumno.setCertitud(Integer.parseInt(valorCelda));
            celda = cellIterator.next();
        	valorCelda = dataFormatter.formatCellValue(celda);
            dtoAlumno.setTiempo(Integer.parseInt(valorCelda.replaceAll("\\.", "")));
            dtoAlumno.setPuntuacion(listaAciertosInt.size() * 10);
            listaAlumnos.add(dtoAlumno);
        }
		return listaAlumnos;
	}
}
