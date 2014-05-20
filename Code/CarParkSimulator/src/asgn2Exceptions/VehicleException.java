package asgn2Exceptions;


@SuppressWarnings("Serial")
public class VehicleException extends Exception {

	
	public VehicleException(String message) {
		super("Simulation Exception: " + message);
	}
}
