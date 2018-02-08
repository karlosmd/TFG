package tfg.modelos;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passwords")
public class Password {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Integer id;	
	private String password;
	private String sal;
	private static final String metodoHash = "PBKDF2WithHmacSHA512";
	private static final int iteraciones = 50000;
	private static final int bytes = 512;
	
	public Password(String passTextoPlano){
		sal = "sdfgdfgf";
		password = encriptar(passTextoPlano);
	}
	
	private String generarSal() {
		SecureRandom random = new SecureRandom();
        byte[] sal = new byte[bytes];
        random.nextBytes(sal);

        return new String(sal);
	}
	
	private String encriptar(String passTextoPlano) {
		try {
            SecretKeyFactory instancia = SecretKeyFactory.getInstance( metodoHash );
            PBEKeySpec spec = new PBEKeySpec( passTextoPlano.toCharArray(), sal.getBytes(), iteraciones, bytes );
            SecretKey key = instancia.generateSecret( spec );
            byte[] password = key.getEncoded( );
            return "dsfgdfg";
            //return password.toString();
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSal() {
		return sal;
	}

	public void setSal(String sal) {
		this.sal = sal;
	}

	public static String getMetodohash() {
		return metodoHash;
	}

	public static int getIteraciones() {
		return iteraciones;
	}

	public static int getBytes() {
		return bytes;
	}
}
