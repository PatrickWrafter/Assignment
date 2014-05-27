package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

public class MotorCycleTests {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link asgn2Vehicles.MotorCycle#MotorCycle(java.lang.String, int)}.
	 */
	@Test
	public void testMotorCycle() throws VehicleException {
		MotorCycle newMC = new MotorCycle("blah", 100);
		assertTrue(newMC.getArrivalTime() == 100);
		assertTrue(newMC.getVehID() == "blah");
		assertNotNull(newMC);
		
		Vehicle newMC2 = new MotorCycle("testing", 500);
		assertTrue(newMC2.getArrivalTime() == 500);
		assertTrue(newMC2.getVehID() == "testing");
		
		assertNotSame(newMC, newMC2);
		
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#Vehicle(java.lang.String, int)}.
	 */
	@Test
	public void testVehicle() throws VehicleException  {
		Vehicle newCar = new Vehicle("testing", 500);
		assertEquals(newCar.getArrivalTime(), 500);
		
	}
	@Test (expected = VehicleException.class)
	public void testVehicle2() throws VehicleException {
		Vehicle newMC = new MotorCycle("D112", -50);
		
		MotorCycle newCar = new MotorCycle("I115", 0);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getVehID()}.
	 */
	@Test
	public void testGetVehID() throws VehicleException {
		Vehicle newVehicle = new Vehicle("testing", 500);
		assertEquals(newVehicle.getVehID() , "testing");
		
		Vehicle newestVehicle = new Vehicle("strawberry", 900);
		assertEquals(newestVehicle.getVehID(), "strawberry");
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getArrivalTime()}.
	 */
	@Test
	public void testGetArrivalTime() throws VehicleException {
		Vehicle newCar = new Vehicle("testing", 500);
		assertEquals(newCar.getArrivalTime(), 500);
		
		Vehicle newBus = new Vehicle("testing", 1);
		assertEquals(newBus.getArrivalTime(), 1);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterQueuedState()}.
	 */
	@Test
	public void testEnterQueuedState() throws VehicleException {
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterQueuedState();
		assertEquals(newCar.isQueued(), true);
		
		Vehicle newCar2 = new MotorCycle("test2", 30);
		newCar2.enterQueuedState();
		assertEquals(newCar.isQueued(), true);
		
		Vehicle newCar3 = new MotorCycle("test2", 30);
		assertEquals(newCar.isQueued(), true);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#exitQueuedState(int)}.
	 */
	@Test
	public void testExitQueuedState() throws VehicleException {
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterQueuedState();
		assertEquals(newCar.isQueued(), true);
		newCar.exitQueuedState(600);
		assertEquals(newCar.isQueued(), false);
		
		
		Vehicle newCar2 = new Car("test2", 30, false);
		newCar2.enterQueuedState();
		assertEquals(newCar2.isQueued(), true);
		newCar2.exitQueuedState(50);
		assertEquals(newCar2.isQueued(), false);
	}
	
	@Test (expected = VehicleException.class)
	public void testExitQueuedState2() throws VehicleException {
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterQueuedState();
		newCar.enterQueuedState();
		assertEquals(newCar.isQueued(), true);
		newCar.exitQueuedState(600);
		assertEquals(newCar.isQueued(), false);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterParkedState(int, int)}.
	 */
	@Test
	public void testEnterParkedState() throws VehicleException{
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterParkedState(500, 600);
		assertEquals(newCar.isParked(), true);			
		
		Vehicle newCar2 = new Car("test2", 50, false);
		newCar2.enterParkedState(50, 600);
		assertEquals(newCar.isParked(), true);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#exitParkedState(int)}.
	 */
	@Test
	public void testExitParkedStateInt() throws VehicleException {
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterParkedState(500, 600);
		assertEquals(newCar.isParked(), true);			
		newCar.exitParkedState(600);
		assertEquals(newCar.isParked(), false);
		
		Vehicle newCar2 = new Car("test2", 50, false);
		newCar2.enterParkedState(50, 100);
		assertEquals(newCar2.isParked(), true);
		newCar2.exitParkedState(100);
		assertEquals(newCar.isParked(), false);
	}


	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isParked()}.
	 */
	@Test
	public void testIsParked() throws VehicleException{
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterParkedState(500, 600);
		assertEquals(newCar.isParked(), true);			
		newCar.exitParkedState(600);
		newCar.enterQueuedState();
	
		newCar.exitQueuedState(600);
		assertEquals(newCar.isParked(), false);
		
		Vehicle newCar2 = new Car("ohio", 500, true);
		assertEquals(newCar2.isParked(), false);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isQueued()}.
	 */
	@Test
	public void testIsQueued() throws VehicleException {
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterQueuedState();
		assertEquals(newCar.isQueued(), true);
		newCar.exitQueuedState(600);
	
		newCar.exitQueuedState(600);
		assertEquals(newCar.isQueued(), false);
		
		
		Vehicle newCar2 = new Car("test2", 30, false);
		newCar2.enterQueuedState();
		assertEquals(newCar2.isQueued(), true);
		newCar2.exitQueuedState(50);
		assertEquals(newCar2.isQueued(), false);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getParkingTime()}.
	 */
	@Test
	public void testGetParkingTime() throws VehicleException{
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterParkedState(500, 300);
		assertEquals(newCar.getParkingTime(),500);
		
		
		Vehicle newCar2 = new Car("test2", 30, false);
		newCar2.enterParkedState(50, 30);
		assertEquals(newCar2.getParkingTime(), 50);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getDepartureTime()}.
	 */
	@Test
	public void testGetDepartureTime() throws VehicleException{
		Vehicle newCar = new Vehicle("testing", 50);
		newCar.getDepartureTime();
		assertEquals(newCar.getDepartureTime(),0);
		newCar.enterParkedState(90, 30);
		assertEquals(newCar.getDepartureTime(), 120);
		newCar.exitParkedState(150);
		assertEquals(newCar.getDepartureTime(), 150);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#wasQueued()}.
	 */
	@Test
	public void testWasQueued() throws VehicleException{
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterQueuedState();
		assertEquals(newCar.isQueued(), true);
		newCar.exitQueuedState(600);
		assertEquals(newCar.isQueued(), false);
		assertEquals(newCar.wasQueued(), true);
		newCar.enterQueuedState();
		assertEquals(newCar.wasQueued(), true);
		
		
		Vehicle newCar2 = new Car("test2", 30, false);
		assertEquals(newCar2.wasQueued(), false);

	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#wasParked()}.
	 */
	@Test
	public void testWasParked() throws VehicleException{
		Vehicle newCar = new Vehicle("testing", 500);
		newCar.enterParkedState(500, 30);
		assertEquals(newCar.isParked(), true);
		newCar.exitParkedState(530);
		assertEquals(newCar.isParked(), false);
		assertEquals(newCar.wasParked(), true);
		newCar.enterParkedState(600,30);
		assertEquals(newCar.wasParked(), true);
		
		
		Vehicle newCar2 = new Car("test2", 30, false);
		assertEquals(newCar2.wasParked(), false);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isSatisfied()}.
	 */
	@Test
	public void testIsSatisfied() throws VehicleException{
		Vehicle newCar1 = new Vehicle("testing", 100);
		assertEquals(newCar1.isSatisfied(), false);
		
		Vehicle newCar2 = new Vehicle("testing", 100);
		newCar2.enterQueuedState();
		assertEquals(newCar2.isSatisfied(), false);
		
		Vehicle newCar3 = new Vehicle("testing", 100);
		newCar3.enterQueuedState();
		newCar3.exitQueuedState(500);
		assertEquals(newCar3.isSatisfied(), false);
		
		
		Vehicle newCar4 = new Vehicle("testing", 100);
		newCar4.enterQueuedState();
		newCar4.exitQueuedState(110);
		newCar4.enterParkedState(110, 30);
		newCar4.exitParkedState(140);
		assertEquals(newCar4.isSatisfied(), true);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#toString()}.
	 */
	@Test
	public void testToString() throws VehicleException {
		Vehicle newCar1 = new Vehicle("testing", 100);
		String str = "Vehicle vehID: "+"testing" + "\n" + "Arrival Time: " 
				+ 100 + "\n" + "Vehicle was not Queued" + "\n" +
				"Entry to carpark: " + 100 + "\n" +
				"Exit from carpark: " + 0 + "\n" + 
				"Parking time: " +0 + "\n" + 
				"Customer was satisfied: " + true + "\n" +
				"Vehicle type: MotorCyle";
		assertEquals(newCar1.toString(), str);
}
}
