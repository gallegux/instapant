package gallegux.instapant.gui.tablon;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gallegux.instapant.ed.Publicacion;
import gallegux.instapant.gui.CTEs;
import gallegux.instapant.gui.PanelFoto;



public class PanelPublicacion extends JPanel 
{
	
	
	private Publicacion publicacion = null;
	private JLabel lAutor = null;
	private PanelFoto pImagen = null;
	private JLabel lTitulo = null;

	
	public PanelPublicacion(Publicacion pub) {
		this.publicacion = pub;
		this.build();
	}


	private void build() 
	{
		this.setBackground(Color.WHITE);
		this.setBorder(CTEs.BORDE_PUBLICACION);
		this.setLayout(new BorderLayout(5,5));

		pImagen = new PanelFoto(this.publicacion.getImagenes());
		
		this.add(encabezado(), BorderLayout.NORTH);
		this.add(pImagen, BorderLayout.CENTER);
		
		if (this.publicacion.getTitulo() != null) {
			this.lTitulo = new JLabel(this.publicacion.getTitulo());
			this.lTitulo.setForeground(CTEs.COLOR_TITULO_PUBLICACION);
			this.add(lTitulo, BorderLayout.SOUTH);
		}
	}
	
	
	public void redim(int anchoPanel) {
		this.pImagen.redim(anchoPanel);
		revalidate();
	}
	
	
	
	private JPanel encabezado() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);
		
		JLabel la = new JLabel(this.publicacion.getAutor().getNombre());
		la.setHorizontalAlignment(JLabel.LEFT);
				
		String fecha = CTEs.FORMATO_FECHA.format(this.publicacion.getFecha());
		JLabel lf = new JLabel(fecha);
		lf.setHorizontalAlignment(JLabel.RIGHT);
		lf.setForeground(CTEs.COLOR_FECHA_PUBLICACION);
		
		p.add(la, BorderLayout.WEST);
		p.add(lf, BorderLayout.EAST);
		
		return p;
	}
	
	
	
	
}
