package gallegux.instapant.gui;



import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import gallegux.instapant.GestionContactosGrupos;



public class PanelContactosGrupos extends JPanel 
implements ActionListener
{
	
	private JTextArea editor = null;
	private JButton btAceptar = null;
	
	
	public PanelContactosGrupos() 
	{
		this.editor = new JTextArea();
		this.btAceptar = new JButton("Aceptar");
		this.btAceptar.addActionListener(this);
		
		this.editor.setFont( this.btAceptar.getFont() );
		
		build();
		
		GestionContactosGrupos.cargar(this.editor);
	}
	
	
	private void build() 
	{

		JScrollPane scr = new JScrollPane(this.editor);
		
		JPanel botonera = new JPanel(new GridBagLayout());
		botonera.add(new JLabel(), new GBC("grid=0,0 fill=HORIZONTAL weightx=1"));
		botonera.add(this.btAceptar, new GBC("grid=1,0"));
		botonera.add(new JLabel(), new GBC("grid=2,0 fill=HORIZONTAL weightx=1"));
		
		setLayout(new BorderLayout(5,5));
		add(scr, BorderLayout.CENTER);
		add(botonera, BorderLayout.SOUTH);
		setBorder(new EmptyBorder(5,5,5,5));
		
	}


	
//	private void cargar()
//	{
//		try {
//			FileInputStream f = new FileInputStream("contactos.properties");
//			byte[] buffer = new byte[50000];
//			int leidos = 0;
//			int total = 0;
//			
//			while ((leidos = f.read(buffer, total, 50000-leidos)) != -1) {
//				total += leidos;
//			}
//			
//			String s = new String(buffer, 0, total);
//			
//			this.editor.setText(s);
//			f.close();
//		}
//		catch (Exception ex) {
//			String s = "# CONFIGURACION DE CONTACTOS Y GRUPOS\r\n" + 
//					"\r\n" + 
//					"\r\n" + 
//					"# configuracion de contacto:\r\n" + 
//					"# <uid>=<nombre>, <ip>\r\n" + 
//					"#\r\n" + 
//					"# ejmplos:\r\n" + 
//					"#\r\n" + 
//					"bbee01=Bob Esponja, 10.132.135.100\r\n" + 
//					"ccll02=Calamardo, 10.132.135.101\r\n" + 
//					"\r\n" + 
//					"\r\n" + 
//					"#configuracion de grupos\r\n" + 
//					"\r\n" + 
//					"# grp_<nombre>=<uid>,<uid>,...\r\n" + 
//					"#\r\n" + 
//					"# ejemplo:\r\n" + 
//					"#\r\n" + 
//					"grp_Personajes=bbee01, ccll02\r\n" + 
//					"# (grupo Personajes que incluye a Bob Esponja y Calamardo)\r\n" + 
//					"\r\n" + 
//					"# las lineas que empizan por # se ignoran";
//			this.editor.setText(s);
//		}
//			
//		
//	}
//	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
//			FileOutputStream f = new FileOutputStream("contactos.properties");
//			f.write(this.editor.getText().getBytes());
//			f.flush();
//			f.close();
//			
//			GestionContactosGrupos.cargar();
			GestionContactosGrupos.grabar(this.editor);
		}
		catch (Exception ex) {
			System.err.println(ex);
		}
	}
	
	
	
	
	

}
