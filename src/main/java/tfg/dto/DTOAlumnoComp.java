package tfg.dto;

import java.util.Comparator;

public class DTOAlumnoComp implements Comparator<DTOAlumno>{
 
    @Override
    public int compare(DTOAlumno dtoAlumno1, DTOAlumno dtoAlumno2) {
        if(dtoAlumno1.getPuntuacion() < dtoAlumno2.getPuntuacion())
            return 1;
        else 
            return -1;
    }
}
