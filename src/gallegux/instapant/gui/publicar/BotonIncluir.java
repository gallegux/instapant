package gallegux.instapant.gui.publicar;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;



public class BotonIncluir extends JButton implements ActionListener 
{

	private boolean incluir = true;
	
	
	public BotonIncluir() {
		super();
		setText("Incluir");
		updateColor();
		addActionListener(this);
	}
	
	
	
	public boolean getIncluir() {
		return this.incluir;
	}

	
	private void updateColor() {
		if (this.incluir) {
			setBackground(Color.GREEN);
		}
		else {
			setBackground(Color.RED);
		}		
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.incluir = !this.incluir;
		updateColor();
	}
	

}
