package ln.api;

import org.json.JSONException;
import org.json.JSONObject;

public class ServicesTools {

	static JSONObject serviceRefused(String message, int codeErreur) throws JSONException
	{
		return new JSONObject("{'Erreur' : "+ codeErreur +", 'Message :' : '" + message + "'}");
	}
	
	static JSONObject serviceAccepted() throws JSONException
	{
		return new JSONObject("{'OK' : 'OK'}");
	}
}
