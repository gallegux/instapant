package gallegux.instapant.gui.publicar;



import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import gallegux.instapant.ed.Contacto;
import gallegux.instapant.ed.Contactos;
import gallegux.instapant.ed.Grupo;
import gallegux.instapant.ed.Grupos;
import gallegux.instapant.gui.GBC;



public class DestinatariosPanel extends JPanel 
{
	
	private List<CheckContacto> checksC = new ArrayList<>();
	private List<CheckGrupo> checksG = new ArrayList<>();
//	private List<SeleccionGrupo> chkGrupos = new ArrayList<>();
	
	
	public DestinatariosPanel(List<Contacto> contactos, List<Grupo> grupos) {
		super();
		
		build(contactos, grupos);
	}
	
	
	
	private void build(List<Contacto> cc, List<Grupo> gg) 
	{
		int contador = 0;
		setLayout(new GridBagLayout());
		
		List<Contacto> contactos = Contactos.getAll();
		List<Grupo> grupos = Grupos.getAll();
		
		for (Contacto c: contactos) {
			CheckContacto chk = new CheckContacto(c);
			checksC.add(chk);
			add(chk, new GBC("anchor=LINE_START").setGrid(0, contador++));
			
			for (Contacto s: cc) {
				if (s.getUid().equals(c.getUid())) {
					chk.setSelected(true);
					break;
				}
			}
//			chk.setSelected( cc.contains(c.getNombre()) );
		}
		
		for (Grupo c: grupos) {
			CheckGrupo chk = new CheckGrupo(c);
			checksG.add(chk);
			add(chk, new GBC("anchor=LINE_START").setGrid(0, contador++));
			
			for (Grupo s: gg) {
				if (s.getNombre().equals(c.getNombre())) {
					chk.setSelected(true);
					break;
				}
			}
			//chk.setSelected( cc.contains(c.getNombre()) );
		}
		
	}
	
	
	
	public List<Contacto> getContactosSeleccionados() {
		ArrayList<Contacto> l = new ArrayList<>();
		
		for (CheckContacto k: checksC) {
			if (k.isSelected()) {
				l.add(k.getContacto());
			}
		}
		
		return l;
	}
	
	
	public List<Grupo> getGruposSeleccionados() {
		ArrayList<Grupo> l = new ArrayList<>();
		
		for (CheckGrupo k: checksG) {
			if (k.isSelected()) {
				l.add(k.getGrupo());
			}
		}
		
		return l;
	}
	
	
	
	class CheckContacto extends JCheckBox
	{
		private Contacto contacto = null;
		
		public CheckContacto(Contacto c) {
			super(c.getNombre());
			this.contacto = c;
		}
		
		public Contacto getContacto() {
			return this.contacto;
		}
	}
	
	
	class CheckGrupo extends JCheckBox
	{
		private Grupo grupo = null;
		
		public CheckGrupo(Grupo g) {
			super(g.getNombre());
			this.grupo = g;
		}
		
		public Grupo getGrupo() {
			return this.grupo;
		}
	}
	
	
}
