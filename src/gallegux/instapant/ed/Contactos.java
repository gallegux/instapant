package gallegux.instapant.ed;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;



public class Contactos 
{
	
	private static TreeMap<String, Contacto> hmUidContacto = new TreeMap<>();
	
	
	public static void set(String uid, Contacto c) {
		hmUidContacto.put(uid, c);
	}
	
	
	public static Contacto get(String uid) {
		return hmUidContacto.get(uid);
	}
	

	public static void set(Properties p) {
		hmUidContacto.clear();
		
		Enumeration<Object> kk = p.keys();
		
		while (kk.hasMoreElements()) {
			String k = kk.nextElement().toString();
			
			if (k.toLowerCase().startsWith("grp_")) {
			}
			else {
				// contacto
				Contacto c = createContacto(k, p.getProperty(k));
				hmUidContacto.put(k, c);
			}
		}
	}
	
	
	
	private static Contacto createContacto(String uid, String datos) {
		try {
			String[] partes = datos.split(",");
			String nombre = partes[0].trim();
			String ip = partes[1].trim();
			
			Contacto c = new Contacto();
			c.setUid(uid);
			c.setNombre(nombre);
			c.setIp(ip);
			
			return c;
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	
	
	public static List<Contacto> getAll() 
	{
		Set<Map.Entry<String, Contacto>> set = hmUidContacto.entrySet();
		Iterator<Map.Entry<String, Contacto>> it = set.iterator();
		ArrayList<Contacto> contactos = new ArrayList<>();
		
		while (it.hasNext()) {
			contactos.add(it.next().getValue());
		}
		
		return contactos;
	}
	
	
}
