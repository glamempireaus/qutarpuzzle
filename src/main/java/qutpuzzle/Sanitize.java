package qutpuzzle;

public class Sanitize
{
	public static String aliasName(String in)
	{
		return in.replaceAll("[^\\p{Alpha} &/.()\\-,']", "").trim();
	}
}
