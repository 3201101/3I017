package ln.app;


/***************************************************

	
	          _____            _____          
	         /\    \          /\    \         
	        /::\____\        /::\____\        
	       /:::/    /       /::::|   |        
	      /:::/    /       /:::::|   |        
	     /:::/    /       /::::::|   |        	
	    /:::/    /       /:::/|::|   |        
	   /:::/    /       /:::/ |::|   |        
	  /:::/    /       /:::/  |::|   | _____  
	 /:::/    /       /:::/   |::|   |/\    \ 		
	/:::/____/       /:: /    |::|   /::\____\		
	\:::\    \       \::/    /|::|  /:::/    /
	 \:::\    \       \/____/ |::| /:::/    / 
	  \:::\    \              |::|/:::/    /  
	   \:::\    \             |::::::/    /   
	    \:::\    \            |:::::/    /    
	     \:::\    \           |::::/    /     
	      \:::\    \          /:::/    /      
	       \:::\____\        /:::/    /       
	        \::/    /        \::/    /        
	         \/____/          \/____/         
	                                          


***************************************************/

/**
 * AppSettings
 * 
 * Surcharge PageSettings pour fournir des informations générales relative à l'application.
 */
public final class AppSettings extends PageSettings
{
	static String atom;
	static String path;

	/**
	 * Surcharge le constructeur de PageSettings
	 * @return AppSettings
	 */
	public AppSettings()
	{
		root = "Ln";
		name = "Ln";
		titl = "Log À Rythmes";
		//atom = "http://li328.delfosia.net/";
		atom = "http://li328.lip6.fr:8280/";
		path = atom + root;
	}

	/**
	 * Retourne l'adresse absolue du site. Nécessaire pour le fix du bug des URL relatives de TOMCAT.
	 * @return URL absolue du site.
	 */
	public String getPath()
	{
		return path;
	}
}
