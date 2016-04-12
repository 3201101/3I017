package ln.api;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ln.db.DBService;

/**
 * UserService
 *
 * Ce service permet la gestion des utilisateurs de l'application.
 */
public class UsersService extends AbstractService
{
	/**
	 * Récupère un utilisateur par son identifiant
	 * @param id Identifiant de l'utilisateur recherché
	 * @return Utilisateur au format JSON
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject getById(String id) throws SQLException, JSONException
	{
		ArrayList<String> n = new ArrayList<String>();
		n.add("id");
		ArrayList<String> v = new ArrayList<String>();
		v.add(id);
		ResultSet r = DBService.select("users", new ArrayList<String>(), n, v);
		boolean b = true;
		JSONObject j = new JSONObject();
		
		if(r.next())
		{
			b = false;
			j.put("_id", r.getInt("id"));
			j.put("username", r.getString("login"));
			j.put("nom", r.getString("nom"));
			j.put("prenom", r.getString("prenom"));
			j.put("admin", r.getBoolean("root"));
		}
		
		r.close();
		
		if(b == true)
			return serviceRefused("Utilisateur inexistant", 404);
		else
			return j;
	}
	
	/**
	 * Récupère un utilisateur par son nom
	 * @param login <String> Identifiant de l'utilisateur recherché
	 * @return <JSONObject> 
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject get(String login) throws SQLException, JSONException
	{
		ArrayList<String> n = new ArrayList<String>();
		n.add("login");
		ArrayList<String> v = new ArrayList<String>();
		v.add(login);
		ResultSet r = DBService.select("users", new ArrayList<String>(), n, v);
		boolean b = true;
		JSONObject j = new JSONObject();
		
		if(r.next())
		{
			b = false;
			j.put("_id", r.getInt("id"));
			j.put("username", r.getString("login"));
			j.put("nom", r.getString("nom"));
			j.put("prenom", r.getString("prenom"));
			j.put("admin", r.getBoolean("root"));
			j.put("email", r.getString("email"));
			j.put("avatar", r.getString("avatar"));
		}
		
		r.close();
		
		if(b == true)
			return serviceRefused("Utilisateur inexistant", 404);
		else
			return j;
	}
	
	/**
	 * Récupère tous les utilisateurs
	 * @param login <String> Identifiant de l'utilisateur recherché
	 * @return <JSONObject> 
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject get() throws SQLException, JSONException
	{
		ResultSet r = DBService.select("users");
		JSONObject o = new JSONObject();
		JSONArray a = new JSONArray();
		
		while(r.next())
		{
			JSONObject j = new JSONObject();
			j.put("_id", r.getInt("id"));
			
			j.put("username", r.getString("login"));
			j.put("nom", r.getString("nom"));
			j.put("prenom", r.getString("prenom"));
			j.put("admin", r.getBoolean("root"));
			j.put("avatar", r.getBoolean("avatar"));
			a.put(j);
		}
		
		r.close();
		o.put("users", a);
		
		return o;
	}
	
	/**
	 * Teste l'existance d'un utilisateur selon son identifiant.
	 * @param login <String> Identifiant de l'utilisateur recherché
	 * @return <JSONObject> 
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject exists(String login) throws SQLException, JSONException
	{
		ArrayList<String> n = new ArrayList<String>();
		n.add("login");
		ArrayList<String> v = new ArrayList<String>();
		v.add(login);
		return serviceStatus(DBService.exists("users", n, v), "Utilisateur inexistant", 404);
	}

	/**
	 * Ajoute un utilisateur à la base de données.
	 * @param login <String> Identifiant de l'utilisateur 
	 * @param password <String> Mot de passe de l'utilisateur
	 * @param nom <String> Nom de famille de l'utilisateur
	 * @param prenom <String> Prénom de l'utilisateur
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject create(String login, String password, String nom, String prenom, String email) throws JSONException
	{
		try
		{
			if(login == null || password == null || nom == null || prenom == null)
				return serviceRefused("Argument(s) manquant(s)", -1);

			ArrayList<String> n = new ArrayList<String>();
			n.add("login");
			ArrayList<String> v = new ArrayList<String>();
			v.add(login);
			
			if(DBService.exists("users", n, v))
				return serviceRefused("User déjà présent", -1);			

			n.add("password");
			n.add("prenom");
			n.add("nom");
			n.add("email");
			n.add("avatar");
			n.add("root");
			v.add(sha1(password));
			v.add(prenom);
			v.add(nom);
			v.add(email);
			v.add("http://placehold.it/128/" + (int)(Math.random() * 999999) + "/ffffff?text=" + Character.toString(login.charAt(0)).toUpperCase());
			v.add("0");
			
			if(DBService.insert("users", n, v) > 0)
				return serviceAccepted();
			return serviceRefused("Erreur SQL non spécifiée (User.create)", 500);
		}
		catch(SQLException e)
		{
			return serviceRefused("Erreur SQL (User.create) " + e, 500);
		}

	}

	/**
	 * Expire une session
	 * @param id
	 * @param root
	 * @throws SQLException
	 */
	private static void expireSession(int uuid) throws SQLException
	{
		ArrayList<String> n = new ArrayList<String>();
		n.add("expired");
		ArrayList<String> v = new ArrayList<String>();
		v.add("TRUE");
		ArrayList<String> w = new ArrayList<String>();
		n.add("uuid");
		ArrayList<String> u = new ArrayList<String>();
		u.add(Integer.toString(uuid));
		
		DBService.update("sessions", n, v, w, u);
	}
	
