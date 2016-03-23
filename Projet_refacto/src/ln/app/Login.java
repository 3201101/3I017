package ln.app;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Login
 *
 * Ce servlet correspond à la page de connexion de l'utilisateur.
 * Cette page fournit un formulaire permettant à l'utilisateur de se connecter à son compte sur l'application.
 * En cas de réussite, elle redirige l'utilisateur vers la page principale de l'application, Index.
 * En cas d'échec de la connexion, un message d'erreur invitant l'utilisateur à réessayer est affiché.
 */
@WebServlet(urlPatterns = "/login", initParams = @WebInitParam(name = "pageTitle", value = "Connexion"))
public class Login extends App
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		request.setAttribute("title", "Login");
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		if (login == 1) {
			// index avec params + bubblepop
		}
		//doGet(req, res);
	}
}