package tfg.modelo;

public class Resultado {
	private String alumno;
	private String reto;
	private String pregunta;
	private String respuesta;
	private Boolean correcta;
	private double tiempo;
	
	public Resultado() {}
	
	public Resultado(String alumno, String reto, String pregunta, String respuesta, Boolean correcta, double tiempo) {
		super();
		this.alumno = alumno;
		this.reto = reto;
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.correcta = correcta;
		this.tiempo = tiempo;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public String getReto() {
		return reto;
	}

	public void setReto(String reto) {
		this.reto = reto;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Boolean getCorrecta() {
		return correcta;
	}

	public void setCorrecta(Boolean correcta) {
		this.correcta = correcta;
	}

	public double getTiempo() {
		return tiempo;
	}

	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}
}
