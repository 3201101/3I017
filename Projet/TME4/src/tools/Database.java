package tools;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;



public class Database
{
	private DataSource dataSource;
	private static Database database;
	private static Mongo mongo;
	
	public Database(String jndiname) throws SQLException
	{
		try
		{
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
		}
		catch (NamingException e)
		{
			throw new SQLException(jndiname + " is missing in JNDI! : " + e.getMessage());
		}
	}
	
	public Connection getConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
	
	public static Connection getMySQLConnection() throws SQLException
	{
		if(DBStatic.mysql_pooling)
		{
			return DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" + DBStatic.mysql_db, DBStatic.mysql_username, DBStatic.mysql_password);
		}
		else
		{
			if(database == null)
			{
				database = new Database("jdbc/db");
			}
			return database.getConnection();
		}
	}
	
	public static DB getMongo() throws UnknownHostException, MongoException
	{
		mongo = new Mongo(DBStatic.mongoDb_host, DBStatic.mongoDb_port);
		return mongo.getDB(DBStatic.mysql_username);
	}
	
	public static void closeMongo()
	{
		mongo.close();
	}
}