package services;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

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
	
	public static JSONObject logInUser(String login, String password) throws JSONException
	{
		try
		{
			if(login == null || password == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);
			
			int id = BDService.logInUser(login, password);
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
	
	public static JSONObject logOutUser(int key) throws JSONException
	{
		try
		{
			if(BDService.removeSession(key))
				return ServicesTools.serviceAccepted();
			return ServicesTools.serviceRefused("Erreur argument", 1000);
			
		}
		catch(SQLException e)
		{
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
	
	public JSONObject addFriend(int follower, int followed) throws JSONException {
		try {
			if(BDService.getFriends(follower).contains(followed))
				return ServicesTools.serviceRefused("Amis déjà existant", -1);
			BDService.addFriend(follower, followed);
			return ServicesTools.serviceAccepted();
		} catch (SQLException e) {
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
	
	public JSONObject removeFriend(int follower, int followed) throws JSONException {
		try {
			if(!BDService.getFriends(follower).contains(followed))
				return ServicesTools.serviceRefused("Amis non déjà existant", -1);
			BDService.removeFriend(follower, followed);
			return ServicesTools.serviceAccepted();
		} catch (SQLException e) {
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
	
	public JSONObject insertComment(int id, String comm) throws JSONException {
		try {
			BDService.insertComment(id, comm);
			return ServicesTools.serviceAccepted();
		} catch (UnknownHostException | MongoException e) {
			return ServicesTools.serviceRefused("Erreur MongoDB", 100000);
		}
	}
	
}
