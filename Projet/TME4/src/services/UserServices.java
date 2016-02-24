package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import bd.BDService;

public class UserServices {
	
	public static JSONObject createUser(String login, String password, String nom, String prenom) 
	throws JSONException
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
	
	public static JSONObject loginUser(String login, String password) 
	throws JSONException
	{
		try
		{
			int time = 5000;
			if(login == null || password == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);
			
			if(BDService.login(login, password))
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
