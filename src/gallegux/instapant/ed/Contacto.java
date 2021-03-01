package gallegux.instapant.ed;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Contacto {
	
	private String uid;
	private String nombre;
	private String ip;
	
	transient ObjectInputStream in;
	transient ObjectOutputStream out;
	
	
	@Override
	public String toString() {
		return String.format("{%s,%s,%s}", uid, nombre, ip);
	}
	
	
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

	public ObjectInputStream getIn() {
		return in;
	}
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	public ObjectOutputStream getOut() {
		return out;
	}
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	
	
	
	

}
