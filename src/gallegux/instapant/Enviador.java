package gallegux.instapant;



import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

import gallegux.instapant.ed.Contacto;
import gallegux.instapant.ed.Publicacion;
import gallegux.instapant.ed.Usuario;
import gallegux.instapant.mensajes.Hola;



public class Enviador {

	
	public static void enviar(Contacto c, Publicacion pub) {
		try {
			Socket skt = new Socket(c.getIp(), 7007);
			OutputStream out = skt.getOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(pub);
			oos.flush();
			
			for (BufferedImage img: pub.getImagenes()) {
				//System.out.println("Enviar imagen: " + img);
				ImageIO.write(img, "png", out);
			}
			out.flush();
			skt.close();
			System.out.println("enviado");
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage() + "  " + c.getIp());
		}
	}
	

	public static void saludar(Contacto c) {
		try {
			System.out.println("Saludar a " + c);
			
			Socket skt = new Socket(c.getIp(), 7007);
			OutputStream out = skt.getOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(out);
			
			Hola hola = new Hola();
			hola.setFromUid( Usuario.getInstance().getUid() );
			hola.setToUid( c.getUid() );
			
			oos.writeObject(hola);
			oos.flush();
			skt.close();
			
			System.out.println("Saludado " + c);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage() + "  " + c.getIp());
		}
	}
	

}
