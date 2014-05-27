package asgn2Vehicles;


import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;

public class Vehicle {
	
	// Variables
	protected String vehID;
	protected int arrivalTime;
	private boolean parked = false;
	private boolean wasParked = false;
	private boolean wasQueued = false;
	private boolean inQueue = false;
	protected boolean satisfied = true; 
	private int intendedDuration = 0;
	protected int parkingTime = 0;
	private int departureTime = 0;
	protected int exitTime;
	
	
	/**
	 * Vehicle Constructor 
	 * @param vehID String identification number or plate of the vehicle
	 * @param arrivalTime int time (minutes) at which the vehicle arrives and is 
	 *        either queued, given entry to the car park or forced to leave
	 * @throws VehicleException if arrivalTime is <= 0 
	 */
	public Vehicle(String vehID,int arrivalTime) throws VehicleException  {
		this.vehID = vehID;
		
		if (arrivalTime <0){
			throw new VehicleException("Invalid arrival time.");
		}
		
		this.arrivalTime = arrivalTime;
		
		
	}

	/**
	 * Transition vehicle to parked state (mutator)
	 * Parking starts on arrival or on exit from the queue, but time is set here
	 * @param parkingTime int time (minutes) at which the vehicle was able to park
	 * @param intendedDuration int time (minutes) for which the vehicle is intended to remain in the car park.
	 *  	  Note that the parkingTime + intendedDuration yields the departureTime
	 * @throws VehicleException if the vehicle is already in a parked or queued state, if parkingTime <= 0, 
	 *         or if intendedDuration is less than the minimum prescribed in asgnSimulators.Constants
	 */
	public void enterParkedState(int parkingTime, int intendedDuration) throws VehicleException {
		if (parked || inQueue){
			throw new VehicleException("Already Parked");
		}
		if (parkingTime<=0){
			throw new VehicleException("Invalid parking time");
		}
		if (intendedDuration < Constants.MINIMUM_STAY){
			throw new VehicleException("Intended duration too short");
		}
		
		
		parked = true; 
		wasParked = true;
		this.intendedDuration = intendedDuration;
		this.parkingTime = parkingTime;
	}
	
	/**
	 * Transition vehicle to queued state (mutator) 
	 * Queuing formally starts on arrival and ceases with a call to {@link #exitQueuedState(int) exitQueuedState}
	 * @throws VehicleException if the vehicle is already in a queued or parked state
	 */
	public void enterQueuedState() throws VehicleException {
		if (inQueue){
			throw new VehicleException("Already in queue");
		}
		if (parked){
			throw new VehicleException("Already parked");
		}
		inQueue = true;
		wasQueued = true;
		
	}
	
	/**
	 * Transition vehicle from parked state (mutator) 
	 * @param departureTime int holding the actual departure time 
	 * @throws VehicleException if the vehicle is not in a parked state, is in a queued 
	 * 		  state or if the revised departureTime < parkingTime
	 */
	public void exitParkedState(int departureTime) throws VehicleException {
		if (!parked){
			throw new VehicleException("Vehicle isn't currently parked");
		}
		if (inQueue){
			throw new VehicleException("Vehicle is already in Queue");
		}
		if (departureTime < parkingTime){
			throw new VehicleException("Departure time occurs before arrival");
		}
		
		parked = false; 
		this.departureTime = departureTime;
	}

	/**
	 * Transition vehicle from queued state (mutator) 
	 * Queuing formally starts on arrival with a call to {@link #enterQueuedState() enterQueuedState}
	 * Here we exit and set the time at which the vehicle left the queue
	 * @param exitTime int holding the time at which the vehicle left the queue 
	 * @throws VehicleException if the vehicle is in a parked state or not in a queued state, or if 
	 *  exitTime is not later than arrivalTime for this vehicle
	 */
	public void exitQueuedState(int exitTime) throws VehicleException {
		if (parked){
			throw new VehicleException("Car is already parked");
		}
		if (exitTime < arrivalTime){
			throw new VehicleException("Exit time occurs before arrival");
		}
		inQueue = false;
		this.exitTime = exitTime;
		
	}
	
	/**
	 * Simple getter for the arrival time 
	 * @return the arrivalTime
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Simple getter for the departure time from the car park
	 * Note: result may be 0 before parking, show intended departure 
	 * time while parked; and actual when archived
	 * @return the departureTime
	 */
	public int getDepartureTime() {
		if (parked){
			return intendedDuration+parkingTime;
		}
		else if (wasParked()){
			return departureTime;
		}
		else return 0;
	}
	
	/**
	 * Simple getter for the parking time
	 * Note: result may be 0 before parking
	 * @return the parkingTime
	 */
	public int getParkingTime() {
		return parkingTime;
	}

	/**
	 * Simple getter for the vehicle ID
	 * @return the vehID
	 */
	public String getVehID() {
		return vehID;
	}

	/**
	 * Boolean status indicating whether vehicle is currently parked 
	 * @return true if the vehicle is in a parked state; false otherwise
	 */
	public boolean isParked() {
		return this.parked;
	}

	/**
	 * Boolean status indicating whether vehicle is currently queued
	 * @return true if vehicle is in a queued state, false otherwise 
	 */
	public boolean isQueued() {
		return inQueue;
	}
	
	/**
	 * Boolean status indicating whether customer is satisfied or not
	 * Satisfied if they park; dissatisfied if turned away, or queuing for too long 
     * Vehicles begin in a satisfied state, but this may change over time
	 * Note that calls to this method may not reflect final status 
	 * @return true if satisfied, false if never in parked state or if queuing time exceeds max allowable 
	 */
	public boolean isSatisfied() {
		
		
		if (!wasParked()&wasQueued()){
			satisfied = false;
		}
		else if (((parkingTime-arrivalTime) > Constants.MAXIMUM_QUEUE_TIME)){
			satisfied = false; 
		}
		else if (!wasQueued()){
			satisfied = false;
		}
		return satisfied;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String queueMessage;
		if (wasQueued){
			queueMessage = "Vehicle was Queued";
		}
		else {queueMessage = "Vehicle was not Queued";}
		
		return "Vehicle vehID: "+vehID + "\n" + "Arrival Time: " 
				+ arrivalTime + "\n" + queueMessage + "\n" +
				"Entry to carpark: " + arrivalTime + "\n" +
				"Exit from carpark: " + exitTime + "\n" + 
				"Parking time: " + parkingTime + "\n" + 
				"Customer was satisfied: " + satisfied + "\n" +
				"Vehicle type: MotorCyle";
				
	}

	/**
	 * Boolean status indicating whether vehicle was ever parked
	 * Will return false for vehicles in queue or turned away 
	 * @return true if vehicle was or is in a parked state, false otherwise 
	 */
	public boolean wasParked() {
		return wasParked;
	}

	/**
	 * Boolean status indicating whether vehicle was ever queued
	 * @return true if vehicle was or is in a queued state, false otherwise 
	 */
	public boolean wasQueued() {
		return wasQueued;
	}
	
}
