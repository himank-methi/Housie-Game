import java.util.ArrayList;
import java.util.*;  

public class Main{
	 
	public static void main(String[] args) {
		
		System.out.println("How many players?");	
		Scanner cin= new Scanner(System.in); 
		final int n = cin.nextInt();  		// input, number of players
		System.out.println("Number of players: " + n);
	
		
		// shared data object
		Data data = Data.getInstance();			// singleton class since only one needed
		data.init(n);	
		
		
		// These all objects are for command pattern
	   	Methods m = new Methods(data);
		generate g = new generate(m);
	    didWin win = new didWin(m);
	    Invoker invoke = new Invoker();
		
		Moderator m1 = Moderator.getInstance();		// singleton class as there can be  
													// only one moderator at max
		m1.setter(data, n, invoke, g, win);	
		
		// Since no start method in runnable object, hence
		// we use thread class to run the runnable class objects
		Thread t1 = new Thread(m1,"Moderator");
		// Starting  the threads
		t1.start();

		
		ArrayList<Player> players= new ArrayList<Player>();			// store player objects
		ArrayList<Thread> playerThreads = new ArrayList<Thread>();	// player objects threads
		
		// player objects, each player is a single thread
		for(int i=0;i<n;i++) {
			Player p = new Player((i+1), data, n, invoke, g, win);
			players.add(p);
		}
		
		for(int i=0;i<n;i++) {
			Thread t = new Thread(players.get(i));
			playerThreads.add(t);
		}
		
		for(int i=0;i<n;i++) {
			playerThreads.get(i).start();
		}
		
		
		// Using join to make main class wait for player and moderator
		// threads to end
		// Closing in try catch block as it might throw exception here
		try {
			t1.join();

			for(int i=0;i<n;i++) {
				playerThreads.get(i).join();
			}
		} catch (Exception e) {
			System.out.println("Main Interrupted"); 
		}
		
		// Game end block
		// executing the execute of didWin concrete command class
		invoke.executeCommand(win);
		
		// This game end block is run only when nobody won
		// If no player got 3 matches i.e they got <3 matches, game should mention
		// nobody won
		if(data.won == 0) {
			System.out.println("Nobody Won!!" + '\n');
			
			// Printing total number of matches each player got when the game ended
			System.out.println("Number of matches of each player when the game ended");
			for(int i=0;i<n;i++) {
				System.out.println("Player" + (i+1) + " : " + data.noOfMatches.get(i));
			}
			System.out.println('\n' + "Game Completed, bbye");
		}
	}
}

