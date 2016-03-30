package ln.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import ln.api.AbstractService;
import ln.api.MessagesService;
import ln.api.UsersService;

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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		/* Récupération du path GET */
		String[] gets = req.getRequestURL().toString().replaceAll(".*/api/", "").split("/"); 
	    
	    /* TODO transmettre à l'api avec fonction dédiée ? */
	    if(gets.length == 0)
	    	/* TODO Afficher une page static de doc */
	    	getServletContext().getRequestDispatcher("/WEB-INF/api.jsp").forward(req, res);
	    else
	    {
	    	try
	    	{
	    		try
	    		{
		    		JSONObject o;
		    		res.setContentType("application/json");
		    		PrintWriter out = res.getWriter();
		    		
		    		/* Sélection API */
		    		switch (gets[0])
		    		{
		    			case "users":
		    				if(gets.length == 2)
		    					out.print(UsersService.get(gets[1]));
		    				else
		    					out.print(UsersService.get());
		    				break;
		    				
		    			default:
		    				out.print(AbstractService.serviceRefused("Bad Request (DEBUG switch " + gets[0] + ")", 400));
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
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		/* Récupération du path GET */
		String[] gets = req.getRequestURL().toString().replaceAll(AppSettings.path + "/api/", "").split("/"); 
	    
	    /* TODO transmettre à l'api avec fonction dédiée ? */
	    if(gets.length == 0)
	    	/* TODO Afficher une page static de doc */
	    	getServletContext().getRequestDispatcher("/WEB-INF/api.jsp").forward(req, res);
	    else
	    {
	    	try
	    	{
	    		JSONObject o;
	    		/* Sélection API */
	    		switch (gets[0])
	    		{
	    			case "users":
	    				o = UsersService.create(req.getParameter("username"), 
	    										req.getParameter("password"),
	    										req.getParameter("nom"),
	    										req.getParameter("prenom"));
	    				
	    			default:
	    				o = AbstractService.serviceRefused("Bad Request", 400);
	    			
	    				
	    		}
	    		
	    	    res.setContentType("application/json");
	    		PrintWriter out = res.getWriter();
	    		out.print(o);
	    	}
	    	catch(JSONException e)
	    	{
	    		throw new ServletException();
	    	}
	    }
	}
	
	private void selectAPI(HttpServletRequest req, HttpServletResponse res, String verbe) throws ServletException, IOException
	{
		
	}
}
