package qutpuzzle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import qutpuzzle.messages.AddTrophyRequest;
import qutpuzzle.messages.AddTrophyResponse;
import qutpuzzle.messages.AddUserRequest;
import qutpuzzle.messages.AddUserResponse;
import qutpuzzle.messages.FetchScoreboardRequest;
import qutpuzzle.messages.FetchScoreboardResponse;
import qutpuzzle.messages.StoreScoreRequest;
import qutpuzzle.messages.StoreScoreResponse;
import qutpuzzle.messages.UserScore;

public class RestActions
{
	private static boolean queryDeviceId(String deviceId)
	{
		try
		{
			String query = "SELECT id FROM users WHERE deviceid = ?";

			PreparedStatement statement = Database.connection.prepareStatement(query);
			statement.setString(1, deviceId);

			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.next())
			{
				return false;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	public static FetchScoreboardResponse fetchScoreboard(FetchScoreboardRequest request,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		FetchScoreboardResponse response = new FetchScoreboardResponse();

		// check if input is correct

		if (request.deviceId.isEmpty() || request.deviceId == null)
		{
			response.errorCode = 1;
			return response;
		}

		if (request.deviceId.length() > 256)
		{
			response.errorCode = 2;
			return response;
		}

		// check if deviceId is registered (to prevent anonymous api usage)

		if (!queryDeviceId(request.deviceId))
		{
			response.errorCode = 10;
			return response;
		}

		// get scoreboard
		try
		{
			String query = "SELECT displayName, timefinished FROM users WHERE timeFinished IS NOT NULL ORDER BY timefinished ASC LIMIT 10";

			PreparedStatement statement = Database.connection.prepareStatement(query);

			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.next())
			{
				response.errorCode = 3;
				return response;
			} else
			{
				do
				{
					UserScore userScore = new UserScore();
					userScore.displayName = resultSet.getString(1);
					userScore.timeFinished = resultSet.getString(2);

					response.scores.add(userScore);

				} while (resultSet.next());
			}

		} catch (SQLException e)
		{
			e.printStackTrace();

			response.errorCode = 100;
			return response;
		}

		System.out.println(response.errorCode);

		// authenticate user

		response.errorCode = 0;
		return response;
	}

	public static StoreScoreResponse storeScore(StoreScoreRequest request, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
	{
		StoreScoreResponse response = new StoreScoreResponse();

		// deviceId check

		if (request.deviceId.isEmpty() || request.deviceId == null)
		{
			response.errorCode = 1;
			return response;
		}

		if (request.deviceId.length() > 256)
		{
			response.errorCode = 2;
			return response;
		}

		// timeFinished check

		if (request.timeFinished == 0)
		{
			response.errorCode = 4;
		}

		Time timeFinished = new java.sql.Time(request.timeFinished);

		Time maximumTime = new java.sql.Time(21600000); // 6 hours
		if (timeFinished.after(maximumTime))
		{
			response.errorCode = 5;
			return response;
		}

		// check if deviceId is registered (to prevent anonymous api usage)

		if (!queryDeviceId(request.deviceId))
		{
			response.errorCode = 10;
			return response;
		}

		// create insert query

		try
		{
			String query = "UPDATE users SET timefinished = ? WHERE deviceid = ?";

			PreparedStatement statement = Database.connection.prepareStatement(query);
			statement.setTime(1, new java.sql.Time(request.timeFinished));
			statement.setString(2, request.deviceId);

			int queryResult = statement.executeUpdate();
			if (queryResult != 1)
			{
				response.errorCode = 100;
				return response;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();

			response.errorCode = 100;
			return response;
		}

		response.errorCode = 0;
		return response;
	}

	public static AddTrophyResponse addTrophy(AddTrophyRequest request, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
	{
		AddTrophyResponse response = new AddTrophyResponse();

		// deviceId check

		if (request.deviceId.isEmpty() || request.deviceId == null)
		{
			response.errorCode = 1;
			return response;
		}

		if (request.deviceId.length() > 256)
		{
			response.errorCode = 2;
			return response;
		}

		// trophyType check

		if (request.trophyType > 5)
		{
			response.errorCode = 3;
			return response;
		}

		// check if deviceId is registered (to prevent anonymous api usage)

		if (!queryDeviceId(request.deviceId))
		{
			response.errorCode = 10;
			return response;
		}

		response.errorCode = 0;
		return response;
	}

	public static AddUserResponse addUser(AddUserRequest request, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
	{
		AddUserResponse response = new AddUserResponse();

		// deviceId check

		if (request.deviceId.isEmpty() || request.deviceId == null)
		{
			response.errorCode = 1;
			return response;
		}

		if (request.deviceId.length() > 256)
		{
			response.errorCode = 2;
			return response;
		}

		// displayName check

		if (request.displayName.isEmpty() || request.displayName == null)
		{
			response.errorCode = 3;
			return response;
		}

		String processedDisplayName = Sanitize.aliasName(request.displayName);

		// check for duplicate deviceId

		if (queryDeviceId(request.deviceId))
		{
			response.errorCode = 4;
			return response;
		}

		// add user

		try
		{
			String query = "INSERT INTO users (deviceid, displayname) VALUES (?, ?)";

			PreparedStatement statement = Database.connection.prepareStatement(query);
			statement.setString(1, request.deviceId);
			statement.setString(2, processedDisplayName);

			int queryResult = statement.executeUpdate();
			if (queryResult != 1)
			{
				response.errorCode = 100;
				return response;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();

			response.errorCode = 100;
			return response;
		}

		response.errorCode = 0;
		return response;
	}
}