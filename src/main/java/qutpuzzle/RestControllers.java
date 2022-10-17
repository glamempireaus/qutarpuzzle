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
import qutpuzzle.messages.FetchScoreboardRequest;
import qutpuzzle.messages.FetchScoreboardResponse;
import qutpuzzle.messages.PlaceTrophyRequest;
import qutpuzzle.messages.PlaceTrophyResponse;

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
	@Path("/fetchScoreboard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public FetchScoreboardResponse fetchScoreboard(FetchScoreboardRequest request)
	{
		return RestActions.fetchScoreboard(request, httpServletRequest, httpServletResponse);
	}

	@POST
	@Path("/loginUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlaceTrophyResponse placeTrophyUser(PlaceTrophyRequest request)
	{
		return RestActions.placeTrophy(request, httpServletRequest, httpServletResponse);
	}

}