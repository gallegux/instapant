package gallegux.instapant.ed;

import java.util.ArrayList;
import java.util.List;

public class Grupo {

	String nombre;
	List<String> miembros = new ArrayList<>();
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<String> getMiembros() {
		return miembros;
	}
	public void setMiembros(List<String> miembros) {
		this.miembros = miembros;
	}
	
}
