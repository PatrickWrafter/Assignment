package asgn2CarParks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;


public class CarPark {

	private int maxQueueSize = 0;
	private int maxMotorCycleSpaces = 0;
	private int maxSmallCarSpaces = 0;
	private int maxCarSpaces = 0;
	private int count =0;
	private int countCars=0;
	private int countMotorCycles = 0;
	private int countSmallCars = 0;
	private int numSmallCars = 0;
	private int numCars = 0;
	private int numMotorCycles = 0;
	private int numDissatisfied = 0;
	
	
	
	private LinkedList<Vehicle> queue;
	private LinkedList<Vehicle> past;
	private LinkedList<Vehicle> failures;
	private LinkedList<Vehicle> newVehicles;
	private LinkedList<Vehicle> carPark;
	private String status;

	public CarPark(){
		this(Constants.DEFAULT_MAX_CAR_SPACES,Constants.DEFAULT_MAX_SMALL_CAR_SPACES,
				Constants.DEFAULT_MAX_MOTORCYCLE_SPACES,Constants.DEFAULT_MAX_QUEUE_SIZE);
		
	}
	
	public CarPark(int maxCarSpaces,int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize) {
		this.maxCarSpaces = maxCarSpaces;
		this.maxSmallCarSpaces = maxSmallCarSpaces;
		this.maxMotorCycleSpaces = maxMotorCycleSpaces;
		this.maxQueueSize = maxQueueSize;
		
	}
	
	
	public void archiveDepartingVehicles(int time,boolean force) throws VehicleException, SimulationException {
		
	}
	
	public void archiveNewVehicle(Vehicle v) throws SimulationException {
		if (v.isParked()||v.isQueued()){
			throw new SimulationException("Vehicle is currently queued or parked");
		}
		newVehicles.add(v);
	}
	
	public void archiveQueueFailures(int time) throws VehicleException {
		Iterator<Vehicle> it = queue.iterator();  
		while (it.hasNext()){
			Vehicle nextCar = it.next();
			if (time - nextCar.getArrivalTime() > Constants.MAXIMUM_QUEUE_TIME){
				newVehicles.add(nextCar);
				numDissatisfied++;
			}
		}
	}
	
	public boolean carParkEmpty() {
		return carPark.isEmpty();
	}
	
	public boolean carParkFull() {
		if (carPark.size() >= (maxCarSpaces+maxMotorCycleSpaces+maxSmallCarSpaces)){
			return true;
		}
		else return false;
	}
	
	public void enterQueue(Vehicle v) throws SimulationException, VehicleException {
		if (queue.size() > maxQueueSize){
			throw new SimulationException("Queue is full");
		}
		if (v.isParked() || v.isQueued()){
			throw new VehicleException("Vehicle is already parked or in queue");
		}
		
		queue.add(v);
		v.enterQueuedState();
	}
	
	public void exitQueue(Vehicle v,int exitTime) throws SimulationException, VehicleException {
		if (!v.isQueued()){
			throw new VehicleException("Vehicle is not queued");
		}
		if (!queue.contains(v)){
			throw new SimulationException("Vehicle is not in queue");
		}
		
		queue.remove(v);
		v.exitQueuedState(exitTime);
	}
	
	public String finalState() {
		String str = "Vehicles Processed: count:" + 
				this.count + ", logged: " + this.past.size() 
				+ "\nVehicle Record: \n";
		for (Vehicle v : this.past) {
			str += v.toString() + "\n\n";
		}
		return str + "\n";
	}
	
	public int getNumCars() {
		return countCars;
	}
	
	public int getNumMotorCycles() {
		return countMotorCycles;
	}
	
	public int getNumSmallCars() {
		return countSmallCars;
	}
	
	public String getStatus(int time) {
		String str = time +"::"
		+ this.count + "::" 
		+ "P:" + this.carPark.size() + "::"
		+ "C:" + this.countCars + "::S:" + this.countSmallCars 
		+ "::M:" + this.countMotorCycles 
		+ "::D:" + this.numDissatisfied 
		+ "::A:" + this.past.size()  
		+ "::Q:" + this.queue.size(); 
		for (Vehicle v : this.queue) {
			if (v instanceof Car) {
				if (((Car)v).isSmall()) {
					str += "S";
				} else {
					str += "C";
				}
			} else {
				str += "M";
			}
		}
		str += this.status;
		this.status="";
		return str+"\n";
	}
	
	public String initialState() {
		return "CarPark [maxCarSpaces: " + this.maxCarSpaces
				+ " maxSmallCarSpaces: " + this.maxSmallCarSpaces 
				+ " maxMotorCycleSpaces: " + this.maxMotorCycleSpaces 
				+ " maxQueueSize: " + this.maxQueueSize + "]";
	}
	
	
	public int numVehiclesInQueue() {
		return queue.size();
	}
	
