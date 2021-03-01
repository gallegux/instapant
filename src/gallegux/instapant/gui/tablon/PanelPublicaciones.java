package gallegux.instapant.gui.tablon;



import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import gallegux.instapant.AlmacenPublicaciones;
import gallegux.instapant.ed.Publicacion;
import gallegux.instapant.gui.CTEs;



public class PanelPublicaciones extends JScrollPane 
{
	JPanel panel = null;
	int contadorViejas = 0;
	int contadorNuevas = 10_000;
	AlmacenPublicaciones almacenPublicaciones = null;
	HiloRedim hiloRedim = null;

	
	
	
	public PanelPublicaciones() {
		super();
		
		super.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		super.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		super.getVerticalScrollBar().setUnitIncrement(25);
		
		this.panel = new JPanel(new GridBagLayout());
		this.panel.setBackground(Color.WHITE);
		this.panel.setBorder(CTEs.BORDE_PUBLICACIONES);
		
		super.setViewportView(this.panel);
	}
	
	
	
	public void add(Publicacion p) {
		PanelPublicacion pp = new PanelPublicacion(p);
		pp.redim(super.getViewport().getWidth() - CTEs.ANCHO_BORDE * 2);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = contadorNuevas++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
			
		panel.add(pp, gbc);
		
		repaint();
		revalidate();
	}
	
	
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
							PanelPublicacion pp = (PanelPublicacion) cc[i];
							pp.redim(anchoPanel);
						}
						catch (Exception ex) {}
					}
				}
			}
			catch (Exception ex) {}
		}
	}
	
	


}
