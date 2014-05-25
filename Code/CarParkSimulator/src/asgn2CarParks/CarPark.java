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
	private int numSmallCars = 0;
	private int numCars = 0;
	private int numMotorCycles = 0;
	private int numDissatisfied = 0;
	
	private LinkedList<Vehicle> queue = null;
	private LinkedList<Vehicle> past;
	private LinkedList<Vehicle> failures;
	private LinkedList<Vehicle> newVehicles;
	private Object spaces;

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
		if (){
			throw new SimulationException("Vehicle is currently queued or parked");
		}
		newVehicles.add(v);
	}
	
	public void archiveQueueFailures(int time) throws VehicleException {
	}
	
	public boolean carParkEmpty() {
		return false;
	}
	
	public boolean carParkFull() {
		return false;
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
		return 0;
	}
	
	public int getNumMotorCycles() {
		return numMotorCycles;
	}
	
	public int getNumSmallCars() {
		return numSmallCars;
	}
	
	public String getStatus(int time) {
		String str = time +"::"
		+ this.count + "::" 
		+ "P:" + this.spaces.size() + "::"
		+ "C:" + this.numCars + "::S:" + this.numSmallCars 
		+ "::M:" + this.numMotorCycles 
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
		return 0;
	}
	
	public void parkVehicle(Vehicle v, int time, int intendedDuration) throws SimulationException, VehicleException {
	}
	
	public void processQueue(int time, Simulator sim) throws VehicleException, SimulationException {
	}
	
	public boolean queueEmpty() {
		return false;
	}
	
	public boolean queueFull() {
		return false;
	}
	
	public boolean spacesAvailable(Vehicle v) {
		return false;
	}
	
	@Override
	public String toString() {
		return null;
	}
	
	public void tryProcessNewVehicles(int time,Simulator sim) throws VehicleException, SimulationException {
	}
	
	public void unparkVehicle(Vehicle v,int departureTime) throws VehicleException, SimulationException {
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
