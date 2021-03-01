package gallegux.instapant.ed;



import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;



public class Configuracion extends Properties
{
	
	final static String FICHERO_CONFIGURACION = String.format("%s%sinstapant.properties", 
			System.getProperty("user.home"), System.getProperty("file.separator"));
	
	public final static String SERVIDOR = "servidor";
	public final static String PUERTO = "puerto";
	public final static String SSL = "ssl";
	public final static String USUARIO = "usuario";
	public final static String BOUNDS = "bounds";
	
	
	private static Configuracion instancia = null;
	
	
	private Configuracion() {}
	
	
	public static synchronized Configuracion getInstance() {
		if (instancia == null)	instancia = new Configuracion();
		
		return instancia;
	}
	
	
	
	public void setInt(String prop, int i) {
		put(prop, i);
	}
	
	public int getInt(String prop) {
		return (Integer) super.get(prop);
	}
		
	
	public void setServidor(String v) {
		setProperty(SERVIDOR, v);
	}
	
	public String getServidor() {
		return getProperty(SERVIDOR, "");
	}
	
	public void setPuerto(String p) {
		setProperty(PUERTO, p);
	}
	
	public String getPuerto() {
		return getProperty(PUERTO, "");
	}
	
	public void setSSL(boolean v) {
		setProperty(SSL, Boolean.toString(v));
	}
	
	public boolean getSSL() {
		return Boolean.parseBoolean( getProperty(SSL, "false") );
	}
	
	public void setUsuario(String v) {
		setProperty(USUARIO, v);
	}
	
	public String getUsuario() {
		return getProperty(USUARIO, "");
	}
	
	public void setWindowBounds(Rectangle v) {
		setProperty("x", Integer.toString(v.x) );
		setProperty("y", Integer.toString(v.y) );
		setProperty("w", Integer.toString(v.width) );
		setProperty("h", Integer.toString(v.height) );
	}
	
	public Rectangle getWindowBounds() {
		try {
			int x = Integer.parseInt( getProperty("x") );
			int y = Integer.parseInt( getProperty("y") );
			int w = Integer.parseInt( getProperty("w") );
			int h = Integer.parseInt( getProperty("h") );
			
			return new Rectangle(x,y,w,h);
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	
	public void cargar() {
		try {
			System.out.println("fichero="+FICHERO_CONFIGURACION);
			//Properties pp = new Properties();
			FileInputStream fin = new FileInputStream(FICHERO_CONFIGURACION);
			super.load(fin);
			fin.close();
		}
		catch (Exception ex) {
			System.err.println(ex);
		}
	}
	
	
	
	public void grabar() {
		try {
			FileOutputStream fout = new FileOutputStream(FICHERO_CONFIGURACION);
//			GZIPOutputStream gout = new GZIPOutputStream(fout);
			write(SERVIDOR, getProperty(SERVIDOR), fout);
			write(PUERTO,   getProperty(PUERTO), fout);
			write(SSL,      getProperty(SSL), fout);
			write(USUARIO,  getProperty(USUARIO), fout);
			
			write("x",   getProperty("x"), fout);
			write("y",   getProperty("y"), fout);
			write("w",   getProperty("w"), fout);
			write("h",   getProperty("h"), fout);
			fout.flush();
			fout.close();
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	
	private void write(String k, String v, OutputStream out)
	throws IOException
	{
		out.write( String.format("%s=%s\n", k, v).getBytes() );
	}


}
