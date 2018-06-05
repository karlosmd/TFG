package tfg.excepcion;

public class ExcepcionPeticionHTTP extends Exception {
	private int codigoEstadoPeticion;
	
	public ExcepcionPeticionHTTP(int codigoEstadoPeticion) {
		super();
		this.codigoEstadoPeticion = codigoEstadoPeticion;
	}
	
	@Override
	public String getMessage() {
		return "Error " + codigoEstadoPeticion + ". No se ha podido establecer conexión.";
	}
}
