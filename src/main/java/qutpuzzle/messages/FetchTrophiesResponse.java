package qutpuzzle.messages;

import java.util.ArrayList;
import java.util.List;

public class FetchTrophiesResponse
{
	public int errorCode;
	public String errorMessage;

	public List<Trophy> trophies = new ArrayList<Trophy>();
}
