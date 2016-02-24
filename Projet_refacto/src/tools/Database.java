package tools;



public class Database
{
	private DataSource dataSource;
	private static Database dataBase;
	
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
	${
		if(DBStatic.mysql_pooling == false)
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
}
