package parkinglot;

import vehicle.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {
    private final ParkingSpot parkingSpot;
    private final Vehicle vehicle;
    private final LocalDateTime startTime;

    public Ticket(ParkingSpot parkingSpot, Vehicle vehicle) {
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.startTime =  LocalDateTime.of(2025, 5, 17, 10, 30); // LocalDateTime.now();
    }

    public int calculateHours() {
        LocalDateTime currentTime = LocalDateTime.now();
        return (int) Duration.between(this.startTime, currentTime).toHours();
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}