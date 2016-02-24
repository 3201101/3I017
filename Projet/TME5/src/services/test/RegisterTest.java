package services.test;

import java.net.UnknownHostException;

import org.json.JSONException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import services.UserServices;
import tools.DBStatic;

public class RegisterTest
{
	public static void main(String[] args) throws JSONException, UnknownHostException, MongoException
	{
		//System.out.println(UserServices.createUser("debug", "debug", "Debug", "Test"));
		
		Mongo m = new Mongo(DBStatic.mongoDb_host, DBStatic.mongoDb_port);
		DB db = m.getDB(DBStatic.mysql_username);
		DBCollection collec = db.getCollection("comments");
		BasicDBObject obj = new BasicDBObject();
		obj.put("user_id", 1);
		obj.put("text", "Lorem Ipsum");
		collec.insert(obj);
		
		DBCursor cursor = collec.find(obj);
		while(cursor.hasNext())
		{
			DBObject res = cursor.next();
			System.out.println(res);
		}
		m.close();
	}
}
