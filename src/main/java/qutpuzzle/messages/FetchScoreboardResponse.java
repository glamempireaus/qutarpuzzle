package qutpuzzle.messages;

import java.util.ArrayList;
import java.util.List;

public class FetchScoreboardResponse
{
	public int errorCode;

	public List<UserScore> scores = new ArrayList<UserScore>();
}
