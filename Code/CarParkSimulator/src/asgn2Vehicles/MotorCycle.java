package asgn2Vehicles;


import asgn2Exceptions.VehicleException;

public class MotorCycle extends Vehicle {
	

	/**
	 * MotorCycle constructor 
	 * @param vehID - identification number or plate of the vehicle
	 * @param arrivalTime - time (minutes) at which the vehicle arrives and is 
	 *        either queued or given entry to the carpark 
	 * @throws VehicleException if arrivalTime is <= 0  
	 */
	public MotorCycle(String vehID, int arrivalTime) throws VehicleException {
		super(vehID, arrivalTime);
		if (arrivalTime <= 0){
			throw new VehicleException("Arrival time is 0 or earlier");
		}
		
	}
}
