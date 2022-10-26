package qutpuzzle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import qutpuzzle.messages.AddUserRequest;
import qutpuzzle.messages.AddUserResponse;
import qutpuzzle.messages.CheckUserExistsRequest;
import qutpuzzle.messages.CheckUserExistsResponse;
import qutpuzzle.messages.FetchScoreboardRequest;
import qutpuzzle.messages.FetchScoreboardResponse;
import qutpuzzle.messages.FetchTrophiesRequest;
import qutpuzzle.messages.FetchTrophiesResponse;
import qutpuzzle.messages.StoreFinishedTimeRequest;
import qutpuzzle.messages.StoreFinishedTimeResponse;
import qutpuzzle.messages.StoreTrophyRequest;
import qutpuzzle.messages.StoreTrophyResponse;
import qutpuzzle.messages.Trophy;
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

	public static CheckUserExistsResponse checkUserExists(CheckUserExistsRequest request,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		CheckUserExistsResponse response = new CheckUserExistsResponse();

		// deviceId check

		if (request.deviceId.isEmpty() || request.deviceId == null)
		{
			response.errorCode = 1;
			response.errorMsg = "Your deviceID is invalid.";
			return response;
		}

		if (request.deviceId.length() > 256)
		{
			response.errorCode = 2;
			response.errorMsg = "Your deviceID is an invalid length.";
			return response;
		}

		String processedDeviceId = request.deviceId;

		if (request.deviceId.length() > 256)
		{
			processedDeviceId = request.deviceId.substring(0, 256);
		}

		// check for duplicate deviceId

		if (queryDeviceId(processedDeviceId))
		{
			response.errorCode = 1;
			response.errorMsg = "This deviceID already exists.";
			return response;
		}

		response.errorCode = 0;
		return response;

	}

	public static AddUserResponse createUser(AddUserRequest request, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
	{
		AddUserResponse response = new AddUserResponse();

		// deviceId check

		if (request.deviceId.isEmpty() || request.deviceId == null)
		{
			response.errorCode = 1;
			response.errorMsg = "Your deviceID is invalid.";
			return response;
		}

		if (request.deviceId.length() > 256)
		{
			response.errorCode = 2;
			response.errorMsg = "Your deviceID is an invalid length.";
			return response;
		}

		String processedDeviceId = request.deviceId;

		if (request.deviceId.length() > 256)
		{
			processedDeviceId = request.deviceId.substring(0, 256);
		}

		// displayName check

		if (request.displayName.isEmpty() || request.displayName == null)
		{
			response.errorCode = 3;
			response.errorMsg = "This display name is an invalid.";
			return response;
		}

		String processedDisplayName = Sanitize.aliasName(request.displayName);

		// check for duplicate deviceId

		if (queryDeviceId(processedDeviceId))
		{
			response.errorCode = 4;
			response.errorMsg = "This deviceid already exists.";
			return response;
		}

		// add user

		try
		{
			String query = "INSERT INTO users (deviceid, displayname) VALUES (?, ?)";

			PreparedStatement statement = Database.connection.prepareStatement(query);
			statement.setString(1, processedDeviceId);
			statement.setString(2, processedDisplayName);

			int queryResult = statement.executeUpdate();
			if (queryResult != 1)
			{
				response.errorCode = 100;
				response.errorMsg = "There was a problem with our backend.";
				return response;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();

			response.errorCode = 100;
			response.errorMsg = "There was a problem with our backend.";
			return response;
		}

		response.errorCode = 0;
		return response;
	}

	public static StoreFinishedTimeResponse storeFinishedTime(StoreFinishedTimeRequest request,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		StoreFinishedTimeResponse response = new StoreFinishedTimeResponse();

		// deviceId check

		if (request.deviceId.isEmpty() || request.deviceId == null)
		{
			response.errorCode = 1;
			response.errorMsg = "Your deviceID is invalid.";
			return response;
		}

		String processedDeviceId = request.deviceId;

		if (request.deviceId.length() > 256)
		{
			processedDeviceId = request.deviceId.substring(0, 256);
		}

		// timeFinished check

		if (request.timeFinished == 0)
		{
			response.errorCode = 4;
			response.errorMsg = "The time finished is invalid.";
		}

		Time timeFinished = new java.sql.Time(request.timeFinished);

		Time maximumTime = new java.sql.Time(21600000); // 6 hours
		if (timeFinished.after(maximumTime))
		{
			response.errorCode = 5;
			response.errorMsg = "This time is longer than 6 hours.";
			return response;
		}

		// check if deviceId is registered (to prevent anonymous api usage)

		if (!queryDeviceId(processedDeviceId))
		{
			response.errorCode = 10;
			response.errorMsg = "The deviceId is not registered.";
			return response;
		}

		// create insert query

		try
		{
			String query = "UPDATE users SET timefinished = ? WHERE deviceid = ?";

			PreparedStatement statement = Database.connection.prepareStatement(query);
			statement.setLong(1, request.timeFinished);
			statement.setString(2, processedDeviceId);

			int queryResult = statement.executeUpdate();
			if (queryResult != 1)
			{
				response.errorCode = 100;
				response.errorMsg = "There was a problem with our backend.";
				return response;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();

			response.errorCode = 100;
			response.errorMsg = "There was a problem with our backend.";
			return response;
		}

		response.errorCode = 0;
		return response;
	}

	public static StoreTrophyResponse storeTrophy(StoreTrophyRequest request, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
	{
		StoreTrophyResponse response = new StoreTrophyResponse();

		// deviceId check

		if (request.deviceId.isEmpty() || request.deviceId == null)
		{
			response.errorCode = 1;
			response.errorMsg = "Your deviceID is invalid.";
			return response;
		}

		String processedDeviceId = request.deviceId;

		if (request.deviceId.length() > 256)
		{
			processedDeviceId = request.deviceId.substring(0, 256);
		}

		// trophyType check

		int maxTrophyTop = 9;
		int maxTrophyBottom = 10;
		if (request.trophy.trophyTopModel > maxTrophyTop || request.trophy.trophyTopTexture > maxTrophyTop
				|| request.trophy.trophyBottomModel > maxTrophyBottom
				|| request.trophy.trophyBottomTexture > maxTrophyBottom || request.trophy.trophyTopModel < 0
				|| request.trophy.trophyTopTexture < 0 || request.trophy.trophyTopModel < 0
				|| request.trophy.trophyTopTexture < 0)
		{
			response.errorCode = 3;
			response.errorMsg = "Trophy model or texture types are invalid.";
			return response;
		}

		// check if deviceId is registered (to prevent anonymous api usage)

		if (!queryDeviceId(processedDeviceId))
		{
			response.errorCode = 10;
			response.errorMsg = "The device is already registered.";
			return response;
		}

		// check if there's a finished time

		try
		{
			String query = "SELECT timefinished FROM users WHERE deviceid = ? AND timefinished IS NOT NULL";

			PreparedStatement statement = Database.connection.prepareStatement(query);
			statement.setString(1, processedDeviceId);

			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.next())
			{
				response.errorCode = 4;
				response.errorMsg = "There is no finishing time!";
				return response;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();

			response.errorCode = 100;
			response.errorMsg = "There was a problem with our backend.";
			return response;
		}

		// add trophy data

		try
		{
			String query = "UPDATE users SET trophytopmodel = ?, trophytoptexture = ?, trophybottommodel = ?, trophybottomtexture = ? WHERE deviceid = ?";

			PreparedStatement statement = Database.connection.prepareStatement(query);
			statement.setInt(1, request.trophy.trophyTopModel);
			statement.setInt(2, request.trophy.trophyTopTexture);
			statement.setInt(3, request.trophy.trophyBottomModel);
			statement.setInt(4, request.trophy.trophyBottomTexture);
			statement.setString(5, processedDeviceId);

			int queryResult = statement.executeUpdate();
			if (queryResult != 1)
			{
				response.errorCode = 100;
				response.errorMsg = "There was a problem with our backend.";
				return response;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();

			response.errorCode = 100;
			response.errorMsg = "There was a problem with our backend.";
			return response;
		}

		response.errorCode = 0;
		return response;
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

		String processedDeviceId = request.deviceId;

		if (request.deviceId.length() > 256)
		{
			processedDeviceId = request.deviceId.substring(0, 256);
		}

		// check if deviceId is registered (to prevent anonymous api usage)

		if (!queryDeviceId(processedDeviceId))
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

			int placementIndex = 1;
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
					userScore.placement = placementIndex;
					placementIndex++;
					userScore.timeFinished = resultSet.getInt(2);
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

	public static FetchTrophiesResponse fetchTrophies(FetchTrophiesRequest request,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		FetchTrophiesResponse response = new FetchTrophiesResponse();

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

		String processedDeviceId = request.deviceId;

		if (request.deviceId.length() > 256)
		{
			processedDeviceId = request.deviceId.substring(0, 256);
		}

		// check if deviceId is registered (to prevent anonymous api usage)

		if (!queryDeviceId(processedDeviceId))
		{
			response.errorCode = 10;
			return response;
		}

		// get trophies
		try
		{
			String query = "SELECT displayName, timefinished, trophytopmodel, trophytoptexture, trophybottommodel, trophybottomtexture FROM users WHERE timefinished IS NOT NULL ORDER BY timefinished ASC LIMIT 40";

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
					Trophy trophy = new Trophy();
					trophy.displayName = resultSet.getString(1);
					trophy.timeFinished = resultSet.getLong(2);
					trophy.trophyTopModel = resultSet.getInt(3);
					trophy.trophyTopTexture = resultSet.getInt(4);
					trophy.trophyBottomModel = resultSet.getInt(5);
					trophy.trophyBottomTexture = resultSet.getInt(6);

					response.trophies.add(trophy);
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
}