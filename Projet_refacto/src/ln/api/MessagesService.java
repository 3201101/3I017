package ln.api;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;

import ln.db.DBService;

/**
 * MessageService
 *
 * Ce service permet la gestion des messages.
 * Il implémente une API qui fournit toutes les fonctionnalités liés aux messages.
 */
public class MessagesService extends AbstractService
{
	/**
	 * Méthode appelée par l'application pour entrer dans l'API.
	 * @param gets Tableau d'arguments
	 * @return L'objet JSON retourné par la méthode de l'API adéquate.
	 * @throws JSONException 
	 */
	public static JSONObject call(String[] gets, HttpServletRequest req, String verbe) throws JSONException
	{
		if(gets.length == 1)
			return null; // TODO select all
		else
		{
			switch(gets[1])
			{
				default:
					return serviceRefused("Requête mal formée.", 400);
			}
		}
	}
	
	/**
	 * Ajoute un message à la base de données.
	 * @param authorId
	 * @param message
	 * @param title
	 * @param limited
	 * @param promoted
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject add(int authorId, String message, String title, boolean limited, boolean promoted, String type) throws JSONException
	{
		BasicDBObject o = new BasicDBObject()
		 .append("author", authorId)
		 .append("message", message)
		 .append("title", title)
		 .append("limited", limited)
		 .append("promoted", promoted)
		 .append("type", type);
		
		try
		{
			return serviceStatus(DBService.add("messages", o), "Erreur à l'insertion.", 1001);
		}
		catch (MongoException | UnknownHostException e)
		{
			return serviceRefused("Erreur liée à la base de données.", 1000);
		}
	}

	public static JSONObject add(int authorId, String message, String title, boolean limited) throws JSONException
	{
		return MessagesService.add(authorId, message, title, limited, false, "normal");
	}
	
	public static JSONObject add(int authorId, String message) throws JSONException
	{
		return MessagesService.add(authorId, message, "", false);
	}
}

