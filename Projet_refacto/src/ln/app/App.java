package ln.app;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class App extends HttpServlet implements Servlet
{
	private void setAppAttributes(HttpServletRequest req)
	{
		req.setAttribute("appName", "Ln");
	}
}
