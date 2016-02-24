package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import tools.Database;


public class BDService
{
	public static boolean userExists(String login) throws SQLException
	{
			Connection co = Database.getMySQLConnection();
			String query = "SELECT id FROM users WHERE login = '" + login + "';";
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
			String query = "INSERT INTO users VALUES(null, '" + login + "', '" + password + "', '" + prenom + "', '" + nom + "')";
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
			int key = UUID.randomUUID().hashCode();
			while(r)
			{
				key = UUID.randomUUID().hashCode();
				String queryTest = "SELECT 'key' FROM users WHERE 'key' = " + key + ";";
				ResultSet res = st.executeQuery(queryTest);
				if(res.next())
					r = true;
				else
					r = false;
			}
			String query = "INSERT INTO session VALUES('" + key + "', '" + id + "', null, '" + root + "', '" + false + "'";
			st.executeUpdate(query);
			st.close();
			co.close();
	}
}

