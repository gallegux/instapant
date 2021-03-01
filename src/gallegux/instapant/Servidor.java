package gallegux.instapant;



import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.imageio.ImageIO;

import gallegux.instapant.ed.Contacto;
import gallegux.instapant.ed.Contactos;
import gallegux.instapant.ed.Publicacion;
import gallegux.instapant.ed.Usuario;
import gallegux.instapant.mensajes.Hola;



public class Servidor extends Thread 
{
	
	private AlmacenPublicaciones almacenPublicaciones = null;
	
	
	
	public Servidor(AlmacenPublicaciones ap) {
		super();
		super.setName("H_rec");
		this.almacenPublicaciones = ap;
	}
	
	
	
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(7007);
			
			while (true) {
				try {
					Socket sc = ss.accept();
					
					aceptar(sc);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	public void aceptar(Socket s) throws IOException, ClassNotFoundException
	{
		System.out.println("IN " + s.getInetAddress());
		
		OutputStream os = s.getOutputStream();
		InputStream is = s.getInputStream();
		
		ObjectInputStream ois = new ObjectInputStream(is);
		//ObjectOutputStream oos = new ObjectOutputStream(os);
		
		Object o = ois.readObject();
		procesarMensaje(o, is, os);
		
//		outs.put(hola.uid, os);
//		ins.put(hola.uid, is);
	}
	
	
	
	void procesarMensaje(Object m, InputStream in, OutputStream os)
	{
		System.out.println(m.getClass());
		
		if (m instanceof Hola) {
			Hola hola = (Hola) m;
			Usuario usu = Usuario.getInstance();
			Contacto cont = Contactos.get(hola.fromUid);
			
			if (hola.getToUid().equals(usu.getUid())  &&  cont != null) {
				(new Thread() {
					public void run() {
						List<Publicacion> pp = AlmacenPublicaciones.getPublicaciones( Usuario.getInstance().getUid() );
						
						for (Publicacion p: pp) {
							//p.setNuevo(false);
							Enviador.enviar(cont, p);
						}
					}
				}).start();
			}
		}
		else if (m instanceof Publicacion) {
			Usuario usuario = Usuario.getInstance();
			Publicacion publicacion = (Publicacion) m;
			boolean procesar = publicacion.containsDestinatario(usuario.getUid());
			
			if (procesar)	procesarPublicacion(publicacion, in);
//			{
//				if (publicacion.isNuevo()) {
//					procesarPublicacionNueva(publicacion, in);
//				}
//				else {
//					procesarPublicacionAntigua(publicacion, in);
//				}
//			}
		}
	}
	
	
	
	private void procesarPublicacion(Publicacion p, InputStream in)
	{
		try {
			System.out.println("Publicacion recibida: " + p);
			for (int i = 0; i < p.getNumImagenes(); i++) {
				BufferedImage img = ImageIO.read(in);
				System.out.println("Imagen recibida: " + img);
				p.addImagen(img);
			}
			this.almacenPublicaciones.add(p);
		}
		catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
	
	
//	private void procesarPublicacionAntigua(Publicacion p, InputStream in)
//	{
//		
//	}
	
	
	

}
