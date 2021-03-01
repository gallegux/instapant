package gallegux.instapant;



import java.awt.Color;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.Enumeration;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

import gallegux.instapant.ed.Contacto;
import gallegux.instapant.ed.Contactos;
import gallegux.instapant.gui.MainWindow;
import gallegux.instapant.gui.ValidacionUsuarioWindow;



public class MainIP 
{

	
	public static void main(String...arg) 
	{
		try {
			UIManager.getDefaults().put("TabbedPane.tabInsets", new Insets(5, 20, 5, 20));
			UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(-1, -1, -2, -2));
			UIManager.getDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(50, 0, 50, 0));
			UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(0, 0, 0, 0));
			UIManager.getDefaults().put("TabbedPane.selectedTabPadInsets", new Insets(25, 20, 25, 120));
			
			UIManager.getDefaults().put("TextField.margin", new Insets(2, 2, 2, 2));
			UIManager.getDefaults().put("PasswordField.margin", new Insets(2, 2, 2, 2));
			
			UIManager.getDefaults().put("TextField.inactiveBackground", new Color(245,245,245));
			
			UIDefaults d = UIManager.getDefaults();
			Enumeration en = d.keys();
			while (en.hasMoreElements()) {
				String k = en.nextElement().toString();
				Object v = d.get(k);
				if (k.toLowerCase().indexOf("textfield") != -1)
					System.out.printf("%s = %s\n", k, v);
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		new MainIP().main();
		
	}
	
	

	MainWindow window = null;
	AlmacenPublicaciones publicaciones = null;
	
	
	public void main()
	{
		ValidacionUsuarioWindow vw = new ValidacionUsuarioWindow(this);
		vw.setLocationRelativeTo(null);
		vw.setVisible(true);
		
	}
	
	
	public void main2()
	{
		
		// carga usuarios
		GestionContactosGrupos.cargar();		
		
		// almacen de publicaciones
		this.publicaciones = new AlmacenPublicaciones();
//		this.publicaciones.start();
		
		// servidor
		new Servidor(publicaciones).start();
		System.out.println("Servidor escuchando...");
		
		// capturador en el portapapeles
		new HiloCapturador().start();
		System.out.println("Hilo capturador!");

		this.window = new MainWindow();
		this.publicaciones.setPanelPublicaciones(this.window.getPanelPublicaciones());
		this.window.setVisible(true);
		
		// saludar
		System.out.println("Saludar...");
		(new Thread() {
			public void run() {
				for (Contacto c: Contactos.getAll()) {
					Enviador.saludar(c);
				}
			}
		}).start();
	}

	
	
//	class HiloEnviador extends Thread
//	{
//		Publicacion publicacion = null;
//		BufferedImage img = null;
//		
//		public HiloEnviador(BufferedImage img) {
//			super();
//			this.img = img;
//		}
//		
//		public HiloEnviador(Publicacion p) {
//			super();
//			super.setName("H_env");
//			this.publicacion = p;
//		}
//		
//		public void run() {
//			List<String> ips = cargarIps();
//			
//			for (String ip: ips) {
//				try {
//					Socket skt = new Socket(ip, 7007);
//					OutputStream out = skt.getOutputStream();
//					
//					ObjectOutputStream oos = new ObjectOutputStream(out);
//					System.out.println("Enviar publicacion: " + this.publicacion);
//					oos.writeObject(this.publicacion);
//					oos.flush();
//					
//					for (BufferedImage img: this.publicacion.getImagenes()) {
//						System.out.println("Enviar imagen: " + img);
//						ImageIO.write(img, "png", out);
//					}
//					out.flush();
//					skt.close();
//					System.out.println("enviado");
//				}
//				catch (Exception ex) {
//					System.out.println(ex.getMessage() + "  " + ip);
//				}
//			}
//			
//			
//		}
//		
//		
//		private List<String> cargarIps() {
//			try {
//				Properties pp = new Properties();
//				FileInputStream in = new FileInputStream("contactos.properties");
//				pp.load(in);
//				
//				Enumeration enu = pp.keys();
//				List<String> ips = new ArrayList<>();
//				
//				while (enu.hasMoreElements()) {
//					String k = enu.nextElement().toString();
//					if (k.endsWith(".ip")) {
//						System.out.println("+"+pp.getProperty(k));
//						ips.add( pp.getProperty(k) );
//					}
//				}
//				return ips;
//			}
//			catch (Exception ex) {
//				ex.printStackTrace();
//				return new ArrayList<>();
//			}
//		}
//	}
	
	
	class HiloCapturador extends Thread
	{
		public void run() {
			try {
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				BufferedImage imgAnterior = null;
				BufferedImage imgCapturada = null;
				Transferable contenido = null;
				
				while (true) {
					sleep(1000);
					
					try {
						contenido = cb.getContents(this);
						imgCapturada = (BufferedImage) contenido.getTransferData(DataFlavor.imageFlavor);
						boolean iguales = equals(imgAnterior, imgCapturada);
						if (imgAnterior == null) {
							imgAnterior = imgCapturada;
						}
						else if (!iguales) {
//							System.out.println("contenido diferente  "+imgCapturada.getWidth()+"x"+imgCapturada.getHeight());
//							
//							Publicacion p = new Publicacion();
//							p.setAutor( Usuario.getInstance().getNombre() );
//							p.setFecha(new Date());
//							p.addImagen(imgCapturada);
//							
//							new HiloEnviador(p).start();
							window.getPanelPublicar().add(imgCapturada);
//							Icon i = new ImageIcon(imgCapturada);
//							JLabel lbl = new JLabel(i);
//							lbl.setPreferredSize(new Dimension(imgCapturada.getWidth(), imgCapturada.getHeight()));
//							JFrame f = new JFrame();
//							f.setContentPane(lbl);
//							f.pack();
//							f.setVisible(true);
//							f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							
//							System.out.println("enviar");
//							Socket skt = new Socket("10.132.103.44", 7007);
//							//ByteArrayOutputStream out = new ByteArrayOutputStream();
//							OutputStream out = skt.getOutputStream();
//							ImageIO.write(imgCapturada, "png", out);
//							out.flush();
//							skt.close();
						}
						
					}
					catch (Exception ex) {
						//System.out.println(ex);
					}
					finally {
						imgAnterior = imgCapturada;
					}
					
				}
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
		
		boolean equals(BufferedImage i1, BufferedImage i2) {
			try {
				if (i1.getWidth() != i2.getWidth()) return false;
				if (i2.getHeight() != i2.getHeight()) return false;
				
				int w = i1.getWidth();
				int h = i2.getHeight();
				
				int[] a1 = i1.getRGB(0, 0, w, h, null, 0, w);
				int[] a2 = i2.getRGB(0, 0, w, h, null, 0, w);
				int l = a1.length;
				
				for (int i = 0; i < l; ++i) {
					if (a1[i] != a2[i]) return false;
				}
				return true;
			}
			catch (Exception x) {
				return false;
			}
		}
		
	}
	
}
