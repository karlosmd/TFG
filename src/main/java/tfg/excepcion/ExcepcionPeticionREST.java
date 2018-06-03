package tfg.excepcion;

public class ExcepcionPeticionREST extends Exception {
	private int codigoEstadoPeticion;
	
	public ExcepcionPeticionREST(int codigoEstadoPeticion) {
		super();
		this.codigoEstadoPeticion = codigoEstadoPeticion;
	}
	
	@Override
	public String getMessage() {
		return "Error " + codigoEstadoPeticion + ". No se ha podido establecer conexión.";
	}
}
