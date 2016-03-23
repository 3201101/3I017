package services;

import org.json.JSONException;
import org.json.JSONObject;

public class ServicesTools {

	public static JSONObject serviceRefused(String message, int codeErreur) throws JSONException
	{
		return new JSONObject("{'Erreur' : "+ codeErreur +", 'Message :' : '" + message + "'}");
	}
	
	public static JSONObject serviceAccepted() throws JSONException
	{
		return new JSONObject("{'OK' : 'OK'}");
	}
}
