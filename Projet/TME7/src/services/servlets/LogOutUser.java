package services.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import services.ServicesTools;
import services.UserServices;

public class LogOutUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			int key = -1;
			try{
				key = Integer.parseInt(req.getParameter("key"));
			}
			catch(NumberFormatException e){
				ServicesTools.serviceRefused("Argument not a int", -1);
			}
			resp.setContentType("text/plain");
			resp.getWriter().print(UserServices.logOutUser(key).toString());
		}
		catch (JSONException e) {
			throw new ServletException("JSONException : " + e);
		}
	}
}
