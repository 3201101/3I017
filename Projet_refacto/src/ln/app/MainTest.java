package ln.app;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONException;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;

import ln.api.MessagesService;
import ln.db.DBService;

public class MainTest {
	public static void main(String[] args) throws SQLException, JSONException, MongoException, UnknownHostException {
		System.out.println(API.index().toString());
		System.out.println("\n\n\n");
		System.out.println(new Date().toString());
		System.out.println("\n\n\n");
		System.out.println(MessagesService.get().toString());
		System.out.println("\n\n\n");
		//System.out.println(MessagesService.add(1, "Ceci est le tout premier message de l'application !").toString());
		BasicDBObject o = new BasicDBObject();
		o.put("test", "test2");
		System.out.println(DBService.add("messages", o));
	}
}
