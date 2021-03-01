package gallegux.instapant.gui;



import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;



public class Recursos {

	
	public static Image loadIcon(String fichero) throws Exception
	{
		InputStream in = Recursos.class.getResourceAsStream(fichero);
		BufferedImage img = ImageIO.read(in);
		return img;
	}
	
	
}
