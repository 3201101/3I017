package ln.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;

import ln.db.DBService;

/**
 * UserService
 *
 * Ce service permet la gestion des utilisateurs de l'application.
 */
public class UserService
{
	public static JSONObject existsUser(String login) throws SQLException, JSONException
	{
		ArrayList<String> n = new ArrayList<String>();
		n.add("login");
		ArrayList<String> v = new ArrayList<String>();
		v.add(login);
		return DBService.serviceStatus(DBService.exists("users", n, v), "Utilisateur inexistant", 404);
	}

	public static void insertUser(String login, String password, String nom, String prenom) throws SQLException
	{
		try
		{
			if(login == null || password == null || nom == null || prenom == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);

			if(BDService.userExists(login))
				return ServicesTools.serviceRefused("User déjà présent", -1);

			BDService.insertUser(login, password, nom, prenom);
		}
		catch(BDException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
		ServicesTools.serviceAccepted();

	}

	public static void insertSession(String id, boolean root) throws SQLException
	{
		Connection co = getMySQLCo();
		Statement st = co.createStatement();
		boolean r = true;
		UUID key = UUID.randomUUID();
		while(r)
		{
			String queryTest = "SELECT `key` FROM users WHERE `key` = " + key + ";";
			ResultSet res = st.executeQuery(queryTest);
			if(res.next())
				key = UUID.randomUUID();
			else
				r = false;
		}
		String query = "INSERT INTO session VALUES(`" + key + "`, `" + id + "`, null, `" + root + "`, `" + false + "`";
		st.executeUpdate(query);
		st.close();
		co.close();
	}
	
	/**
	 * La méthode createUser() ajoute un nouvel utilisateur à la base de données de l'application.
	 * @param  login         Identifiant du nouvel utilisateur
	 * @param  password      Mot de passe du nouvel utilisateur
	 * @param  nom           Nom du nouvel utilisateur
	 * @param  prenom        Prénom du nouvel utilisateur
	 * @return               Code d'erreur ou de réussite sous forme d'objet JSON
	 * @throws JSONException [description]
	 */
	public static JSONObject createUser(String login, String password, String nom, String prenom) throws JSONException
	{
		try
		{
			if(login == null || password == null || nom == null || prenom == null)
				return DBService.serviceRefused("Argument(s) manquant(s)", -1);

			if(existsUser(login))
				return ServicesTools.serviceRefused("User déjà présent", -1);

			DBService.insertUser(login, password, nom, prenom);
		}
		catch(SQLException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
		
		return ServicesTools.serviceAccepted();
	}

	public static JSONObject loginUser(String login, String password) throws JSONException
	{
		try
		{
			int delay = 5000;
			if(login == null || password == null)
				return ServicesTools.serviceRefused("Arguments invalides.", -1);

			ArrayList<String> n = new ArrayList<String>();
			n.add("login");
			n.add("password");
			ArrayList<String> v = new ArrayList<String>();
			v.add(login);
			v.add(password);
			
			if(DBService.exists())
			{
				//String key = sQLInsertSession(login, time);
				//return new JSONObject("{ 'key' : '" + key + "'}");
			}
		}
		catch(SQLException e)
		{
			return ServicesTools.serviceRefused("Erreur dans la base de données", 1000);
		}
	}
}

