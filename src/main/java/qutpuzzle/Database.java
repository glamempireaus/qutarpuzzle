package qutpuzzle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database
{
	public static String user = "postgres";
	public static String password = "1212";
	public static String schema = "public";
	public static String connectionUrl = "jdbc:postgresql://localhost:5432/qutpuzzle";
	public static Connection connection;

	public static void init()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					connectionUrl + "?currentSchema=" + schema + "&user=" + user + "&password=" + password);
		} catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();

		}
	}

	public static int getUserIdFromSessionHash(String sessionHash)
	{
		int userId = -1;

		try
		{
			String query = "SELECT id from usersessions where sessionhash = ?";

			PreparedStatement statement = Database.connection.prepareStatement(query);
			statement.setString(1, sessionHash);

			ResultSet resultSet = statement.executeQuery();

			// handle incorrect hash

			if (!resultSet.next())
			{
				return -2;
			}

			userId = resultSet.getInt(1);

		} catch (SQLException e)
		{
			e.printStackTrace();

			return -100;
		}

		return userId >= 0 ? userId : -1;
	}
}