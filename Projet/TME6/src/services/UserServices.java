package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import bd.BDService;

public class UserServices
{	
	public static JSONObject createUser(String login, String password, String nom, String prenom) throws JSONException
	{
		try
		{
			if(login == null || password == null || nom == null || prenom == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);
			
			if( BDService.userExists(login))
				return ServicesTools.serviceRefused("User déjà présent", -1);
			
			BDService.insertUser(login, password, nom, prenom);
			
			return ServicesTools.serviceAccepted();
		}
		catch(SQLException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL\n" + e + "", 1000);
		}
	}
	
	public static JSONObject loginUser(String login, String password) throws JSONException
	{
		try
		{
			if(login == null || password == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);
			
			int id = BDService.loginUser(login, password);
			if(id != 0)
			{
				int key = BDService.insertSession(id, false);
				return new JSONObject("{ 'key' : '" + key + "'}");
			}
			return ServicesTools.serviceRefused("Utilisateur non existant", -1);
		}
		catch(SQLException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
	
	public static JSONObject logoutUser(int key) throws JSONException
	{
		try
		{
			if(removeSession(key))
				return ServicesTools.serviceAccepted();
			return ServicesTools.serviceRefused("Erreur argument", 1000);
			
		}
		catch(SQLException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
}
