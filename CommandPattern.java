import java.util.ArrayList;

interface Command {					// command interface
	
		public void execute();		// we need only one method, execute 

}


// Methods is a receiver, which is going to implement the specific commands
class Methods {
	
	    private Data data;	// shared data
	    
	    public Methods(Data data) {
	    	this.data = data;
	    }
	    
	    // method to generate random numbers
		public void generateRandomNumber() {	
			int rng = (int)( Math.random() * 50 ); 
			data.generate = rng;
		}
		
		// method to check if any of the players won
		public void checkWon() {
			int x = 0;
			for(int i=0; i<data.noOfPlayers; i++) {
				x = data.playerWon.get(i) + x;
			}
			if(x > 0)	data.won = 1;
			else data.won = 0;
		}
}


//	Concrete command classes 
class generate implements Command {			// Implementing command to generate random numbers
	   
		private Methods m;

		public generate(Methods m){			// Constructor is passed some (methods) object
			this.m = m;						// which this command is going to control
		}

		public void execute() {				// this execute calls the generateRandomNumber method
			m.generateRandomNumber();		// on the receiving object (methods)
		}
}

class didWin implements Command {			// Implementing command to check if won
	   	
		private Methods m;
		
	   	public didWin(Methods m){
	   		this.m = m;
	   	}

	   	public void execute() {				// this execute calls the checWon method on the
	   		m.checkWon();					// receiving object (methods)
	   	}
}

class Invoker {			// This is invoker, it will be given command object
						// to carry out its operations
		
		// to take commands object which we can implement later at once
	   	private ArrayList<Command> commandList = new ArrayList<Command>(); 	
	   																	
	   	// this methods takes the command as argument, and stores it in commandList
	   	public void takeCommand(Command command){
	   		commandList.add(command);		
	   	}

	   	// this method executes all the stored commands in commandList, all at once
	   	public void placeCommands(){
	   
	   		for (Command command : commandList) {
	   			command.execute();
	   		}
	   		commandList.clear();
	   	}
	   	
	   	// This methods is for situations when, a command is needed to be executed at that instance
	   	// i.e. without storing it and implementing late
	   	public void executeCommand(Command command) {
	   		command.execute();
	   	}
}
