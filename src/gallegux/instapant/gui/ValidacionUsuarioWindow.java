package gallegux.instapant.gui;



import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gallegux.instapant.GetUsuarioLDAP;
import gallegux.instapant.MainIP;
import gallegux.instapant.Version;
import gallegux.instapant.ed.Configuracion;
import gallegux.instapant.ed.Usuario;



public class ValidacionUsuarioWindow extends JFrame
implements ActionListener, WindowListener, KeyListener
{
	
	final String FICHERO_CONFIGURACION = String.format("%s%sinstapant.properties", 
			System.getProperty("user.home"), System.getProperty("file.separator"));
	
	
	JTextField tfServidor = null;
	JTextField tfPuerto = null;
	JCheckBox chkSSL = null;
	JTextField tfUsuario = null;
	JPasswordField tfContrasenia = null;
	JCheckBox chkGuardarConfiguracion = null;
	JButton btAceptar = null;
	boolean validado = false;
	MainIP main = null;
	
	
	public ValidacionUsuarioWindow(MainIP main)
	{
		super( String.format("%s  %s", Version.APP_NAME, Version.IDENT) );

		setSize(450,360);
		setResizable(false);
		
		try {
			setIconImage( Recursos.loadIcon("/gallegux/instapant/gui/instapant_icon.png"));
		}
		catch (Exception ex) {
			System.err.println(ex);
		}
		
		this.tfServidor = new JTextField();
		this.tfPuerto = new JTextField();
		this.chkSSL = new JCheckBox("SSL");
		this.tfUsuario = new JTextField();
		this.tfContrasenia = new JPasswordField();
		this.chkGuardarConfiguracion = new JCheckBox("Guardar datos conexión");
		this.chkGuardarConfiguracion.setSelected(true);
		this.btAceptar = new JButton("Aceptar");
		this.btAceptar.addActionListener(this);
		
		this.tfServidor.addKeyListener(this);
		this.tfPuerto.addKeyListener(this);
		this.tfUsuario.addKeyListener(this);
		this.tfContrasenia.addKeyListener(this);
		
		addWindowListener(this);
		
		build();
		
		this.main = main;
		
		Configuracion config = Configuracion.getInstance();
		config.cargar();

		this.tfServidor.setText( config.getServidor() );
		this.tfPuerto.setText( config.getPuerto() );
		this.chkSSL.setSelected( config.getSSL() );
		this.tfUsuario.setText( config.getUsuario() );
	
	}
	
	
	
	@Override
	public void setVisible(boolean v) {
		super.setVisible(v);
		if (!this.tfUsuario.getText().equals(""))	this.tfContrasenia.grabFocus();
	}
	
	
 	private void build()
	{
		JLabel l1 = new JLabel("Validación LDAP");
		JLabel l2 = new JLabel("Usuario:");
		JLabel l3 = new JLabel("Contraseña:");
		JLabel l4 = new JLabel("Servidor:");
		JLabel l5 = new JLabel("Puerto:");
		
		l1.setFont( l1.getFont().deriveFont(l1.getFont().getSize2D()*1.4f) );
		
		JPanel p = new JPanel(new GridBagLayout());
		
		p.add(l1, new GBC("grid=0,0 gridwidth=3 anchor=CENTER insets=5,5,20,5"));
		
		
		p.add(l4, new GBC("grid=0,1 anchor=LINE_START insets=5,5,5,5"));
		p.add(this.tfServidor, new GBC("grid=1,1 gridwidth=2 fill=HORIZONTAL weightx=1 insets=5,5,5,5"));
		
		p.add(l5, new GBC("grid=0,2 insets=5,5,5,5 anchor=LINE_START"));
		p.add(this.tfPuerto, new GBC("grid=1,2 anchor=LINE_START fill=HORIZONTAL weightx=0.4 insets=5,5,5,5"));
		p.add(this.chkSSL, new GBC("grid=2,2 fill=HORIZONTAL weightx=0.5  insets=5,50,5,5"));
		
		p.add(l2, new GBC("grid=0,3 insets=5,5,5,5 anchor=LINE_START"));
		p.add(this.tfUsuario, new GBC("grid=1,3 gridwidth=2 fill=HORIZONTAL weightx=1 insets=5,5,5,5"));
		p.add(l3, new GBC("grid=0,4 insets=5,5,5,5 anchor=LINE_START"));
		p.add(this.tfContrasenia, new GBC("grid=1,4 gridwidth=2 fill=HORIZONTAL weightx=1 insets=5,5,5,5"));
		
		p.add(this.chkGuardarConfiguracion, new GBC("grid=0,5 gridwidth=3 anchor=CENTER insets=15,5,10,5"));
		
		p.add(this.btAceptar, new GBC("grid=0,6 gridwidth=3 anchor=CENTER insets=15,5,5,5"));
		
		p.setBorder(new EmptyBorder(10, 30, 10, 30));
		setContentPane(p);
	}

	

	public boolean isValidado() {
		return this.validado;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("!");
		System.out.println("USR: " + this.tfUsuario.getText());
//		System.out.println("PWD: " + this.tfContrasenia.getText());
		
		try {
			GetUsuarioLDAP g = new GetUsuarioLDAP(this.tfServidor.getText().trim(), this.tfPuerto.getText().trim(),
					this.chkGuardarConfiguracion.isSelected(),
					this.tfUsuario.getText().trim(), this.tfContrasenia.getText());
			g.check();
			String uid = g.getUID();
			String nombre = g.getNombreUsuario();
			System.out.printf("%s %s\n",  uid, nombre);
			
			if (nombre != null) {
				Usuario u = Usuario.getInstance();
				u.setUid(uid);
				u.setNombre(nombre);
				System.out.println("Guardar? "+this.chkGuardarConfiguracion.isSelected());
				if (this.chkGuardarConfiguracion.isSelected()) {
					Configuracion cfg = Configuracion.getInstance();
					cfg.setServidor( this.tfServidor.getText().trim() );
					cfg.setPuerto( this.tfPuerto.getText().trim() );
					cfg.setSSL( this.chkGuardarConfiguracion.isSelected() );
					cfg.setUsuario( this.tfUsuario.getText().trim() );
					cfg.grabar();
				}
				
				dispose();
				this.main.main2();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}



	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}



	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == 10) {
			this.btAceptar.doClick();
		}
	}



	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}
	

}
