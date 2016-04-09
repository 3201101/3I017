package ln.api;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
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
	 * Retourne les n derniers ou premiers messages d'un parent donné;
	 * @param parent identifiant du message parent, pour retourner les commenaitre de ce message. 0 si aucun parent.
	 * @param n nombre de messages à retourner. 0 pour retourner tous les messages trouvés.
	 * @param reverse True pour retourner les documents dans l'ordre antéchronologique, false pour l'ordre chronologique.
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject get(int parent, int n, boolean reverse) throws JSONException
	{
		try
		{
			BasicDBObject q = new BasicDBObject();
			q.append("parent", parent);		// Message parent, 0 si aucun, n pour commentaire du message n.
			DBCursor c = DBService.find("messages", q);
			if(reverse == true)
				c.sort(new BasicDBObject().append("date", -1));
			else
				c.sort(new BasicDBObject().append("date", 1));

			JSONObject o = new JSONObject();
			JSONArray a = new JSONArray();
			
			int i = 0;
			while(c.hasNext() && (i < n || n == 0))
			{
				BasicDBObject b = (BasicDBObject) c.next();
				JSONObject j = new JSONObject();
				j.put("id", b.getObjectId("_id").toString());
				j.put("author", b.getString("author"));
				j.put("message", b.getString("message"));
				j.put("title", b.getString("title"));
				j.put("type", b.getString("type"));
				j.put("limited", b.getBoolean("limited"));
				j.put("promoted", b.getBoolean("promoted"));
				j.put("date", b.getString("date"));
				
				a.put(j);
			}
			
			c.close();
			o.put("messages", a);
			return o;
			
		}
		catch (MongoException | UnknownHostException e)
		{
			return serviceRefused("Erreur liée à la base de données.", 1000);
		}
	}
	
	/**
	 * Retourne les 10 derniers messages postés. 
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject get() throws JSONException
	{
		return get(0, 10, true);
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
	public static JSONObject add(int authorId, String message, String title, boolean limited, boolean promoted, String type, int parent) throws JSONException
	{
		BasicDBObject o = new BasicDBObject()
		 .append("author", authorId)
		 .append("message", message)
		 .append("title", title)
		 .append("limited", limited)
		 .append("promoted", promoted)
		 .append("type", type)
		 .append("date", new Date());
		
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
		return MessagesService.add(authorId, message, title, limited, false, "normal", 0);
	}
	
	public static JSONObject add(int authorId, String message, int parent) throws JSONException
	{
		return MessagesService.add(authorId, message, "", false, false, "normal", parent); 
	}
	
	public static JSONObject add(int authorId, String message) throws JSONException
	{
		return MessagesService.add(authorId, message, "", false);
	}
}

