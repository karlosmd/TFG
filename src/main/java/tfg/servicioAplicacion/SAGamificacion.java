package tfg.servicioAplicacion;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import tfg.excepcion.ExcepcionPeticionHTTP;
import tfg.modelo.Alumno;
import tfg.modelo.Asignatura;
import tfg.modelo.Insignia;
import tfg.modelo.Reto;
import tfg.modelo.Variable;

public interface SAGamificacion {
	public void iniciarSesionGamificacion() throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
	public void crearUsuario(Alumno alumno) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
	public void crearJuego(Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP, ExcepcionPeticionHTTP;
	public void crearAchievement(Insignia insignia, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP, ExcepcionPeticionHTTP;
	public void crearRanking(Asignatura asignatura, Variable variable) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
	public void comprobarReward(Asignatura asignatura, Alumno alumno, List<Insignia> listaInsignias) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
	public void cogerAchievement(List<Insignia> listaInsignias, Asignatura asignatura) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP, ExcepcionPeticionHTTP;
	public void cogerRankingVersionMejorada(Asignatura asignatura, Variable variable, Alumno alumno) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
	public void exportarResultados(Reto reto, String resultados) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
	public void mandarResultado(Reto reto, Alumno alumno, String nombre, int valor) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
	public void inicializarRanking(Asignatura asignatura, Alumno alumno, Variable variable, int valor) throws ClientProtocolException, IOException, ExcepcionPeticionHTTP;
	}
