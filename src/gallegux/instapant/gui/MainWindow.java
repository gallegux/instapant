package gallegux.instapant.gui;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import gallegux.instapant.Version;
import gallegux.instapant.ed.Configuracion;
import gallegux.instapant.ed.Usuario;
import gallegux.instapant.gui.publicar.PanelPublicar;
import gallegux.instapant.gui.tablon.PanelPublicaciones;
import javafx.geometry.Insets;



public class MainWindow extends JFrame implements WindowListener, ComponentListener
{

	PanelPublicaciones panelPublicaciones = new PanelPublicaciones();
	PanelContactosGrupos panelCCyGG = new PanelContactosGrupos();
	PanelPublicar panelPublicar = new PanelPublicar();
	JTabbedPane tabs = new JTabbedPane();
	int anchuraAnterior = 0;
	
	
	public MainWindow() 
	{
		super( String.format("%s  %s  -  %s", Version.APP_NAME, Version.IDENT, Usuario.getInstance().getNombre()) );
		
		
		try {
			setIconImage( Recursos.loadIcon("/gallegux/instapant/gui/instapant_icon.png"));
		}
		catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			Rectangle r = Configuracion.getInstance().getWindowBounds();
			setBounds(r);
		}
		catch (Exception ex) {
			setSize(700,700);
			setLocationRelativeTo(null);
		}
		
		
		setResizable(true);
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(400, 400));
		//tabs.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		tabs.add("Tablón", panelPublicaciones);
		tabs.add("Publicar", panelPublicar);
		tabs.add("Contactos y Grupos", panelCCyGG);
		
		setContentPane(tabs);
		 
		addComponentListener(this);
		addWindowListener(this);
	}


	public PanelPublicaciones getPanelPublicaciones() {
		return panelPublicaciones;
	}

	public PanelPublicar getPanelPublicar() {
		return panelPublicar;
	}


	@Override
	public void windowActivated(WindowEvent e) {}


	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		Configuracion cfg = Configuracion.getInstance();
		cfg.setWindowBounds(getBounds());
		cfg.grabar();
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
	public void componentHidden(ComponentEvent arg0) {}


	@Override
	public void componentMoved(ComponentEvent arg0) {}


	@Override
	public void componentResized(ComponentEvent arg0) {
		if (anchuraAnterior != getWidth()) {
			this.panelPublicaciones.redim();
			this.panelPublicar.redim();
			anchuraAnterior = getWidth();
		}
	}


	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
