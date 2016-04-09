package ln.app;

/**
 * L'objet PageSettings contient des métadonnées comme le nom et l'adresse relative d'une page donnée.
 */
public class PageSettings
{
	String root;
	String name;
	String titl;
	
	/**
	 * Initialise une PageSettings vide.
	 * @return PageSettings vide.
	 */
	public PageSettings()
	{
		this("", "", "");
	}

	/**
	 * Initialise une PageSettings simple.
	 * @param root Adresse relative de la page par rapport à la racide de l'application.
	 * @return PageSettings simple.
	 */
	public PageSettings(String root)
	{
		this(root, root, root);
	}

	/**
	 * Initialise une PageSettings.
	 * @param root Adresse relative de la page par rapport à la racide de l'application.
	 * @param name Nom court de la page.
	 * @return PageSettings.
	 */
	public PageSettings(String root, String name)
	{
		this(root, name, "");
	}

	/**
	 * Initialise une PageSettings.
	 * @param root Adresse relative de la page par rapport à la racide de l'application.
	 * @param name Nom court de la page.
	 * @param titl Nom long de la page.
	 * @return PageSettings.
	 */
	public PageSettings(String root, String name, String titl)
	{
		this.root = root;
		this.name = name;
		this.titl = titl;
	}

	/**
	 * Retourne l'adresse relative de la page par rapport à la racine de l'application.
	 * @return URL relative.
	 */
	public String getRoot()
	{
		return root;
	}

	/**
	 * Retourne le nom de la page.
	 * @return Nom de la page.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Retourne le titre complet de la page.
	 * @return Titre de la page.
	 */
	public String getTitl()
	{
		return titl;
	}
}
