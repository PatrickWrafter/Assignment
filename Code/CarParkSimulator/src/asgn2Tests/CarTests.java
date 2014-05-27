package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;

public class CarTests {
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
	 * Test method for {@link asgn2Vehicles.Car#toString()}.
	 */
	@Test
	public void testToString() throws VehicleException{
		Car newCar = new Car("hi", 15, false);
		
		String str = "Vehicle vehID: "+"hi" + "\n" + "Arrival Time: " 
				+ 15 + "\n" + "Vehicle was not Queued" + "\n" +
				"Entry to carpark: " + 15 + "\n" +
				"Exit from carpark: " + 0 + "\n" + 
				"Parking time: " + 0 + "\n" + 
				"Customer was satisfied: " + true + "\n" +
				"Vehicle cannot use small car spaces";
		
		assertEquals(newCar.toString() , str);
		
		
		
		Car newCar2 = new Car("hi", 99, true);
		
		String str2 = "Vehicle vehID: "+"hi" + "\n" + "Arrival Time: " 
				+ 99 + "\n" + "Vehicle was not Queued" + "\n" +
				"Entry to carpark: " + 99 + "\n" +
				"Exit from carpark: " + 0 + "\n" + 
				"Parking time: " + 0 + "\n" + 
				"Customer was satisfied: " + true + "\n" +
				"Vehicle can use small car spaces";
		
		assertEquals(newCar2.toString() , str2);
		
		
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#Car(java.lang.String, int, boolean)}.
	 * @throws VehicleException 
	 */
	@Test
	public void testCar() throws VehicleException {
		Car newCar = new Car("Ridiculous", 5, false);
		assertTrue(newCar.getVehID() == "Ridiculous");
		
		Car newCar2 = new Car("Hi", 10, false);
		assertTrue(!newCar2.isSmall() & (newCar2.getArrivalTime()==10));
		assertTrue(newCar2.getVehID() == "Hi");
		
		
		Car newCar3 = new Car("Yo", 500, false);
		assertTrue(newCar3.getVehID() == "Yo" & (newCar3.getArrivalTime()==500));
	}

		
		
	

	/**
	 * Test method for {@link asgn2Vehicles.Car#isSmall()}.
	 */
	@Test
	public void testIsSmall() throws VehicleException{
		Car newCar = new Car("kieran", 99, true);
		
		assertTrue(newCar.isSmall());
		
		Car newCar2 = new Car("alloy", 109, false);
		
		assertTrue(!newCar2.isSmall());
		
	}
}

