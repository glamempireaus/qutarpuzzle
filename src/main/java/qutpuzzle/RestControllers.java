package qutpuzzle;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import qutpuzzle.messages.AddUserRequest;
import qutpuzzle.messages.AddUserResponse;
import qutpuzzle.messages.CheckUserExistsRequest;
import qutpuzzle.messages.CheckUserExistsResponse;
import qutpuzzle.messages.FetchScoreboardRequest;
import qutpuzzle.messages.FetchScoreboardResponse;
import qutpuzzle.messages.StoreFinishedTimeRequest;
import qutpuzzle.messages.StoreFinishedTimeResponse;
import qutpuzzle.messages.StoreTrophyRequest;
import qutpuzzle.messages.StoreTrophyResponse;

@Path("/")
public class RestControllers
{
	@Context
	ServletContext context;
	@Context
	HttpServletRequest httpServletRequest;
	@Context
	HttpServletResponse httpServletResponse;

	@POST
	@Path("/checkUserExists")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CheckUserExistsResponse checkUserExists(CheckUserExistsRequest request)
	{
		return RestActions.checkUserExists(request, httpServletRequest, httpServletResponse);
	}

	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddUserResponse createUser(AddUserRequest request)
	{
		return RestActions.createUser(request, httpServletRequest, httpServletResponse);
	}

	@POST
	@Path("/storeFinishedTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StoreFinishedTimeResponse storeFinishedTime(StoreFinishedTimeRequest request)
	{
		return RestActions.storeFinishedTime(request, httpServletRequest, httpServletResponse);
	}

	@POST
	@Path("/storeTrophy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StoreTrophyResponse storeTrophy(StoreTrophyRequest request)
	{
		return RestActions.storeTrophy(request, httpServletRequest, httpServletResponse);
	}

	@POST
	@Path("/fetchScoreboard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public FetchScoreboardResponse fetchScoreboard(FetchScoreboardRequest request)
	{
		return RestActions.fetchScoreboard(request, httpServletRequest, httpServletResponse);
	}
}