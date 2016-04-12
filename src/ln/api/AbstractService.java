package ln.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ln.db.DBService;

public class AbstractService
{
	/**
	 * Méthodes généralistes
	 */

	/**
	 * Retourne un objet JSON standard contenant un code d'erreur.
	 * @param  message       Message d'erreur
	 * @param  codeErreur    Code numérique de l'erreur
	 * @return               Objet JSON
	 * @throws JSONException Erreur JSON
	 */
	public static JSONObject serviceRefused(String message, int codeErreur) throws JSONException
	{
		return new JSONObject().put("status", codeErreur).put("error", message);
	}
	
	/**
	 * Retourne un objet JSON stardard signalant la réussite d'une opération.
	 * @return 				 Objet JSON
	 * @throws JSONException Erreur JSON
	 */	
	public static JSONObject serviceAccepted() throws JSONException
	{
		return new JSONObject("{'OK' : 'OK'}");
	}

	/**
	 * Retourne un objet JSON standard.
	 * @param  status        Booléen signalant la réussite de l'opération.
	 * @param  message       Message d'erreur
	 * @param  codeErreur    Code numérique de l'erreur
	 * @return               Objet JSON
	 * @throws JSONException Erreur JSON
	 */
	public static JSONObject serviceStatus(boolean status, String message, int codeErreur) throws JSONException
	{
		if(status)
			return serviceAccepted();
		else
			return serviceRefused(message, codeErreur);
	}
	
	
	/**
	 * Documentation
	 */
	
	/**
	 * Rend le contenu de la documentation de l'API.
	 * @return Documentation de l'API au format JSON
	 * @throws JSONException
	 */
	public static JSONObject doc() throws JSONException
	{
		try 
		{
			ResultSet sql = DBService.select("api", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), "ORDER BY uri ASC, id ASC");
			
			JSONObject o = new JSONObject();
			JSONArray a = new JSONArray();
	
			while(sql.next())
			{
				JSONObject j = new JSONObject();
				
				j.put("id", sql.getInt("id"));
				j.put("service", sql.getString("service"));
				j.put("method", sql.getString("method"));
				j.put("uri", sql.getString("uri"));
				j.put("status", sql.getString("status"));
				j.put("note", sql.getString("note"));
				j.put("description", sql.getString("description"));
				j.put("parameters", sql.getString("parameters"));
				j.put("example", sql.getString("example"));
				j.put("errors", sql.getString("errors"));
				j.put("java", sql.getString("java"));
				
				a.put(j);
			}

			sql.close();
			o.put("doc", a);
			
			return o;
		}
		catch(SQLException e)
		{
			return AbstractService.serviceRefused("Erreur SQL " + e, 1000);
		}
	}
}
