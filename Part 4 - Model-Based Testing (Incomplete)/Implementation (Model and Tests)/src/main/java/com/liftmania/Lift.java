package com.liftmania;

public class Lift {

	int id;
	boolean moving=false;	
	int floor = 0;
	boolean doorsOpen = false;
	boolean betweenFloors = false;
	boolean doorsClosing = false;
	
	public int getId() {
		return id;
	}


	public boolean isMoving() {
		return moving;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public Lift(int id) {
		this.id = id;
	}
	
	public void setFloor(int floor) {
		this.floor = floor;
		this.betweenFloors = false;
	}

	public int getFloor() {
		return floor;
	}

	public boolean isOpen() {
		return doorsOpen;
	}

	public void closeDoors() {
		doorsOpen = false;
		doorsClosing = false;
	}

	public void openDoors() {
		doorsOpen = true;
	}

	public void setAsBetweenFloors() {
		betweenFloors = true;
	}

	public boolean isBetweenFloors() {
		return betweenFloors;
	}

	/**
	 * Calculates the distance of a lift from a particular floor
	 * 
	 * @param floor
	 *            - The floor number to measure distance to.
	 * @return
	 */

	public int distanceFromFloor(int floor) {
		return Math.abs(this.floor - floor);
	}

	public void startClosingDoors() {
		doorsClosing = true;
	}
}