	/**
	 * Crée une nouvelle session
	 * @param id
	 * @param root
	 * @return Clé de la session
	 * @throws SQLException
	 */
	private static String createSession(String id, String username, boolean root) throws SQLException
	{
		ArrayList<String> n = new ArrayList<String>();
		n.add("user_id");
		n.add("admin");
		n.add("username");
		n.add("date");
		ArrayList<String> v = new ArrayList<String>();
		v.add(id);
		v.add(root ? "1" : "0");
		v.add(username);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		v.add(sdf.format(d.getTime()));
		
		ArrayList<String> u = new ArrayList<String>();
		u.add("uuid");
		ArrayList<String> c = new ArrayList<String>();
		
		String key;
		do
		{
			key = Integer.toString(UUID.randomUUID().hashCode());
			c.clear();
			c.add(key);
		} while(DBService.exists("sessions", u, c));
		
		n.add("uuid");
		v.add(key);

		int r = DBService.insert("sessions", n, v);
		if(r == 0)
			throw new SQLException("Echec de la création de la session");
		return key;
	}
	
	public static String getSession(int key) throws SQLException
	{
		ArrayList<String> n = new ArrayList<String>();
		n.add("uuid");
		ArrayList<String> v = new ArrayList<String>();
		v.add(Integer.toString(key));
		
		ResultSet r = DBService.select("sessions", new ArrayList<String>(), n, v);
		String s = "";
		if(r.next())
		{
			Date d = new Date();
			long session_lifetime = 4*60*60*1000;
			if( (r.getBoolean("admin")) || (d.getTime() - r.getDate("date").getTime() < session_lifetime) && r.getBoolean("expired") == false)
				s = r.getString("username");
		}
		return s;
	}
	

	/**
	 * Connecte un utilisateur.
	 * @param login
	 * @param password
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject login(String login, String password) throws JSONException
	{
		try
		{
			if(login == null || password == null)
				return serviceRefused("Argument(s) manquant(s)", 401);

			ArrayList<String> n = new ArrayList<String>();
			n.add("login");
			n.add("password");
			ArrayList<String> v = new ArrayList<String>();
			v.add(login);
			v.add(sha1(password));
			
			if(DBService.exists("users", n, v))
			{
				ArrayList<String> s = new ArrayList<String>();
				
				ResultSet r = DBService.select("users", s, n, v);
				r.first();
				
				String session = createSession(Integer.toString(r.getInt("id")), r.getString("login"), r.getBoolean("root"));
				return new JSONObject()
					.put("session", session)
					.put("login", login)
					.put("admin", r.getBoolean(6));
			}
			else
				return serviceRefused("Utilisateur inexistant.", 404);
		}
		catch(SQLException e)
		{
			return serviceRefused("Erreur SQL (User.login) " + e, 500);
		}
	}
	
	/**
	 * Déconnecte un utilisateur
	 * @param uuid
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject logout(int uuid) throws JSONException
	{
		try
		{
			expireSession(uuid);
			return serviceAccepted();
		}
		catch(SQLException e)
		{
			return serviceRefused("Erreur SQL (User.logout) " + e, 500);
		}
	}
	
	
	/**
	 * Fonction utilitaires
	 */
	
	/**
	 * Calcule le hash SHA1 d'une chaîne de caractères.
	 * @param password Chaîne à hasher.
	 * @return
	 */
	private static String sha1(String password)
	{
		byte[] sha1 = null;
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = crypt.digest();
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    
	    Formatter formatter = new Formatter();
	    for (byte b : sha1)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    
	    return result;
	}
}


