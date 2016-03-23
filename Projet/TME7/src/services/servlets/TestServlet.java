package services.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.DBStatic;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class TestServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		//System.out.println(UserServices.createUser("debug", "debug", "Debug", "Test"));
		Mongo m = new Mongo(DBStatic.mongoDb_host, DBStatic.mongoDb_port);
		DB db = m.getDB(DBStatic.mysql_username);
		DBCollection collec = db.getCollection("comments");
		BasicDBObject obj = new BasicDBObject();
		obj.put("user_id", 1);
		obj.put("text", "Lorem Ipsum");
		collec.insert(obj);
		
		PrintWriter pw = resp.getWriter();
		
		DBCursor cursor = collec.find(obj);
		while(cursor.hasNext())
		{
			DBObject res = cursor.next();
			pw.write(res.toString());
		}
		m.close();
	}
}
