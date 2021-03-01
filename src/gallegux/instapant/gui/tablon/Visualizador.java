package gallegux.instapant.gui.tablon;



import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Visualizador extends JFrame 
{
	
	
	public Visualizador(BufferedImage i)
	{
		super();
		
		ImageIcon ii = new ImageIcon(i);
		JLabel lbl = new JLabel(ii);
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(lbl, BorderLayout.CENTER);
		
		setContentPane(p);
		pack();
	}

}
