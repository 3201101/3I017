package ln.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

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
		return new JSONObject().put("Erreur", codeErreur).put("Message", message);
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
	 * Fonction d'appel
	 * TODO to abstract ?
	 */
	public static JSONObject call(String[] gets, HttpServletRequest req, String verbe) throws JSONException
	{
		return serviceRefused("Non implémenté.", 404);
	}
}
