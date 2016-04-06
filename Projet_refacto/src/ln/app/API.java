package ln.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ln.api.AbstractService;
import ln.api.MessagesService;
import ln.api.UsersService;
import ln.db.DBService;


/**
 * Servlet API
 *
 * Ce servlet est le point d'entrée de l'API de l'application.
 * Elle retourne des documents JSON générés par l'API en fonction des entrées reçues en GET.
 *
 * Cette page est mappée à l'URL "/api".
 */
public class API extends HttpServlet implements Servlet
{
	private static final long serialVersionUID = 1L;

	protected static JSONObject index() throws JSONException
	{
		try 
		{
			ResultSet sql = DBService.select("api", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), "ORDER BY uri ASC, id ASC");
			
			JSONObject o = new JSONObject();
			JSONArray a = new JSONArray();
	
			while(sql.next())
			{
				JSONObject j = new JSONObject();
				
				j.put("id", sql.getInt("id"));
				j.put("service", sql.getString("service"));
				j.put("method", sql.getString("method"));
				j.put("uri", sql.getString("uri"));
				j.put("status", sql.getString("status"));
				j.put("note", sql.getString("note"));
				j.put("description", sql.getString("description"));
				j.put("parameters", sql.getString("parameters"));
				j.put("example", sql.getString("example"));
				j.put("errors", sql.getString("errors"));
				j.put("java", sql.getString("java"));
				
				a.put(j);
			}

			sql.close();
			o.put("doc", a);
			
			return o;
		}
		catch(SQLException e)
		{
			return AbstractService.serviceRefused("Erreur SQL " + e, 1000);
		}
	}

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
		    				out.print(index());
		    				break;

		    			case "/users":
		    				out.print(UsersService.get());
		    				break;

		    			case "/messages":
		    				out.print(MessagesService.get());
		    				break;
		    				
		    			default:
		    				out.print(AbstractService.serviceRefused("Bad Request (DEBUG switch " + path + ")", 400));
		    		}
			    }
			}
			catch(SQLException e)
			{
				AbstractService.serviceRefused("Bad Request (DEBUG SQLE)", 400);
			}
		}
    	catch(JSONException e)
    	{
    		throw new ServletException();
    	}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		/* Récupération du path GET */
		String path = req.getRequestURL().toString().replaceAll(".*/api", "");
		
		try
		{
			/*try
			{*/
			   
	    		res.setContentType("application/json");
	    		PrintWriter out = res.getWriter();
	    		
	    		/* Sélection API */
	    		switch (path)
	    		{

	    			case "/messages":
	    				out.print(MessagesService.add(Integer.parseInt(req.getParameter("author")), req.getParameter("message")));
	    				break;
	    				
	    			default:
	    				out.print(AbstractService.serviceRefused("Bad Request (DEBUG switch " + path + ")", 400));
	    		}
			/*}
			catch(SQLException e)
			{
				AbstractService.serviceRefused("Bad Request (DEBUG SQLE)", 400);
			}*/
		}
    	catch(JSONException e)
    	{
    		throw new ServletException();
    	}
	}
}
