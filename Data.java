import java.util.ArrayList;

class Data{
	
	static Data uniqueObject;		
	private Data(){}		// constructor 
		
	// using getInstance method to create new object if uniqueOnject pointer in null
	// and if it is not null, then returns the already created object
	// using synchronized keyword so that only one object can access it
	// at particular time, to protect from accidently generating two objects
	public static synchronized Data getInstance() { 
		if(uniqueObject == null) {
			uniqueObject = new Data();
		}
		return uniqueObject;
	}
  
	// Declaring variables
	public int noOfPlayers;	
	public int generate;		// stores randomly generated number
	public int won;				// set if any player won
	
	// To maintain all the generated numbers
	public ArrayList<Integer> generatedNumbers = new ArrayList<Integer>();
	
	// using it as counter to synchronize among player threads 
	// and also players and moderator threads
	static int number = 0;
	
	// to synchronize players and moderator threads
	public Object lock = new Object();
	
	// Number generated flag
	static boolean noGenerated = false;
	
	//	To keep track of which player won, initially all zeros, if any won then corresponding set to one
	public ArrayList<Integer> playerWon = new ArrayList<Integer>();
	
	// To keep track of number of matches of each player
	public ArrayList<Integer> noOfMatches = new ArrayList<Integer>();
	
	
	public void init(int n) {		// To take noOfPlayers and to initialize playerWon and noOfMacthes
		noOfPlayers = n;
		for(int i=0;i<noOfPlayers;i++) {
			playerWon.add(0);
		}
		for(int i=0;i<noOfPlayers;i++) {
			noOfMatches.add(0);
		}
	}
}