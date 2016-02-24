package net.ln.services;

import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * UserService
 *
 * Ce service permet la gestion des utilisateurs de l'application.
 */
public class UserService
{
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

	public static JSONObject loginUser(String login, String password) throws JSONException
	{
		try
		{
			int time = 5000;
			if(login == null || password == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);

			if( SQLLoginPass(login, password))
			{
				String key = SQLInsertSession(login, time);
				return new JSONObject("{ 'key' : '" + key + "'}");
			}
		}
		catch(SQLException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
}
