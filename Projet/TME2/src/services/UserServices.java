package services;

import org.json.JSONException;
import org.json.JSONObject;

public class UserServices {
	
	public static JSONObject createUser(String login, String password, String nom, String prenom) 
	throws JSONException
	{
		try
		{
			if(login == null || password == null || nom == null || prenom == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);
			
			if( SQLsearch(login))
				return ServicesTools.serviceRefused("User déjà présent", -1);
			
			SQLInsert(login, password, nom, prenom);
		}
		catch(BDException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
		ServicesTools.serviceAccepted();
	}
	
	public static JSONObject loginUser(String login, String password) 
	throws JSONException
	{
		try
		{
			int time = 5000;
			if(login == null || password == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);
			
			if( SQLLoginPass(login, password))
			{
				String key = SQLInsertSession(login, time);
				return return new JSONObject("{ 'key' : '" + key + "'}");
			}
			
		}
		catch(BDException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
}
