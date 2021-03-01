package gallegux.instapant;



import java.io.IOException;
import java.net.InetAddress;
import java.util.Hashtable;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;




public class GetUsuarioLDAP 
{

	private String servidor, puerto, ADuser, ADpwd;
	private boolean ssl = false;
	private String uid = null, nombreUsuario = null;;
	private boolean checked = false;
	private LdapContext context;
	
	
	public GetUsuarioLDAP(String servidor, String puerto, boolean ssl, String usr, String pwd)
	{
		this.servidor = servidor;
		this.puerto = puerto;
		this.ssl = ssl;
		this.ADuser = usr;
		this.ADpwd = pwd;
	}

	
	
	private LdapContext connect()
			throws CommunicationException, NamingException
	{
        //String principalName = String.format("uid=%s,o=Junta de Castilla-La Mancha,c=es", this.ADuser);
        //String ldapURL = "ldap://ldap.jccm.es:636";
		String ldapURL = String.format("ldap://%s:%s",  this.servidor, this.puerto);

	    Hashtable props = new Hashtable();
	    if (this.ssl)	props.put(Context.SECURITY_PROTOCOL, "ssl");
        props.put(Context.SECURITY_PRINCIPAL, this.ADuser);
        props.put(Context.SECURITY_CREDENTIALS, this.ADpwd);
	    props.put(Context.PROVIDER_URL, ldapURL);
	    props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    
	    try {
	    	this.context = new InitialLdapContext(props, null);
	        return this.context;
	    }
	    catch (NamingException e) {
	    	System.out.println("Excepcion capturada"+e);
	    	e.printStackTrace();
	    	throw e;
	        //throw new NamingException("Failed to connect to " + domainName + ((serverName==null)? "" : " through " + serverName));
	    }
	}
	
	
	
	public void check() throws NamingException
	{
		if (this.checked)	return;
		
		connect();
		
        try {
        	this.context.setRequestControls(new Control[] { new PagedResultsControl(20, Control.NONCRITICAL) });
        }
        catch (IOException ioex) {
        	System.out.println("BuscarAD"+ ioex);
        }
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        //sc.setReturningAttributes(ATRIBUTOS);

        String cadenaBusqueda = String.format("(&(%s))", getUserFilter() );
        String root = getRoot();
        
        NamingEnumeration<SearchResult> answer = this.context.search( root, cadenaBusqueda, sc);

        if (answer.hasMoreElements()) {
            Attributes attr = answer.nextElement().getAttributes();
//            Attribute uid = attr.get("uid");
            Attribute nombre = attr.get("cn");
            
//            NamingEnumeration ne = attr.getAll();
//            while (ne.hasMore())	System.out.println(ne.next());
            
            if (nombre != null)	{
            	this.nombreUsuario = nombre.get().toString();
            }
        }
        this.checked = true;
	}
	

	
	private String getUserFilter() {
		String[] partes = this.ADuser.split(",");
		String[] partesUsuario = partes[0].split("=");
		this.uid = partesUsuario[1];
		
		return partes[0];
	}
	
	private String getRoot() {
		String[] partes = this.ADuser.split(",");
		StringBuilder s = new StringBuilder();
		for (int i = 1; i < partes.length; i++) {
			s.append(',');
			s.append(partes[i]);
		}
		return s.substring(1);
	}
	
	
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	public String getUID() {
		return this.uid;
	}
	
	
	public static void main(String...arg) {
		try {
			GetUsuarioLDAP g = new GetUsuarioLDAP("ldap.jccm.es", "636", true, 
					"uid=ffgr33,o=Junta de Castilla-La Mancha,c=es", "sofia,21");
			g.check();
			//String nombre = g.getUserFromLdap("uid=ffgr33,o=Junta de Castilla-La Mancha,c=es");
			System.out.println(g.getUID());
			System.out.println(g.getNombreUsuario());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
