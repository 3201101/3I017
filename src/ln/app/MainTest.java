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
		
		BasicDBObject o = new BasicDBObject();
		o.put("test", "test2");
		System.out.println(DBService.add("messages", o));*/
		
		//System.out.println(UsersService.login("3201101", "password"));
		System.out.println(MessagesService.add(-2102079032, "Test"));
	}
}