	public void parkVehicle(Vehicle v, int time, int intendedDuration) throws SimulationException, VehicleException {
		// Exception handling
		if (v.isParked() || v.isQueued()){
			throw new VehicleException("Car is already parked");
		}
		 
		
		
		// Add Car to Park
		if (v.getClass() == Car.class){
			Car car = Car.class.cast(v);
			if (car.isSmall()){
				if (numSmallCars < maxSmallCarSpaces){
					numSmallCars++;
					countSmallCars++;
					count++;
					carPark.add(v);
					v.enterParkedState(time, intendedDuration);
				}
				else if (numCars < maxCarSpaces){
					numCars++;
					countSmallCars++;
					count++;
					carPark.add(v);
					v.enterParkedState(time, intendedDuration);
				}
				else throw new SimulationException("No Available spots for new car");
				
				
				
			}
		
			else{
				if (numCars >= maxCarSpaces){
					throw new SimulationException("No Available spots for new car");
				}
				numCars++;
				countCars++;
				count++;
				carPark.add(v);
				v.enterParkedState(time, intendedDuration);
			}
		}
		// Add MotorCycle to Park
		else {
			if (numMotorCycles < maxMotorCycleSpaces){
				numMotorCycles++;
				countMotorCycles++;
				count++;
				carPark.add(v);
				v.enterParkedState(time, intendedDuration);
			}
			else if (numSmallCars < maxSmallCarSpaces){
				countMotorCycles++;
				numSmallCars++;
				count++;
				carPark.add(v);
				v.enterParkedState(time, intendedDuration);
			}
			else {throw new SimulationException("No Available Spots for new car");}
		}	
	}
		
	
	public void processQueue(int time, Simulator sim) throws VehicleException, SimulationException {
		boolean active = true;
		
		while (active){
			Vehicle nextCar = queue.peek();
			
			// Exceptions
			if (nextCar.isParked()){
				throw new SimulationException("Vehicle is already parked");
			}
			if ((time - nextCar.getArrivalTime()) > Constants.MAXIMUM_QUEUE_TIME){
				throw new SimulationException("Vehicle has been in queue too long");
			}
			
			// Check to see if next car can be parked
			if (spacesAvailable(nextCar)){
				nextCar = queue.pop();
				nextCar.exitQueuedState(time);
				parkVehicle(nextCar, time, sim.setDuration());
			}
			// If car cannot be parked, queue stops processing
			else active = false;
			
		}
		
	}
		
	
	
	public boolean queueEmpty() {
		return (queue.size() == maxQueueSize);
	}
	
	public boolean queueFull() {
		return (queue.size() == 0);
	}
	
	public boolean spacesAvailable(Vehicle v) {
		if (v.getClass() == Car.class){
			Car car = Car.class.cast(v);
			if (car.isSmall()){
				return (numSmallCars < maxSmallCarSpaces || numCars < maxCarSpaces);
			}
			else
				return (numCars < maxCarSpaces);
		}
		else if (v.getClass() == MotorCycle.class){
			return (numMotorCycles < maxMotorCycleSpaces || numSmallCars < maxSmallCarSpaces);
		}
		
		else
			return false;
	}
	
	@Override
	public String toString() {
		return "CarPark [count: "+ count + " numCars: "
				+countCars+" numSmallCars: "+countSmallCars +
				" numMotorCycles: "	+ countMotorCycles	+
				" queue: " 	+ (queue.size()) + " numDissatisfied: "
				+numDissatisfied + " past: " + past.size() +"]";
				
	}
	
	public void tryProcessNewVehicles(int time,Simulator sim) throws VehicleException, SimulationException {
		
		if (sim.newCarTrial()){
			String vehID = "C" + this.count;
			Car newVehicle = new Car(vehID, time, false);
			archiveNewVehicle(newVehicle);
		}		
		
		if (sim.smallCarTrial()){
			String vehID = "C" + this.count;
			Car newVehicle = new Car(vehID, time, true);
			archiveNewVehicle(newVehicle);
		}
		
		if (sim.motorCycleTrial()){
			String vehID = "V" + this.count;
			MotorCycle newVehicle = new MotorCycle(vehID, time);
			archiveNewVehicle(newVehicle);	
		}
		
		
	}
	
	public void unparkVehicle(Vehicle v,int departureTime) throws VehicleException, SimulationException {
		if (v.isQueued() || !v.isParked()){
			throw new VehicleException("Vehicle isn't parked.");
		}
		if (!carPark.contains(v)){
			throw new SimulationException("Vehicle isn't in car park");
		}
		v.exitParkedState(departureTime);
		past.add(v);
		carPark.remove(v);
		if (v instanceof Car){
			if (((Car)v).isSmall()){
				numSmallCars--;
				countSmallCars--;
			}
			else{
				numCars--;
				countCars--;
			}	
		}
		else{
			numMotorCycles--;
			countMotorCycles--;
		}
	}
	
	private String setVehicleMsg(Vehicle v,String source, String target) {
		String str="";
		if (v instanceof Car) {
			if (((Car)v).isSmall()) {
				str+="S";
			} else {
				str+="C";
			}
		} else {
			str += "M";
		}
		return "|"+str+":"+source+">"+target+"|";
	}
	
	
	
	
}
