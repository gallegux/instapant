package gallegux.instapant.ed;


import java.io.Serializable;
import java.net.InetAddress;
import java.util.Properties;


public class Usuario implements Serializable 
{

	private static Usuario instancia = null;
	
	
	public String uid=null, nombre=null, ip=null;
	
	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public Usuario() {
		try {
			setIp( InetAddress.getLocalHost().getHostAddress() );
			System.out.println("IP "  + getIp());
		}
		catch (Exception ex) {
			System.err.println(ex);
		}
	}
	
	
	public Contacto getContacto() {
		Contacto c = new Contacto();
		c.setIp(getIp());
		c.setNombre(getNombre());
		c.setUid(getUid());
		
		return c;
	}
	

//	public Usuario(Properties p) {
//		setUid(p.getProperty("uid"));
//		setNombre(p.getProperty("nombre"));
//		setIp(p.getProperty("ip"));
//	}
	
	
	
	public static synchronized Usuario getInstance() {
		if (instancia == null) {
			instancia = new Usuario();
		}
		return instancia;
	}
	
//	public static void setInstance(Usuario u) {
//		instancia = u;
//	}
	
	
}
