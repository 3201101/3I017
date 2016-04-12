package ln.app;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Index
 *
 * Ce servlet correspond à la page principale du site, qui fait aussi office de page d'accueil.
 * Elle affiche le flux de tous les messages postés en temps réel, et les liens pour la connexion des utilisateurs.
 * Si l'utilisateur est déjà connecté, il peut afficher sur cette même page le flux de messages de ses amis.
 *
 * Cette page est mappée à l'URL "/".
 */
public class Index extends HttpServlet implements Servlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * Affiche la page d'accueil.
	 * @param  req              Requête
	 * @param  res              Réponse
	 * @throws ServletException 
	 * @throws IOException      
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		req.setAttribute("app", new AppSettings());
		req.setAttribute("page", new PageSettings("", "Accueil", "Accueil"));
		
		String url = req.getRequestURL().toString();
		String hsh = url.replaceAll(".*#", "");
		String usr = url.replaceAll(".*@", "");
		
		if(hsh != url)
			req.setAttribute("flux_query", "#" + hsh);
		
		if(url.replaceAll(".*@", "") != url)
			req.setAttribute("flux_query", "@" + usr);
		
		getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, res);
	}

	/**
	 * Affiche la page d'accueil
	 * @param  req              Requête
	 * @param  res              Réponse
	 * @throws ServletException 
	 * @throws IOException      
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		doGet(req, res);
	}
}
