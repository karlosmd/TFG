package tfg.entidades;

import java.nio.charset.StandardCharsets;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.DatatypeConverter;

@Entity
@Table(name = "passwords")
public class Password {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	
	private String password;
	private String sal;
	
	@OneToOne(mappedBy="password")
	private Usuario usuario;
	
	@Transient
	private static final String metodoHash = "PBKDF2WithHmacSHA512";
	@Transient
	private static final int iteraciones = 50000;
	@Transient
	private static final int numBytes = 128;
	
	public Password(String passTextoPlano){
		sal = generarSal();
		password = encriptar(passTextoPlano);
	}
	
	private String generarSal() {
		SecureRandom random = new SecureRandom();
        byte[] sal = new byte[numBytes];
        random.nextBytes(sal);
        
        return toHex(sal);
	}
	
	private String encriptar(String passTextoPlano) {
		try {
            SecretKeyFactory instancia = SecretKeyFactory.getInstance( metodoHash );
            PBEKeySpec spec = new PBEKeySpec( passTextoPlano.toCharArray(), toByte(sal), iteraciones, numBytes );
            SecretKey key = instancia.generateSecret( spec );
            byte[] password = key.getEncoded( );
            return toHex(password);
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
	}
	
	public static String toHex(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}

	public static byte[] toByte(String s) {
	    return DatatypeConverter.parseHexBinary(s);
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
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
