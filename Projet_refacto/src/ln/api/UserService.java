package ln.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import ln.db.DBService;

/**
 * UserService
 *
 * Ce service permet la gestion des utilisateurs de l'application.
 */
public class UserService
{
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
		return DBService.serviceStatus(DBService.exists("users", n, v), "Utilisateur inexistant", 404);
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
	public static JSONObject create(String login, String password, String nom, String prenom) throws JSONException
	{
		try
		{
			if(login == null || password == null || nom == null || prenom == null)
				return DBService.serviceRefused("Argument(s) manquant(s)", -1);

			ArrayList<String> n = new ArrayList<String>();
			n.add("login");
			ArrayList<String> v = new ArrayList<String>();
			v.add(login);
			
			if(DBService.exists("users", n, v))
				return DBService.serviceRefused("User déjà présent", -1);

			n.add("password");
			n.add("prenom");
			n.add("nom");
			v.add(password);
			v.add(prenom);
			v.add(nom);
			
			return DBService.serviceAccepted();
		}
		catch(SQLException e)
		{
			return DBService.serviceRefused("Erreur SQL", 1000);
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
	private static int createSession(String id, boolean root) throws SQLException
	{
		ArrayList<String> n = new ArrayList<String>();
		n.add("user_id");
		n.add("admin");
		ArrayList<String> v = new ArrayList<String>();
		v.add(id);
		v.add(root ? "TRUE" : "FALSE");
		
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
		
		return DBService.insert("sessions", n, v);
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
				return DBService.serviceRefused("Argument(s) manquant(s)", -1);

			ArrayList<String> n = new ArrayList<String>();
			n.add("login");
			n.add("password");
			ArrayList<String> v = new ArrayList<String>();
			v.add(login);
			v.add(password);
			
			if(DBService.exists("users", n, v))
			{
				ArrayList<String> s = new ArrayList<String>();
				n.add("root");
				
				ResultSet r = DBService.select("users", s, n, v);
				r.first();
				
				String session = Integer.toString(createSession(login, r.getBoolean(1)));
				return new JSONObject("{ 'session' : '" + session + "'}");
			}
			else
				return DBService.serviceRefused("Utilisateur inexistant.", 404);
		}
		catch(SQLException e)
		{
			return DBService.serviceRefused("Erreur dans la base de données", 1000);
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
			return DBService.serviceAccepted();
		}
		catch(SQLException e)
		{
			return DBService.serviceRefused("Erreur dans la base de données", 1000);
		}
	}
}

