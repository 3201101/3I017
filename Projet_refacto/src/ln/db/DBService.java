package ln.db;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.UUID;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mysql.jdbc.Connection;




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
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */	
	private static DB getMongo() throws UnknownHostException, MongoException
	{
		return new Mongo(DBSettings.db_host, DBSettings.db_mongo_port).getDB(DBSettings.db_name);
	}

	/**
	 * La méthode getMongoCo() retourne la Collection MongoDB dont le nom est passé en paramètre.
	 * @param  table Nom de la Collection voulue
	 * @return       Objet Collection
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	private static DBCollection getMongoCo(String table) throws UnknownHostException, MongoException
	{
		return getMongo().getCollection(table);
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

	/**
	 * Sélectionne toutes les entrées d'une table SQL.
	 * @param table <String> Nom de la table contenant les entrées voulues
	 * @return <ResultSet> Résultats
	 * @throws SQLException
	 */
	public static ResultSet select(String table) throws SQLException
	{
		return select(table, new ArrayList<String>());
	}

	/**
	 * Sélectionne certains champs de toutes les entrées d'une table SQL.
	 * @param table <String> Nom de la table contenant les entrées voulues
	 * @param select_name <Collection<String>> Collectione des champs voulus
	 * @return <ResultSet> Résultats
	 * @throws SQLException
	 */
	public static ResultSet select(String table, Collection<String> select_name) throws SQLException
	{
		return select(table, select_name, new ArrayList<String>(), new ArrayList<String>());
	}

	/**
	 * Sélectionne certains champs d'entrées d'une table SQL avec conditions.
	 * @param table <String> Nom de la table contenant les entrées voulues
	 * @param select_name <Collection<String>> Collectione des champs voulus
	 * @param where_name <Collection<String>> Collectione des champs conditionnés
	 * @param where_val <Collection<String>> Collectione des conditions sur les champs
	 * @return <ResultSet> Résultats
	 * @throws SQLException
	 */
	public static ResultSet select(String table, Collection<String> select_name, Collection<String> where_name, Collection<String> where_val) throws SQLException
	{
		return select(table, select_name, where_name, where_val, "");
	}

	/**
	 * Sélectionne certains champs d'entrées d'une table SQL avec conditions et options.
	 * @param  table        <String> Nom de la table contenant les entrées voulues
	 * @param  select_name  <Collection<String>> Collectione des champs voulus
	 * @param  where_name   <Collection<String>> Collectione des champs conditionnés
	 * @param  where_val    <Collection<String>> Collectione des conditions sur les champs
	 * @param  opt          <String> Options SQL
	 * @return              Objet ResultSet contenant les résultats de la requête
	 * @throws SQLException Erreur SQL
	 */
	public static ResultSet select(String table, Collection<String> select_name, Collection<String> where_name, Collection<String> where_val, String opt) throws SQLException
	{
		if(where_name.size() != where_val.size())
			throw new IllegalArgumentException();
		
		Connection co = getMySQLCo();
		String query = "SELECT ";

		// Colonnes à sélectionner
		int s = select_name.size();
		if(s == 0)
			query+= "* ";
		else
		{
			for (String i : select_name)
				query+= i + ",";
			
			query = query.substring(0, query.length()-1);
		}

		// Table d'origine
		query+= "FROM " + table + " ";

		// Conditions
		if(where_name.size() > 0)
		{
			query+= "WHERE ";
			Iterator<String> nI = where_name.iterator();
			Iterator<String> vI = where_val.iterator();
			
			while(vI.hasNext() && nI.hasNext())
				query+= nI.next()  + " = '" + vI.next() + "',";
			
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
	 * Vérifie l'existance d'une valeur dans la base de données respectant les paramètres donnés.
	 * @param  table        table contenant les données recherchées
	 * @param  select_name  Collectione des colonnes voulues
	 * @param  where_name   Collectione des colonnes conditionnées
	 * @param  where_val    valeurs des colonnes conditionnées
	 * @param  opt          options sql
	 * @return              Booléen : True si un résultat a été trouvé, False sinon.
	 * @throws SQLException Erreur SQL
	 */
	public static boolean exists(String table, Collection<String> where_name, Collection<String> where_val) throws SQLException
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
	 * Insère une nouvelle valeur dans la base de données
	 * @param  table        table contenant les données recherchées
	 * @param  insert_name  Nom des colones à insérer
	 * @param  insert_val   Valeurs à insérer
	 * @param  opt          options sql
	 * @return              Objet ResultSet contenant les résultats de la requête
	 * @throws SQLException Erreur SQL
	 */
	public static int insert(String table, Collection<String> insert_name, Collection<String> insert_val) throws SQLException
	{
		Connection co = getMySQLCo();
		String query = "INSERT INTO " + table + " ";

		// Colonnes
		query+= "(";
		
		for (String i : insert_name)
			query+= i + ",";
		
		query = query.substring(0, query.length()-1) + ") ";
		
		// Valeurs
		query+= "VALUES (";
		
		for (String i : insert_name)
			query+= "'" + i + "',";
		
		query = query.substring(0, query.length()-1) + ");";
		
		Statement st = co.createStatement();
		int r = st.executeUpdate(query);
		co.close();

		return r;
	}
	
	/**
	 * Met à jour une valeur existante dans la base de données
	 * @param  table        table contenant les données recherchées
	 * @param  insert_name  Nom des colones à insérer
	 * @param  insert_val   Valeurs à insérer
	 * @param  where_name   Collectione des colonnes conditionnées
	 * @param  where_val    valeurs des colonnes conditionnées
	 * @param  opt          options sql
	 * @return              Objet ResultSet contenant les résultats de la requête
	 * @throws SQLException Erreur SQL
	 */
	public static int update(String table, Collection<String> insert_name, Collection<String> insert_val, Collection<String> where_name, Collection<String> where_val) throws SQLException
	{
		Connection co = getMySQLCo();
		String query = "UPDATE " + table + " ";

		// Colonnes
		query+= "SET ";
		
		Iterator<String> inI = where_name.iterator();
		Iterator<String> ivI = where_val.iterator();
		
		while(ivI.hasNext() && inI.hasNext())
			query+= inI.next()  + " = '" + ivI.next() + "',";
		
		query = query.substring(0, query.length()-1);
		
		query+= "WHERE ";
		
		Iterator<String> wnI = where_name.iterator();
		Iterator<String> wvI = where_val.iterator();
		
		while(wvI.hasNext() && wnI.hasNext())
			query+= wnI.next()  + " = '" + wvI.next() + "',";
		
		query = query.substring(0, query.length()-1);
		
		Statement st = co.createStatement();
		int r = st.executeUpdate(query);
		co.close();

		return r;
	}

}
