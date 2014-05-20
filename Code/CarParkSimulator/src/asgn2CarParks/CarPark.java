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

	public CarPark(){
		this(Constants.DEFAULT_MAX_CAR_SPACES,Constants.DEFAULT_MAX_SMALL_CAR_SPACES,
				Constants.DEFAULT_MAX_MOTORCYCLE_SPACES,Constants.DEFAULT_MAX_QUEUE_SIZE);
		
	}
	
	public CarPark(int maxCarSpaces,int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize) {
	}
	
	
	public void archiveDepartingVehicles(int time,boolean force) throws VehicleException, SimulationException {
	}
	
	public void archiveNewVehicle(Vehicle v) throws SimulationException {
	}
	
	public void archiveQueueFailures(int time) throws VehicleException {
	}
	
	public boolean carParkEmpty() {
	}
	
	public boolean carParkFull() {
	}
	
	public void enterQueue(Vehicle v) throws SimulationException, VehicleException {
	}
	
	public void exitQueue(Vehicle v,int exitTime) throws SimulationException, VehicleException {
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
	}
	
	public int getNumSmallCars() {
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
	}
	
	public void parkVehicle(Vehicle v, int time, int intendedDuration) throws SimulationException, VehicleException {
	}
	
	public void processQueue(int time, Simulator sim) throws VehicleException, SimulationException {
	}
	
	public boolean queueEmpty() {
	}
	
	public boolean queueFull() {
	}
	
	public boolean spacesAvailable(Vehicle v) {
	}
	
	@Override
	public String toString() {
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
