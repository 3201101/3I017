package ln.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;


/**
 * DBService
 *
 * Bibliothèque de fonctions de manipulation des bases de données.
 */
public class DBService
{
	//private static Database database;

	/**
	 * La méthode getMySQLCo() retourne un objet Connection lié à la base de données MySQL de l'application.
	 * @return Objet Connection
	 * @throws SQLConnection Exception signalant une erreur de la base de données
	 */
	private static Connection getMySQLCo() throws SQLException
	{
		if(DBSettings.db_mysql_pooling)
		{
			try
			{
				DataSource dS = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/db");
				return (Connection) dS.getConnection();
			}
			catch (NamingException e)
			{
				throw new SQLException("jdbc/db is missing in JNDI! : " + e.getMessage());
			}
		}
		else
		{
			return (Connection) DriverManager.getConnection("jdbc:mysql://" + DBSettings.db_host + "/" + DBSettings.db_name, DBSettings.db_user, DBSettings.db_pass);
		}
	}

	/**
	 * La méthode getMongo() retourne un objet Mongo représentant la base de données MongoDB de l'application.
	 * @return Objet Mongo
	 */
	private static Mongo getMongo()
	{
		return new Mongo(DBStatic.mongoDb_host, DBStatic.mongoDb_port)getDB(DBStatic.mongoDB_name);
	}

	/**
	 * La méthode getMongoCo() retourne la List MongoDB dont le nom est passé en paramètre.
	 * @param  table Nom de la List voulue
	 * @return       Objet List
	 */
	private static List getMongoCo(String table)
	{
		return getMongo().getList(table);
	}

	public static JSONObject serviceRefused(String message, int codeErreur) throws JSONException
	{
		return new JSONObject("{'Erreur' : "+ codeErreur +", 'Message :' : '" + message + "'}");
	}
	
	public static JSONObject serviceAccepted() throws JSONException
	{
		return new JSONObject("{'OK' : 'OK'}");
	}
	
	public static JSONObject serviceStatus(boolean status, String message, int codeErreur) throws JSONException
	{
		if(status)
			return serviceAccepted();
		else
			return serviceRefused(message, codeErreur);
	}

	public static ResultSet select(String table) throws SQLException
	{
		return select(table, new ArrayList<String>());
	}

	public static ResultSet select(String table, List<String> select_name) throws SQLException
	{
		return select(table, select_name, new ArrayList<String>(), new ArrayList<String>());
	}

	public static ResultSet select(String table, List<String> select_name, List<String> where_name, List<String> where_val) throws SQLException
	{
		return select(table, select_name, where_name, where_val, "");
	}

	/**
	 * La méthode select() construit une requête SQL SELECT à l'aide de la liste de paramètres données
	 * @param  table        table contenant les données recherchées
	 * @param  select_name  liste des colonnes voulues
	 * @param  where_name   liste des colonnes conditionnées
	 * @param  where_val    valeurs des colonnes conditionnées
	 * @param  opt          options sql
	 * @return              Objet ResultSet contenant les résultats de la requête
	 * @throws SQLException Erreur SQL
	 */
	public static ResultSet select(String table, List<String> select_name, List<String> where_name, List<String> where_val, String opt) throws SQLException
	{
		Connection co = getMySQLCo();
		String query = "SELECT ";

		// Colonnes à sélectionner
		int s = select_name.size();
		if(s == 0)
		{
			query+= "* ";
		}
		else
		{
			for (String i : select_name)
			{
				query+= i;
				query+= ",";
			}
			query = query.substring(0, query.length()-1);
		}

		// Table d'origine
		query+= "FROM " + table + " ";

		// Conditions
		query+= "WHERE ";

		s = where_name.size();
		if(s == 0)
		{
			query+= "1 ";
		}
		else
		{
			for (int i = 0; i < s; i++)
			{
				query+= where_name.get(i) + " = " + where_val.get(i);
				query+= ",";
			}
			query = query.substring(0, query.length()-1);
		}

		// Options SQL
		query+= opt;

		Statement st = co.createStatement();
		ResultSet r = st.executeQuery(query);
		co.close();

		return r;
	}

	/**
	 * La méthode exists() construit une requête SQL SELECT à l'aide de la méthode select() et de la liste des paramètres donnés, et renvoie TRUE si au moins un résultat a été trouvé, FALSE sinon.
	 * @param  table        table contenant les données recherchées
	 * @param  select_name  liste des colonnes voulues
	 * @param  where_name   liste des colonnes conditionnées
	 * @param  where_val    valeurs des colonnes conditionnées
	 * @param  opt          options sql
	 * @return              Booléen : True si un résultat a été trouvé, False sinon.
	 * @throws SQLException Erreur SQL
	 */
	public static boolean exists(String table, List<String> where_name, List<String> where_val) throws SQLException
	{
		ResultSet r = select(table, new ArrayList<String>(), where_name, where_val);
		boolean b = false;

		if(r.next())
			b = true;

		// Fermeture du Statement et du ResultSet
		r.getStatement().close();

		return b;
	}

	/**
	 * La méthode select() construit une requête SQL SELECT à l'aide de la liste de paramètres données
	 * @param  table        table contenant les données recherchées
	 * @param  select_name  liste des colonnes voulues
	 * @param  where_name   liste des colonnes conditionnées
	 * @param  where_val    valeurs des colonnes conditionnées
	 * @param  opt          options sql
	 * @return              Objet ResultSet contenant les résultats de la requête
	 * @throws SQLException Erreur SQL
	 */
	public static int insert(String table, List<String> where_name, List<String> where_val) throws SQLException
	{
		Connection co = getMySQLCo();
		String query = "INSERT INTO " + table + " ";

		// Colonnes
		query+= "(";
		
		for (int i = 0; i < where_name.size(); i++)
		{
			query+= where_name.get(i);
			query+= ",";
			query = query.substring(0, query.length()-1);
		}
		
		// Valeurs
		query+= ") VALUES (";
		
		for (int i = 0; i < where_val.size(); i++)
		{
			query+= where_val.get(i);
			query+= ",";
			query = query.substring(0, query.length()-1);
		}
		
		query+= ");";
		
		Statement st = co.createStatement();
		int r = st.executeUpdate(query);
		co.close();

		return r;
	}

}
