package qutpuzzle;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class Main
{
	@PostConstruct
	public void main()
	{
		System.out.println("Starting QUT AR Puzzle server");

		Database.init();

	}
}
