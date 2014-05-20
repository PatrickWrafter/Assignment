package asgn2Exceptions;


@SuppressWarnings("serial")
public class SimulationException extends Exception {

	public SimulationException(String message){
		super("Simulation Exception: " + message);
	}
	
}
