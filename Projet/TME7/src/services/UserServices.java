package services;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import bd.DBService;

public class UserServices
{	
	public static JSONObject createUser(String login, String password, String nom, String prenom) throws JSONException
	{
		try
		{
			if(login == null || password == null || nom == null || prenom == null)
				return ServicesTools.serviceRefused("Argument(s) manquant(s)", -1);
			
			if( DBService.userExists(login))
				return ServicesTools.serviceRefused("User déjà présent", -1);
			
			DBService.insertUser(login, password, nom, prenom);
			
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
			
			int id = DBService.logInUser(login, password);
			if(id != 0)
			{
				int key = DBService.insertSession(id, false);
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
			if(DBService.removeSession(key))
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
			if(DBService.getFriends(follower).contains(followed))
				return ServicesTools.serviceRefused("Amis déjà existant", -1);
			DBService.addFriend(follower, followed);
			return ServicesTools.serviceAccepted();
		} catch (SQLException e) {
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
	
	public JSONObject removeFriend(int follower, int followed) throws JSONException {
		try {
			if(!DBService.getFriends(follower).contains(followed))
				return ServicesTools.serviceRefused("Amis non déjà existant", -1);
			DBService.removeFriend(follower, followed);
			return ServicesTools.serviceAccepted();
		} catch (SQLException e) {
			return ServicesTools.serviceRefused("Erreur SQL", 1000);
		}
	}
	
	public JSONObject insertComment(int id, String comm) throws JSONException {
		try {
			DBService.insertComment(id, comm);
			return ServicesTools.serviceAccepted();
		} catch (UnknownHostException | MongoException e) {
			return ServicesTools.serviceRefused("Erreur MongoDB", 100000);
		}
	}
	
}
