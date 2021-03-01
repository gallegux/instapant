package gallegux.instapant.mensajes;



import java.io.Serializable;



public class Hola implements Serializable 
{
	
	public String fromUid = null;
	public String toUid = null;
	
	
	public String getFromUid() {
		return fromUid;
	}
	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}
	public String getToUid() {
		return toUid;
	}
	public void setToUid(String toUid) {
		this.toUid = toUid;
	}

}
