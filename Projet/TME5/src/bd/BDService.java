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
	
	public static int loginUser(String login, String password) throws SQLException
	{
			Connection co = Database.getMySQLConnection();
			Statement st = co.createStatement();
			String query = "SELECT 'id' FROM users WHERE 'login' = " + login + ", 'password' = " + password + "";
			ResultSet res = st.executeQuery(query);
			int r = 0;
			r = res.getInt(1);
			st.close();
			co.close();
			
			return r;
	}
	
	public static int insertSession(int id, boolean root) throws SQLException
	{
			Connection co = Database.getMySQLConnection();
			Statement st = co.createStatement();
			boolean r = true;
			int key = UUID.randomUUID().hashCode();
			while(r)
			{
				key = UUID.randomUUID().hashCode();
				String queryTest = "SELECT 'key' FROM session WHERE 'key' = " + key + ";";
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
			
			return key;
	}
	
	public static boolean removeSession(int key) throws SQLException
	{
		Connection co = Database.getMySQLConnection();
		Statement st = co.createStatement();
		String queryTest = "UPDATE users SET 'expired' = FALSE WHERE 'key' = " + key + ";";
		boolean r = st.executeUpdate(queryTest) != 0;
		st.close();
		co.close();
		return r;
	}
	
	public static 
}

