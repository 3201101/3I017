package net.ln.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;


/**
 * DBService
 *
 * Bibliothèque de fonctions de manipulation des bases de données.
 */
public class DBService
{
	private static Database mysql_db;
	private static Database mongo_db;

	/**
	 * La méthode getMySQLCo() retourne un objet Connection lié à la base de données MySQL de l'application.
	 * @return Objet Connection
	 * @throws SQLConnection Exception signalant une erreur de la base de données
	 */
	private static Connection getMySQLCo() throws SQLConnection
	{
		Database mysql_db;

		if(DBSettings.db_mysql_pooling == false)
		{
			return DriverManager.getConnection("jdbc:mysql://" + DBSettings.mysql_host + "/" + DBSettings.mysql_db, DBSettings.mysql_username, DBSettings.mysql_password);
		}
		else
		{
			return new Database("jdbc/db").getConnection();
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
	 * La méthode getMongoCo() retourne la collection MongoDB dont le nom est passé en paramètre.
	 * @param  table Nom de la collection voulue
	 * @return       Objet Collection
	 */
	private static Collection getMongoCo(String table)
	{
		return this.getMongo().getCollection(table);
	}

	static JSONObject serviceRefused(String message, int codeErreur) throws JSONException
	{
		return new JSONObject("{'Erreur' : "+ codeErreur +", 'Message :' : '" + message + "'}");
	}
	
	static JSONObject serviceAccepted() throws JSONException
	{
		return new JSONObject("{'OK' : 'OK'}");
	}

	public static select(String table) throws SQLException
	{
		return select(table, new ArrayList<String>());
	}

	public static select(String table, ArrayList<String> select_name) throws SQLException
	{
		return select(table, select_name, new ArrayList<String>(), new ArrayList<String>());
	}

	public static select(String table, ArrayList<String> select_name, ArrayList<String> where_name, ArrayList<String> where_val) throws SQLException
	{
		return select(table, select_name, nwhere_name, where_val, "");
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
	public static ResultSet select(String table, ArrayList<String> select_name, ArrayList<String> where_name, ArrayList<String> where_val, String opt) throws SQLException
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
			for (int i = 0; i < s; i++)
			{
				query+= select_name.get(i);
				if(i < (s-1))
					query+= ",";
				query+= " ";
			}
		}

		// Table d'origine
		query+= "FROM " + table + " ";

		// Conditions
		query+= "WHERE "

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
				if(i < (s-1))
					query+= ",";
				query+= " ";
			}
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
	public static boolean exists(String table, ArrayList<String> select_name, ArrayList<String> where_name, ArrayList<String> where_val) throws SQLException
	{
		ResultSet r = select(table, select_name, nwhere_name, where_val);
		boolean b = false;

		if(r.next())
			b = true;

		// Fermeture du Statement et du ResultSet
		r.getStatement().close();

		return b;
	}





	public static boolean userExists(String login) throws SQLException
	{
		Connection co = Database.getMySQLConnection();
		String query = "SELECT `id` FROM users WHERE `login` = " + login + ";";
		Statement st = co.createStatement();
		ResultSet res = st.executeQuery(query);
		boolean r;
		if(res.next())
			r = true;
		else
			r = false;
		res.close();
		st.close();
		co.close();
		return r;
	}

	public static void insertUser(String login, String password, String nom, String prenom) throws SQLException
	{
		Connection co = Database.getMySQLConnection();
		String query = "INSERT INTO users VALUES(null, `" + login + "`, `" + password + "`, `" + prenom + "`, `" + nom + "`";
		Statement st = co.createStatement();
		st.executeUpdate(query);
		st.close();
		co.close();
	}

	public static void insertSession(String id, boolean root) throws SQLException
	{
		Connection co = Database.getMySQLConnection();
		Statement st = co.createStatement();
		boolean r = true;
		while(r)
		{
			int key = UUID.randomUUID();
			String queryTest = "SELECT `key` FROM users WHERE `key` = " + key + ";";
			ResultSet res = st.executeQuery(queryTest);
			if(res.next())
				r = true;
			else
				r = false;
		}
		String query = "INSERT INTO session VALUES(`" + key + "`, `" + id + "`, null, `" + root + "`, `" + false + "`";
		st.executeUpdate(query);
		st.close();
		co.close();
	}
}
