package gallegux.instapant.ed;



import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Publicacion implements Serializable
{
	
	private Usuario autor = null;
	private Date fecha = null;
	private int numImagenes = 0;
//	private boolean nuevo = true;
//	private String uidDestinatario = null;
	private List<String> uidsDestinatarios = new ArrayList<>();
	private String titulo = null;
	private transient List<BufferedImage> imagenes = null;;
	

	
	public Usuario getAutor() {
		return autor;
	}
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	public String getTitulo() {
		return this.titulo;
	}
	public void setTitulo(String t) {
		this.titulo = t;
	}
	public Date getFecha() {
		return this.fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
//	public void setNuevo(boolean b) {
//		this.nuevo = b;
//	}
//	public boolean isNuevo() {
//		return this.nuevo;
//	}
	public int getNumImagenes() {
		return this.numImagenes;
	}
	
//	public void setDestinatario(String uid) {
//		this.uidDestinatario = uid;
//	}
//	public String getDestinatario() {
//		return this.uidDestinatario;
//	}
	
	public List<BufferedImage> getImagenes() {
		return this.imagenes;
	}
	public void addImagen(BufferedImage imagen) {
		if (this.imagenes == null) {
			this.imagenes = new ArrayList<>();
		}

		if (this.imagenes.size() == this.numImagenes) {
			this.numImagenes++;
		}
		
		this.imagenes.add(imagen);
	}
	
	public void addDestinatario(String uid) {
		this.uidsDestinatarios.add(uid);
	}
	
	public boolean containsDestinatario(String uid) {
		return this.uidsDestinatarios.contains(uid);
	}
	
//	public void addDestinatarios(List<String> uids) {
//		this.uidsDestinatarios.addAll(uids);
//	}
	
	
	@Override
	public String toString() {
		return (this.autor+" "+this.fecha+" "+this.numImagenes);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		
		if (!(o instanceof Publicacion))	return false;
		
		Publicacion p = (Publicacion) o;
		
		if (this.numImagenes != p.getNumImagenes())	return false;
		if (!this.autor.equals(p.getAutor()))	return false;
		if (!this.fecha.equals(p.getFecha()))	return false;
		
		return true;
	}
	
}
