package gallegux.instapant.gui;



import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gallegux.instapant.gui.publicar.BotonIncluir;
import gallegux.instapant.gui.publicar.PanelCapturas;
import gallegux.instapant.gui.tablon.Visualizador;



public class PanelFoto extends JPanel 
implements MouseListener, ActionListener
{
	
	private List<BufferedImage> imagenes = new ArrayList<>();
	private List<BufferedImage> imagenesRedim = new ArrayList<>();
	private int imagenVisible = 0;
	private boolean eliminable = false;
	private boolean elegible = false;
//	private boolean elegida = true;
	private BotonIncluir btIncluir = null;
	private JButton btEliminar = null;

	
	
	public PanelFoto(BufferedImage img)
	{
		super();
		setOpaque(false);
		setLayout(null);
		
		this.imagenes.add(img);
		this.imagenesRedim.add(img);
		
		this.btEliminar = new JButton("Eliminar");
		this.btEliminar.setForeground(Color.WHITE);
		this.btEliminar.setBackground(Color.BLACK);
		this.btIncluir = new BotonIncluir();
		
		this.btEliminar.addActionListener(this);
		//this.btIncluir.addAc
		
		addMouseListener(this);
	}
	
	
	public PanelFoto(List<BufferedImage> img)
	{
		super();
		setOpaque(false);
		setLayout(null);
		
		this.imagenes = img;
		
		for (int i = 0; i < this.imagenes.size(); i++) {
			this.imagenesRedim.add(img.get(i)); // lista de referencias (no ocupa memoria)
		}
		
		this.btEliminar = new JButton("Eliminar");
		this.btEliminar.setForeground(Color.WHITE);
		this.btEliminar.setBackground(Color.BLACK);
		this.btIncluir = new BotonIncluir();
		
		addMouseListener(this);
	}
	
	
	public List<BufferedImage> getImagenes() {
		return this.imagenes;
	}
	
	
	
	public void setEliminable(boolean b) {
		this.eliminable = b;
		
		if (b) {
			this.add(this.btEliminar);
		}
		else {
			this.remove(this.btEliminar);
		}
	}
	
	public void setElegible(boolean b) {
		this.elegible = b;

		if (b) {
			this.add(this.btIncluir);
		}
		else {
			this.remove(this.btIncluir);
		}
	}
	
	public boolean isElegida() {
		return this.btIncluir.getIncluir();
	}
	
	
	
	public void paint(Graphics g)
	{
		g.drawImage(this.imagenesRedim.get(this.imagenVisible), 0, 0, null);

		super.paint(g);
		
	}
	
	
	public void redim(int anchoPanel)
	{
		if (anchoPanel < this.imagenes.get(this.imagenVisible).getWidth()) {
	//		System.out.println("anchoPanel " + anchoPanel);
			double f = (double ) (this.imagenes.get(this.imagenVisible).getWidth()) / (double) anchoPanel;
	//		System.out.println("factor " + f);
			
			int w = anchoPanel;
			int h = (int) ((this.imagenes.get(this.imagenVisible).getHeight()) / f);
			
	//		System.out.format("%dx%d  %dx%d\n", this.imagen.getWidth(), this.imagen.getHeight(), w, h);
			
			Image r = imagenes.get(this.imagenVisible).getScaledInstance(anchoPanel, h, BufferedImage.SCALE_SMOOTH);
			this.imagenesRedim.set(this.imagenVisible, toBI(r));
		}
		else {
			this.imagenesRedim.set(this.imagenVisible, this.imagenes.get(this.imagenVisible));
		}
		
		if (this.elegible)		this.btIncluir.setBounds(0, 0, CTEs.ANCHO_BOTON_IMAGEN, CTEs.ALTO_BOTON_IMAGEN);
		if (this.eliminable)	this.btEliminar.setBounds(this.getWidth()-CTEs.ANCHO_BOTON_IMAGEN, 0, CTEs.ANCHO_BOTON_IMAGEN, CTEs.ALTO_BOTON_IMAGEN);

	}
	
	
	
	private BufferedImage toBI(Image i)
	{
        BufferedImage bi = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.drawImage(i, 0, 0, null);
        g.dispose();
        return bi;
	}
	
	
	@Override
	public int getWidth() {
		return this.imagenesRedim.get(this.imagenVisible).getWidth();
	}
	
	@Override
	public int getHeight() {
		return this.imagenesRedim.get(this.imagenVisible).getHeight();
	}
	
	@Override
	public Dimension getSize() {
		return new Dimension(getWidth(), getHeight());
	}
	
	@Override
	public Dimension getPreferredSize() {
		return getSize();
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		Visualizador v = new Visualizador(this.imagenes.get(this.imagenVisible));
		v.setVisible(true);
	}



	@Override
	public void mouseEntered(MouseEvent e) {}



	@Override
	public void mouseExited(MouseEvent e) {}



	@Override
	public void mousePressed(MouseEvent e) {}



	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Container c = getParent();
			c.remove(this);
			c.repaint();
			c.revalidate();
		}		
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
}
