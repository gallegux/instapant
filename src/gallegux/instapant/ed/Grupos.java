package gallegux.instapant.ed;



import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;



public class Grupos 
{

	private static TreeMap<String, Grupo> hmGrupos = new TreeMap<>();
	
	
	public static Grupo get(String nombreGrupo) {
		return hmGrupos.get(nombreGrupo);
	}
	

	public static void set(Properties p) {
		hmGrupos.clear();
		
		Enumeration<Object> kk = p.keys();
		
		while (kk.hasMoreElements()) {
			String k = kk.nextElement().toString();
			
			if (k.toLowerCase().startsWith("grp_")) {
				try {
					String nombre = k.substring(4);
					String miembros = p.getProperty(k);
					
					Grupo g = createGrupo(nombre, miembros);
					
					hmGrupos.put(nombre, g);
				}
				catch (Exception ex) {
				}
			}			
		}
	}
	
	
	
	private static Grupo createGrupo(String nombre, String miembros) {
		try {
			String[] arrMiembros = miembros.split(",");
			
			Grupo grupo = new Grupo();
			grupo.setNombre(nombre);
			
			for (String m: arrMiembros) {
				String miembro = m.trim();
				grupo.getMiembros().add(miembro);
			}
			
			return grupo;
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	
	
	public static List<Grupo> getAll() 
	{
		Set<Map.Entry<String, Grupo>> set = hmGrupos.entrySet();
		Iterator<Map.Entry<String, Grupo>> it = set.iterator();
		ArrayList<Grupo> grupos = new ArrayList<>();
		
		while (it.hasNext()) {
			grupos.add(it.next().getValue());
		}
		
		return grupos;
	}
	
	
	
}
