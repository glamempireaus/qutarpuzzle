package qutpuzzle.messages;

import java.util.ArrayList;
import java.util.List;

public class FetchScoreboardResponse
{
	public int errorCode;
	public String errorMessage;

	public List<UserScore> scores = new ArrayList<UserScore>();
}
