package asgn2Vehicles;

import asgn2Exceptions.VehicleException;

public class Car extends Vehicle {
	
	private boolean small; 

	/**
	 * The Car Constructor - small set at creation, not mutable. 
	 * @param vehID - identification number or plate of the vehicle
	 * @param arrivalTime - time (minutes) at which the vehicle arrives and is 
	 *        either queued or given entry to the carpark 
	 * @param small - indicator whether car is regarded as small or not
	 * @throws VehicleException if arrivalTime is <= 0  
	 */
	public Car(String vehID, int arrivalTime, boolean small) throws VehicleException {
		super(vehID, arrivalTime);
		this.small = small; 
		
		
		if (arrivalTime < 0){
			throw new VehicleException("Arrival time 0 or earlier.");
		}
	}

	/**
	 * Boolean status indicating whether car is small enough for small 
	 * car parking spaces  
	 * @return true if small parking space, false otherwise
	 */
	public boolean isSmall() {
		return small;
	}

	/* (non-Javadoc)
	 * @see asgn2Vehicles.Vehicle#toString()
	 */
	@Override
	public String toString() {
		String carMessage;
		String queueMessage;
		if (wasQueued()){
			queueMessage = "Vehicle was Queued";
		}
		else {queueMessage = "Vehicle was not Queued";}
		
		if (isSmall()){
			carMessage = "Vehicle can use small car spaces";
		}
		else {carMessage = "Vehicle cannot use small car spaces";}
		
		return "Vehicle vehID: "+vehID + "\n" + "Arrival Time: " 
				+ arrivalTime + "\n" + queueMessage + "\n" +
				"Entry to carpark: " + arrivalTime + "\n" +
				"Exit from carpark: " + exitTime + "\n" + 
				"Parking time: " + parkingTime + "\n" + 
				"Customer was satisfied: " + satisfied + "\n" +
				carMessage;
	}
}
