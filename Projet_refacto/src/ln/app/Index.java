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
@WebServlet("/")
public class Index extends App
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		request.setAttribute("title", "Index");
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		doGet(req, res);
	}
}
