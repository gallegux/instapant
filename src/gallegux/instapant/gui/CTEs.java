package gallegux.instapant.gui;

import java.awt.Color;
import java.text.SimpleDateFormat;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CTEs {
	
	public final static int ANCHO_BORDE = 20;

	public final static Border BORDE_PUBLICACION = new EmptyBorder(ANCHO_BORDE, 0, ANCHO_BORDE, 0);

	public final static Border BORDE_PUBLICACIONES = new EmptyBorder(0, ANCHO_BORDE, 0, ANCHO_BORDE);
	
	public final static SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("HH:MM");
	
	public final static Color COLOR_FECHA_PUBLICACION = new Color(128,128,128);
	public final static Color COLOR_TITULO_PUBLICACION = new Color(128,0,0);
	
	public final static int ANCHO_BOTON_IMAGEN = 90;
	public final static int ALTO_BOTON_IMAGEN = 28;
	
	public final static int ANCHO_VENT_DEST = 200;
	public final static int ALTO_VENT_DEST = 250;
}
