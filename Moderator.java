
class Moderator implements Runnable{

	static Moderator uniqueObject;
	private Moderator(){}
	
	// using getInstance method to create new object if uniqueOnject pointer in null
	// and if it is not null, then returns the already created object
	// using synchronized keyword so that only one object can access it
	// at particular time, to protect from accidently generating two objects
	public static synchronized Moderator getInstance() { 
		if(uniqueObject == null) {
			uniqueObject = new Moderator();
		}
		return uniqueObject;
	}
	
	private Data data; // shared data
	private int noOfPlayers;
	// These objects to run methods
	private Invoker invoke;
	private generate g;
	private didWin win;
	
	public void setter(Data data, int n, Invoker i, generate g, didWin w) {		
		this.data = data;		// set the shared data object, to class data object
		noOfPlayers = n;
		this.g = g;
		invoke = i;
		win = w;
	}
	
	
	public void run() {
		synchronized(data.lock) {	
			for(int i=0;i<10;i++) {
				
				// executing the execute of didWin concrete command class
				invoke.executeCommand(win);
				// if statement is checking whether any of players has already one
				// if yes, then there is no need to generate or check again
				if(data.won == 0) {
					
					// here using data number as a counter, if it is multiple of noOfplayers
					// hence all the players has checked the generated number
					// if not, it will keep on waiting
					// Also to protect it from executing if number is already generated 
					while (Data.number % noOfPlayers != 0 || Data.noGenerated) {
						// Closing in try catch block as it might throw exception here
						try {
							data.lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace(); 	 	 	
						}
					}
					
					// executing the execute of didWin concrete command class
					invoke.executeCommand(win);
					// checking to stop any wait after already won
					if(data.won == 0) {
						
						// sleep for realistic feels
						// Closing in try catch block as it might throw exception here
						try { Thread.sleep(10); } catch(Exception e) {}
						System.out.println("Iteration #" + (i+1));
						
						// executing the execute of generate concrete command class
						invoke.executeCommand(g);	// generating number
						data.generatedNumbers.add(data.generate);	// appending the generatedNumbers array
						System.out.println("Moderator announced: " + data.generate); 
						data.lock.notifyAll(); 			// After running the loop once, notifying 
														// all the threads waiting for lock
						Data.noGenerated = true;		// Setting the number generated flag
					}
				} else break;
			}
		}
	}
}