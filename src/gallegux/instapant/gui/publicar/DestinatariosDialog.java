package gallegux.instapant.gui.publicar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import gallegux.instapant.ed.Contacto;
import gallegux.instapant.ed.Grupo;
import gallegux.instapant.gui.CTEs;



public class DestinatariosDialog extends JDialog implements ActionListener
{
	
	private DestinatariosPanel destPanel = null;
	private boolean respuesta = false;
	private JButton btAceptar = null;
	private JButton btCancelar = null;
	

	public DestinatariosDialog(JFrame owner, List<Contacto> contactos, List<Grupo> grupos)
	{
		super(owner, "Destinatarios", true);
		
		this.destPanel = new DestinatariosPanel(contactos, grupos);
		
		this.btAceptar  = new JButton("Aceptar");
		this.btCancelar = new JButton("Cancelar");
		
		this.btAceptar.addActionListener(this);
		this.btCancelar.addActionListener(this);

		setSize(CTEs.ANCHO_VENT_DEST, CTEs.ALTO_VENT_DEST);
		setResizable(false);
		
		build();
	}
	
	
	void build() 
	{
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		JScrollPane s = new JScrollPane(this.destPanel);
		
		// botonera
		JPanel pb = new JPanel(new GridLayout(1,2,5,5));
		pb.add(this.btAceptar);
		pb.add(this.btCancelar);
		pb.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// panel
		p.add(s, BorderLayout.CENTER);
		p.add(pb, BorderLayout.SOUTH);

		setContentPane(p);
	}

	
	public boolean getRespuestaAceptar() {
		return this.respuesta;
	}
	
	
	public List<Contacto> getContactosSeleccionados() {
		return this.destPanel.getContactosSeleccionados();
	}
	
	
	public List<Grupo> getGruposSeleccionados() {
		return this.destPanel.getGruposSeleccionados();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		this.respuesta = e.getSource() == this.btAceptar;
		setVisible(false);
	}
	
	
	
}
