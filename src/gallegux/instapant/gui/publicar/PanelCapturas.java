package gallegux.instapant.gui.publicar;



import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import gallegux.instapant.AlmacenPublicaciones;
import gallegux.instapant.ed.Publicacion;
import gallegux.instapant.gui.CTEs;
import gallegux.instapant.gui.PanelFoto;
import gallegux.instapant.gui.tablon.PanelPublicacion;



public class PanelCapturas extends JScrollPane 
{
	
	public final static long MAX_MINUTOS = 15;
	public final static long MAX_TIEMPO = MAX_MINUTOS * 60L * 1000L;
	
	JPanel panel = null;
	int contador = 0;
	HiloRedim hiloRedim = null;
	HashMap<PanelFoto, Long> mapFotoHora = new HashMap<>(); 
	
	
	
	public PanelCapturas() {
		super();
		
		super.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		super.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		super.getVerticalScrollBar().setUnitIncrement(25);
		
		this.panel = new JPanel(new GridBagLayout());
		this.panel.setBackground(Color.WHITE);
		this.panel.setBorder(CTEs.BORDE_PUBLICACIONES);
		
		super.setViewportView(this.panel);
		
		new HiloLimpiador().start();
	}
	
	
	
	public void add(BufferedImage img) 
	{
		PanelFoto pf = new PanelFoto(img);
		pf.setElegible(true);
		pf.setEliminable(true);
		pf.redim(super.getViewport().getWidth() - CTEs.ANCHO_BORDE * 2);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = contador++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(CTEs.ANCHO_BORDE, 0, CTEs.ANCHO_BORDE, 0);
			
		pf.setBorder(new EmptyBorder(CTEs.ANCHO_BORDE, CTEs.ANCHO_BORDE, CTEs.ANCHO_BORDE, CTEs.ANCHO_BORDE));
		panel.add(pf, gbc);
		
		repaint();
		revalidate();
		
		this.mapFotoHora.put(pf, System.currentTimeMillis());
	}
	
	
	
	public void del(PanelFoto pf)
	{
		this.panel.remove(pf);
	}
	
	
	public List<PanelFoto> getSeleccionadas() {
		List<PanelFoto> l = new ArrayList<>();
		
		for (Component c: this.panel.getComponents()) {
			if (c instanceof PanelFoto) {
				PanelFoto pf = (PanelFoto) c;
				if (pf.isElegida())	l.add( (PanelFoto) c );
			}
		}
		return l;
	}
	
	
	
//	public void redim() {		
//		int anchoPanel = super.getViewport().getWidth() - CTEs.ANCHO_BORDE * 2;
////		System.out.println(anchoPanel);
//		
//		Component[] cc = this.panel.getComponents();
//		
//		for (Component c: cc) {
//			if (c instanceof PanelFoto) ((PanelFoto) c).redim(anchoPanel);
//		}
//	}
	
	
	public void redim() 
	{
		if (hiloRedim != null)	hiloRedim.parar();
		
		int anchoPanel = super.getViewport().getWidth() - CTEs.ANCHO_BORDE * 2;
		hiloRedim = new HiloRedim(anchoPanel);
		hiloRedim.start();
	}

	
	
	class HiloRedim extends Thread
	{
		private boolean parar = false;
		private int anchoPanel = 0;
		
		public HiloRedim(int anchoPanel) {
			this.anchoPanel = anchoPanel;
		}
		
		public void parar() {
			parar = true;
		}
		
		public void run() {
			try {
				sleep(1000);
				if (!parar) {
					Component[] cc = panel.getComponents();
					
					for (int i = 0; i < cc.length; ++i) {
						try {
							PanelFoto pp = (PanelFoto) cc[i];
							pp.redim(anchoPanel);
						}
						catch (Exception ex) {}
					}
					repaint();
					revalidate();
				}
			}
			catch (Exception ex) {}
		}
	}

	
	
	class HiloLimpiador extends Thread
	{
		public void run() {
			ArrayList<PanelFoto> eliminar = new ArrayList<>();

			while (true) {
				try {
					sleep(60 * 1000);	// 1 minuto
				}
				catch (Exception ex) {}
				
				Set<Entry<PanelFoto, Long>> set = mapFotoHora.entrySet();
				Iterator<Entry<PanelFoto, Long>> i = set.iterator();
				long time = System.currentTimeMillis();
				boolean eliminados = false;
				
				while (i.hasNext()) {
					Entry<PanelFoto, Long> entry = i.next();
					if (time - entry.getValue() > MAX_TIEMPO) {
						panel.remove(entry.getKey());
						//eliminar.add(entry.getKey());
						mapFotoHora.remove(entry.getKey());
						eliminados = true;
					}
				}
				
				// los eliminamos del map
				for (PanelFoto p: eliminar) {
					mapFotoHora.remove(p);
				}
				
				if (eliminados) {
					repaint();
					revalidate();
				}
			}
		}
	}


}
