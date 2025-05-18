package parkinglot;

import vehicle.Vehicle;
import vehicle.VehicleType;

public abstract class ParkingSpot {
    private final int spotNumber;
    private boolean isOccupied;
    Vehicle vehicle;
    private final VehicleType spotType;

    public ParkingSpot(int spotNumber, VehicleType spotType) {
        this.spotNumber = spotNumber;
        this.isOccupied = false;
        this.spotType = spotType;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public void vacate() {
        if(!this.isOccupied) {
            System.out.println("Vehicle not parked");
        }
        this.vehicle = null;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleType getSpotType() {
        return spotType;
    }
}
