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
import qutpuzzle.messages.AddTrophyRequest;
import qutpuzzle.messages.AddTrophyResponse;
import qutpuzzle.messages.AddUserRequest;
import qutpuzzle.messages.AddUserResponse;
import qutpuzzle.messages.FetchScoreboardRequest;
import qutpuzzle.messages.FetchScoreboardResponse;
import qutpuzzle.messages.StoreScoreRequest;
import qutpuzzle.messages.StoreScoreResponse;

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
	@Path("/addUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddUserResponse addUser(AddUserRequest request)
	{
		return RestActions.addUser(request, httpServletRequest, httpServletResponse);
	}

	@POST
	@Path("/storeScore")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StoreScoreResponse storeScore(StoreScoreRequest request)
	{
		return RestActions.storeScore(request, httpServletRequest, httpServletResponse);
	}

	@POST
	@Path("/addTrophy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddTrophyResponse addTrophy(AddTrophyRequest request)
	{
		return RestActions.addTrophy(request, httpServletRequest, httpServletResponse);
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