package ln.db;

import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
	/**
	 * Méthodes liées à la manipulation de bases de données MySQL
	 */

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
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
			}
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return (Connection) DriverManager.getConnection("jdbc:mysql://" + DBSettings.db_host + ":" + DBSettings.db_mysql_port + "/" + DBSettings.db_name, DBSettings.db_user, DBSettings.db_pass);
		}
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
				query+= nI.next()  + " = '" + vI.next() + "' AND ";
			
			query = query.substring(0, query.length()-5);
		}

		// Options SQL
		query+= opt;

		Statement st = co.createStatement();
		ResultSet r = st.executeQuery(query);
		
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
		
		for (String i : insert_val)
			query+= "'" + i + "',";
		
		query = query.substring(0, query.length()-1) + ");";
		System.out.println(query);
		Statement st = co.createStatement();
		int r = st.executeUpdate(query);

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

		return r;
	}

	/**
	 * Méthodes liées à la manipulation de bases de données MongoDB
	 */

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
	public static DBCollection getMongoCo(String table) throws UnknownHostException, MongoException
	{
		return getMongo().getCollection(table);
	}
	
	/**
	 * Recherche un document dans une table de la base de données MongoDB.
	 * @param table Collection visée par la recherche 
	 * @param o BasicDBObject contenant les critères de recherche
	 * @return DBCursor Curseur pointant sur les résultats de la recherche
	 * @throws MongoException
	 * @throws UnknownHostException
	 */
	public static DBCursor find(String table, BasicDBObject o) throws MongoException, UnknownHostException
	{
		return getMongoCo(table).find(o);
	}

	/**
	 * Ajoute un nouveau document dans une table de la base de données MongoDB.
	 * @param table Collection à modifier
	 * @param o BasicDBObject à insérer
	 * @return True si l'ajout est réussi
	 * @throws MongoException
	 * @throws UnknownHostException
	 */
	public static boolean add(String table, BasicDBObject o) throws MongoException, UnknownHostException
	{
		getMongoCo(table).insert(o);
		return true;
	}
	
	
}
