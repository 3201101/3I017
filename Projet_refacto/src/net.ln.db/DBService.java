package net.ln.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import com.mysql.jdbc.Connection;


/**
 * DBService
 *
 * Bibliothèque de fonctions de manipulation des bases de données.
 */
public class DBService
{
	private static Database database;

	/**
	 * La méthode getMySQLCo() retourne un objet Connection lié à la base de données MySQL de l'application.
	 * @return Objet Connection
	 * @throws SQLConnection Exception signalant une erreur de la base de données
	 */
	private static Connection getMySQLCo() throws SQLConnection
	{
		if(DBSettings.db_mysql_pooling == false)
		{
			return DriverManager.getConnection("jdbc:mysql://" + DBSettings.mysql_host + "/" + DBSettings.mysql_db, DBSettings.mysql_username, DBSettings.mysql_password);
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

	/**
	 * La méthode getMongoCo() retourne un objet Connection lié à la base de données MongoDB de l'application.
	 * @return Objet Connection
	 */
	private static Connection getMongoCo()
	{

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
