package ln.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import ln.api.AbstractService;
import ln.api.MessagesService;
import ln.api.UsersService;


/**
 * API
 *
 * Ce servlet est le point d'entrée de l'API de l'application.
 * Elle retourne des documents JSON générés par l'API en fonction des entrées reçues en GET.
 *
 * Cette page est mappée à l'URL "/api".
 */
public class API extends HttpServlet implements Servlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * Renvoie le résultat d'une requête GET à l'API (ou la page de documentation le cas échant).
	 * @param  req              Requête
	 * @param  res              Réponse
	 * @throws ServletException 
	 * @throws IOException      
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		/* Récupération du path GET */
		String path = req.getRequestURL().toString().replaceAll(".*/api", "");
		
		try
		{
			try
			{
				/* Page d'accueil de l'API avec documentation */
				if(path == "" || path == "/" || path.length() < 2)
				{
					req.setAttribute("app", new AppSettings());
					req.setAttribute("page", new PageSettings("/api", "API", "Documentation de l'API"));
			    	getServletContext().getRequestDispatcher("/WEB-INF/api.jsp").forward(req, res);
				}
				else
			    {	   
		    		res.setContentType("application/json");
		    		PrintWriter out = res.getWriter();
		    		
		    		/* Sélection API */
		    		switch (path)
		    		{
		    			case "/index":
		    				out.print(AbstractService.doc());
		    				break;
		    				
		    			case "/friends":
		    				out.print(UsersService.friends(Integer.parseInt(req.getParameter("session"))));
		    				break;

		    			case "/users":
		    				if(req.getParameter("username") != null)
		    					out.print(UsersService.get(req.getParameter("username")));
		    				else
		    					out.print(UsersService.get());
		    				break;
		    				
		    			case "/messages":
		    				if(req.getParameter("reverse") != null)
		    					out.print(MessagesService.get("", Integer.parseInt(req.getParameter("n")), Boolean.parseBoolean(req.getParameter("reverse")), Long.parseLong((req.getParameter("offset")))));
		    				else if(req.getParameter("id") != null)
		    					out.print(MessagesService.get(req.getParameter("id")));
		    				else
		    					out.print(MessagesService.get());
		    				break;
		    				
		    			case "/comments":
		    				out.print(MessagesService.get(req.getParameter("parent"), 0, false, 0));
		    				break;
		    				
		    			default:
		    				out.print(AbstractService.serviceRefused("Ressource introuvable : " + path + ")", 404));
		    		}
			    }
			}
			catch(Exception e)
			{
				AbstractService.serviceRefused("Serveur : " + e, 500);
			}
		}
    	catch(JSONException e)
    	{
    		throw new ServletException();
    	}
	}

	/**
	 * Renvoie le résultat d'une requête POST à l'API.
	 * @param  req              Requête
	 * @param  res              Réponse
	 * @throws ServletException 
	 * @throws IOException      
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		/* Récupération du path GET */
		String path = req.getRequestURL().toString().replaceAll(".*/api", "");
		
		try
		{
			try
			{
	    		res.setContentType("application/json");
	    		PrintWriter out = res.getWriter();
	    		
	    		/* Sélection API */
	    		switch (path)
	    		{
		    		case "/login":
	    				out.print(UsersService.login(req.getParameter("username"), req.getParameter("password")));
	    				break;
	    				
		    		case "/logout":
	    				out.print(UsersService.logout(Integer.parseInt(req.getParameter("session"))));
	    				break;
	    				
		    		case "/follow":
	    				out.print(UsersService.follow(Integer.parseInt(req.getParameter("session")), req.getParameter("followed")));
	    				break;
	
		    		case "/users":
	    				out.print(UsersService.create(
	    					req.getParameter("username"),
	    					req.getParameter("password"),
	    					req.getParameter("nom"),
	    					req.getParameter("prenom"),
	    					req.getParameter("email")
   						));
	    				break;	
	    			
	    			case "/messages":
	    				out.print(MessagesService.add(
    						Integer.parseInt(req.getParameter("session")),
    						req.getParameter("message"),
    						req.getParameter("title"),
    						Boolean.parseBoolean(req.getParameter("limited")),
    						Boolean.parseBoolean(req.getParameter("annonce")),
    						req.getParameter("type"),
    						req.getParameter("parent")
   						));
	    				break;
	    				
	    			default:
	    				out.print(AbstractService.serviceRefused("Ressource introuvable : " + path + ")", 404));
			    }
			}
			catch(Exception e)
			{
				AbstractService.serviceRefused("Serveur : " + e, 500);
			}
		}
    	catch(JSONException e)
    	{
    		throw new ServletException();
    	}
	}
	
	/**
	 * Renvoie le résultat d'une requête PUT à l'API.
	 * @param  req              Requête
	 * @param  res              Réponse
	 * @throws ServletException 
	 * @throws IOException      
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		
	}
	
	/**
	 * Renvoie le résultat d'une requête DELETE à l'API.
	 * @param  req              Requête
	 * @param  res              Réponse
	 * @throws ServletException 
	 * @throws IOException      
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		
	}
}
