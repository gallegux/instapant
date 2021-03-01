package gallegux.instapant;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JTextArea;

import gallegux.instapant.ed.Contacto;
import gallegux.instapant.ed.Contactos;
import gallegux.instapant.ed.Grupos;



public class GestionContactosGrupos 
{
	
	public final static String FICHERO_CONTACTOS = String.format("%s%scontactos.properties", 
			System.getProperty("user.home"), System.getProperty("file.separator"));
	
	
	
	public static void cargar()
	{
		try {
			FileInputStream fin = new FileInputStream(FICHERO_CONTACTOS);
			Properties p = new Properties();
			p.load(fin);
			Contactos.set(p);
			Grupos.set(p);
			System.out.println("Contactos y grupos cargados");
		}
		catch (Exception ex) {
			System.err.println(ex);
		}
		
	}
	
	
	
	public static void cargar(JTextArea editor)
	{
		try {
			FileInputStream f = new FileInputStream(FICHERO_CONTACTOS);
			byte[] buffer = new byte[50000];
			int leidos = 0;
			int total = 0;
			
			while ((leidos = f.read(buffer, total, 50000-leidos)) != -1) {
				total += leidos;
			}
			
			String s = new String(buffer, 0, total);
			
			editor.setText(s);
			f.close();
		}
		catch (Exception ex) {
			String s = "# CONFIGURACION DE CONTACTOS Y GRUPOS\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"# configuracion de contacto:\r\n" + 
					"# <uid>=<nombre>, <ip>\r\n" + 
					"#\r\n" + 
					"# ejmplos:\r\n" + 
					"#\r\n" + 
					"bbee01=Bob Esponja, 10.132.135.100\r\n" + 
					"ccll02=Calamardo, 10.132.135.101\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"#configuracion de grupos\r\n" + 
					"\r\n" + 
					"# grp_<nombre>=<uid>,<uid>,...\r\n" + 
					"#\r\n" + 
					"# ejemplo:\r\n" + 
					"#\r\n" + 
					"grp_Personajes=bbee01, ccll02\r\n" + 
					"# (grupo Personajes que incluye a Bob Esponja y Calamardo)\r\n" + 
					"\r\n" + 
					"# las lineas que empiezan por # se ignoran";
			editor.setText(s);
		}			
		
	}
	
	
	
	public static void grabar(JTextArea editor)
	{
		try {
			FileOutputStream f = new FileOutputStream(FICHERO_CONTACTOS);
			f.write(editor.getText().getBytes());
			f.flush();
			f.close();
			
			cargar();
		}
		catch (Exception ex) {
			System.err.println(ex);
		}
		
	}
	
	
	
//	final static String FICHERO = "contactos.properties";
//	final static String _UID = ".uid";
//	final static String _NOMBRE = ".nombre";
//	final static String _IP = ".ip";
//	
//	static Properties contactos = new Properties();
//	
//
//	public static void cargarContactos() {
//		try {
//			InputStream in = new FileInputStream(FICHERO);
//			contactos.load(in);
//		}
//		catch (Exception ex) {
//			
//		}
//	}
//	
//	
//	public static void guardarContactos() {
//		try {
//			OutputStream out = new FileOutputStream(FICHERO);
//			contactos.store(out, "");
//		}
//		catch (Exception ex) {
//			
//		}
//	}
//	
//	
//	public static Contacto get(String uid) {
//		Contacto c = new Contacto();
//		
//		c.setUid(uid);
//		c.setNombre(contactos.getProperty(uid + _NOMBRE));
//		c.setIp(contactos.getProperty(uid + _IP));
//		
//		return c;
//	}
	

}
