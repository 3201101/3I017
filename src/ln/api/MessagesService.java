package ln.api;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

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
	public static JSONObject get(int parent, int n, boolean reverse, int offset) throws JSONException
	{
		try
		{
			BasicDBObject q = new BasicDBObject();
			q.append("parent", parent);		// Message parent, 0 si aucun, n pour commentaire du message n.
			
			if(offset != 0)
			{
				if(reverse == true)
					q.append("_id", new BasicDBObject("$gt", offset));
				else
					q.append("_id", new BasicDBObject("$lt", offset));
			}
			
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
			return serviceRefused("Erreur Mongo (Message.get) " + e, 500);
		}
	}
	
	/**
	 * Retourne les 10 derniers messages postés. 
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject get() throws JSONException
	{
		return get(0, 10, true, 0);
	}
	
	/**
	 * Ajoute un message.
	 * @param session       Token d'authentification
	 * @param message       Contenu du message
	 * @param title         Titre du message
	 * @param limited       Portée du message (True pour un message limité aux amis de l'auteur)
	 * @param promoted		Annonce (True pour une mise en avant du message)
	 * @param type			Type du message (Définit la couleur du message, "normal" par défaut)
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject add(int session, String message, String title, boolean limited, boolean promoted, String type, int parent) throws JSONException
	{
		String author;
		try 
		{
			author = UsersService.getSession(session);
		}
		catch (SQLException e1)
		{
			return serviceRefused("Erreur SQL (Message.add) " + e1, 500);
		}
		
		if(author == "")
		{
			return serviceRefused("Session invalide.", 403);
		}
		
		BasicDBObject o = new BasicDBObject()
		 .append("author", author)
		 .append("message", message)
		 .append("title", title)
		 .append("limited", limited)
		 .append("promoted", promoted)
		 .append("type", type)
		 .append("date", new Date())
		 .append("parent", parent);
		
		try
		{
			return serviceStatus(DBService.add("messages", o), "Erreur à l'insertion.", 1001);
		}
		catch (MongoException | UnknownHostException e)
		{
			return serviceRefused("Erreur Mongo (Message add) " + e, 500);
		}
	}

	/**
	 * Ajoute un message
	 * @param  session       Session d'authentification
	 * @param  message       Contenu du message
	 * @param  title         Titre du message
	 * @param  limited       Portée du message
	 * @return               
	 * @throws JSONException 
	 */
	public static JSONObject add(int session, String message, String title, boolean limited) throws JSONException
	{
		return MessagesService.add(session, message, title, limited, false, "normal", 0);
	}
	
	public static JSONObject add(int session, String message, int parent) throws JSONException
	{
		return MessagesService.add(session, message, "", false, false, "normal", parent); 
	}
	
	public static JSONObject add(int session, String message) throws JSONException
	{
		return MessagesService.add(session, message, "", false);
	}
}

