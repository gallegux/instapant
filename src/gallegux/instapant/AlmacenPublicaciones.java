package gallegux.instapant;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gallegux.instapant.ed.Publicacion;
import gallegux.instapant.ed.Usuario;
import gallegux.instapant.gui.tablon.PanelPublicaciones;



public class AlmacenPublicaciones
{

	private static List<Publicacion> publicaciones = new ArrayList<>();
	private static PanelPublicaciones panel = null;
	
	static {
		new HiloEliminarAntiguas().start();
	}
	

	
	
	public static void setPanelPublicaciones(PanelPublicaciones pp) {
		panel = pp;
	}
	
	
	public static void add(Publicacion p) {
		publicaciones.add(p);
		
		if (panel != null) {
			panel.add(p);
		}
	}
	
	
	public static void del(Publicacion p) {
		publicaciones.remove(p);
	}
	
	
	
	public static List<Publicacion> getPublicaciones(String uidAutor)
	{
		List<Publicacion> pp = new ArrayList<>();
		
		for (Publicacion p: publicaciones) {
			if (p.getAutor().getUid().equals(uidAutor)) {
				pp.add(p);
			}
		}
		
		return pp;
	}
	
	
	
	private static class HiloEliminarAntiguas extends Thread
	{
		public void run() {
			while (true) {
				
				// espera de 1 minuto
				try { sleep(60 * 60 * 1000); } catch (Exception ex) {}
				
				eliminarAntiguos();
			}
		}

		private void eliminarAntiguos()
		{
			ArrayList<Publicacion> eliminar = new ArrayList<>();
			Date d = new Date( System.currentTimeMillis() - 60 * 60 * 1000 );
			boolean repaint = false;
			
			for (Publicacion p: publicaciones) {
				if (p.getFecha().getTime() < d.getTime()) {
					eliminar.add(p);
					repaint = true;
				}
			}
			for (Publicacion p: eliminar) del(p);
			if (panel != null  &&  repaint) panel.repaint();
		}
	}
	
	
	
}
