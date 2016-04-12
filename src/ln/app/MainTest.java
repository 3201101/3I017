package ln.app;

import java.io.UnsupportedEncodingException;

import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

import org.json.JSONException;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;

import ln.api.AbstractService;
import ln.api.MessagesService;
import ln.api.UsersService;
import ln.db.DBService;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mysql.jdbc.Connection;

/**
 * Fonction test inutile en prod
 * (Parce que JUnit c'est surfait ^_^')
 * @author root
 *
 */
public class MainTest {
	public static void main(String[] args) throws SQLException, JSONException, MongoException, UnknownHostException {
		/*
		System.out.println(AbstractService.doc().toString());
		System.out.println("\n\n\n");
		System.out.println(new Date().toString());
		System.out.println("\n\n\n");
		System.out.println(MessagesService.get().toString());
		System.out.println("\n\n\n");
		//System.out.println(MessagesService.add(1, "Ceci est le tout premier message de l'application !").toString());
		BasicDBObject o = new BasicDBObject();
		o.put("test", "test2");
		System.out.println(DBService.add("messages", o));*/
		int key = -394410774;
		ArrayList<String> n = new ArrayList<String>();
		n.add("uuid");
		ArrayList<String> v = new ArrayList<String>();
		v.add(Integer.toString(key));
		
		ResultSet r = DBService.select("sessions", new ArrayList<String>(), n, v);
		System.out.println(r.toString());
		String s = "";
		if(r.next())
		{
			System.out.println("" + r.getDate("date").getTime());
			Date d = new Date();
			long session_lifetime = 365*24*60*60*1000;
			System.out.println(System.currentTimeMillis());
			System.out.println(d.getTime() - r.getDate("date").getTime());
			System.out.println(session_lifetime);
			if( (r.getBoolean("admin")) || (d.getTime() - r.getDate("date").getTime() < session_lifetime) && r.getBoolean("expired") == false)
				s =  r.getString("username");
			System.out.println("expire)");
		}
		r.close();
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		
		System.out.println("on");
		System.out.println(s);
		System.out.println(UsersService.getSession(key));
		//System.out.println(MessagesService.add(4, "Test alpha charlie", "T", false, false, "warning", 0));
	}
}
