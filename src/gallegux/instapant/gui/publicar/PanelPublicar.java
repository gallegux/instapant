package gallegux.instapant.gui.publicar;



import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import gallegux.instapant.Enviador;
import gallegux.instapant.ed.Contacto;
import gallegux.instapant.ed.Contactos;
import gallegux.instapant.ed.Grupo;
import gallegux.instapant.ed.Publicacion;
import gallegux.instapant.ed.Usuario;
import gallegux.instapant.gui.GBC;
import gallegux.instapant.gui.PanelFoto;



public class PanelPublicar extends JPanel implements ActionListener
{
	
	PanelCapturas panelCapturas = null;
	JTextField tfTitulo = null;
	JTextField tfComentarios = null;
	JTextField tfDestinatarios = null;
	JButton btCompartir = null;
	JButton btDestinatarios = null;
	private List<Contacto> contactosElegidos = new ArrayList<>();
	private List<Grupo> gruposElegidos = new ArrayList<>();
	
	
	
	public PanelPublicar() {
		super();
		
		this.panelCapturas = new PanelCapturas();
		this.tfTitulo = new JTextField();
		this.tfComentarios = new JTextField();
		this.tfDestinatarios = new JTextField();
		this.tfDestinatarios.setEditable(false);
		this.btDestinatarios = new JButton("Destinatarios:");
		this.btCompartir = new JButton("Compartir");
		
		this.btDestinatarios.addActionListener(this);
		this.btCompartir.addActionListener(this);
		
		build();
	}
	
	
	
	private void build()
	{
		setLayout(new BorderLayout());
		
		JPanel form = buildFormulario();
		
		add(this.panelCapturas, BorderLayout.CENTER);
		add(form, BorderLayout.SOUTH);
		
	}
	
	
	private JPanel buildFormulario()
	{
		JPanel p = new JPanel(new GridBagLayout());
		String insets = " insets=5,5,5,5";
		
		p.add(new JLabel("Título:"), new GBC("grid=0,0"+insets));
		p.add(this.tfTitulo, new GBC("grid=1,0 fill=HORIZONTAL weightx=1"+insets));

//		p.add(new JLabel("Comentarios:"), new GBC("grid=0,1"+insets));
//		p.add(this.tfComentarios, new GBC("grid=1,1 fill=HORIZONTAL weightx=1"+insets));

		p.add(this.btDestinatarios, new GBC("grid=0,2"+insets));
		p.add(this.tfDestinatarios, new GBC("grid=1,2 fill=HORIZONTAL weigthx=1"+insets));
		// (
		JPanel pb = new JPanel(new GridBagLayout());
		pb.add(new JLabel(), new GBC("grid=0,0 fill=HORIZONTAL weightx=1"));
		pb.add(new JLabel(), new GBC("grid=2,0 fill=HORIZONTAL weightx=1"));
		pb.add(this.btCompartir, new GBC("grid=1,0"));
		// )
		
		p.add(pb, new GBC("grid=0,3 gridwidth=2 fill=HORIZONTAL weightx=1"+insets));
		
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		return p;
	}

	
	
	public void add(BufferedImage img)
	{
		this.panelCapturas.add(img);
	}

	
	
	public void redim() {
		this.panelCapturas.redim();
	}
	
	
	
	
	private void seleccionarDestinatarios() {
		JFrame fr = (JFrame) SwingUtilities.getWindowAncestor(this);
		DestinatariosDialog dd = new DestinatariosDialog(fr, contactosElegidos, gruposElegidos);
		dd.setLocationRelativeTo(this.btDestinatarios);
		dd.setVisible(true);
		
		if (dd.getRespuestaAceptar()) {
			this.contactosElegidos = dd.getContactosSeleccionados();
			this.gruposElegidos = dd.getGruposSeleccionados();

			StringBuilder s = new StringBuilder();

			for (Contacto e: this.contactosElegidos) {
				s.append(", ");
				s.append(e.getNombre());
			}
			for (Grupo e: this.gruposElegidos) {
				s.append(", ");
				s.append(e.getNombre());
			}
			this.tfDestinatarios.setText(s.substring(2));
		}
		
	}
	
	
	
	private void compartir() 
	{
		List<BufferedImage> imagenes = new ArrayList<>();
		List<Contacto> destinatarios = new ArrayList<>();

		List<PanelFoto> panelesFoto = this.panelCapturas.getSeleccionadas();
		
		for (PanelFoto pf: panelesFoto)	{
			imagenes.addAll(pf.getImagenes());
		}
		
		destinatarios.add(Usuario.getInstance().getContacto()); 
		destinatarios.addAll(this.contactosElegidos);
		
		for (Grupo g: this.gruposElegidos) {
			for (String uid: g.getMiembros()) {
				Contacto c = Contactos.get(uid);
				if (!destinatarios.contains(c))	destinatarios.add(c);
			}
		}
		System.out.println(destinatarios);
		
		if (imagenes.size() > 0  &&  destinatarios.size() > 0) {
			for (PanelFoto pf: panelesFoto)	this.panelCapturas.del(pf);
			this.panelCapturas.repaint();
			this.panelCapturas.revalidate();
			enviar(imagenes, destinatarios);
		}
	}
	
	
	
	private void enviar(List<BufferedImage> imagenes, List<Contacto> destinatarios) 
	{
		(new Thread() {
			public void run() {
				for (BufferedImage i: imagenes) {
					Publicacion pub = new Publicacion();
					pub.addImagen(i);
					pub.setAutor(Usuario.getInstance());
					pub.setFecha(new Date());
					pub.setTitulo(tfTitulo.getText().trim());
					
					for (Contacto c: destinatarios)	pub.addDestinatario(c.getUid());
					
					for (Contacto c: destinatarios) {
						Enviador.enviar(c, pub);
					}
//						try {
//							Socket skt = new Socket(c.getIp(), 7007);
//							OutputStream out = skt.getOutputStream();
//							
//							ObjectOutputStream oos = new ObjectOutputStream(out);
//							oos.writeObject(pub);
//							oos.flush();
//							
//							for (BufferedImage img: pub.getImagenes()) {
//								//System.out.println("Enviar imagen: " + img);
//								ImageIO.write(img, "png", out);
//							}
//							out.flush();
//							skt.close();
//							System.out.println("enviado");
//						}
//						catch (Exception ex) {
//							System.out.println(ex.getMessage() + "  " + c.getIp());
//						}
//					}
				}
			}
		}).start();
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		
		if (boton == this.btDestinatarios) {
			seleccionarDestinatarios();
		}
		else {
			compartir();
		}
	}
	
	

}
