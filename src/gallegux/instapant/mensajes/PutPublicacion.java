package gallegux.instapant.mensajes;

import gallegux.instapant.ed.Publicacion;

public class PutPublicacion {

	Publicacion publicacion = null;
	
	public PutPublicacion(Publicacion p) {
		this.publicacion = p;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
	
	
}
