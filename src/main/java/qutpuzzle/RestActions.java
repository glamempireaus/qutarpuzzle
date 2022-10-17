package qutpuzzle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import qutpuzzle.messages.FetchScoreboardRequest;
import qutpuzzle.messages.FetchScoreboardResponse;
import qutpuzzle.messages.PlaceTrophyRequest;
import qutpuzzle.messages.PlaceTrophyResponse;

public class RestActions
{
	public static FetchScoreboardResponse fetchScoreboard(FetchScoreboardRequest request,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		FetchScoreboardResponse response = new FetchScoreboardResponse();

		/*
		 * // sanitize input
		 * 
		 * String processedEmail = Sanitize.email(request.email); String
		 * processedFirstName = Sanitize.personName(request.firstName); String
		 * processedLastName = Sanitize.personName(request.lastName);
		 * 
		 * // get password hash
		 * 
		 * byte[] salt = Hashing.generateSalt(); String passwordHash =
		 * Hashing.generateHashUsingSalt(request.password, salt);
		 */
		// check for a duplicate email address

		/*
		 * try { String query = "SELECT id FROM users WHERE email = ?";
		 * 
		 * PreparedStatement statement = Database.connection.prepareStatement(query);
		 * statement.setString(1, processedEmail);
		 * 
		 * ResultSet resultSet = statement.executeQuery();
		 * 
		 * if (resultSet.next()) { response.errorCode = 5; return response; }
		 * 
		 * } catch (SQLException e) { e.printStackTrace();
		 * 
		 * response.errorCode = 100; return response; }
		 * 
		 * // place processed input + hash into database
		 * 
		 * try { String query =
		 * "INSERT INTO users (email, passwordhash, firstname, lastname, passwordsalt, creationtimestamp) VALUES (?, ?, ?, ?, ?, ?)"
		 * ;
		 * 
		 * PreparedStatement statement = Database.connection.prepareStatement(query);
		 * statement.setString(1, processedEmail); statement.setString(2, passwordHash);
		 * statement.setString(3, processedFirstName); statement.setString(4,
		 * processedLastName); statement.setBytes(5, salt); statement.setDate(6, new
		 * java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime()));
		 * 
		 * int queryResult = statement.executeUpdate(); if (queryResult != 1) {
		 * response.errorCode = 100; return response; }
		 * 
		 * } catch (SQLException e) { e.printStackTrace();
		 * 
		 * response.errorCode = 100; return response; }
		 */

		// authenticate user

		response.errorCode = 0;
		return response;
	}

	public static PlaceTrophyResponse placeTrophy(PlaceTrophyRequest request, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
	{
		PlaceTrophyResponse response = new PlaceTrophyResponse();

		response.errorCode = 0;
		return response;
	}
}