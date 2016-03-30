package ln.app;

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
		root = "0ln";
		name = "Ln";
		titl = "Log À Rythmes";
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
