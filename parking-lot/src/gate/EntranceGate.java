package gate;

import parkinglot.ParkingLot;
import parkinglot.ParkingSpot;
import parkinglot.Ticket;
import vehicle.Vehicle;
import vehicle.VehicleFactory;
import vehicle.VehicleType;

public class EntranceGate {
    ParkingLot parkingLot;

    public EntranceGate(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Ticket processEntrance(String licensePlate, VehicleType vehicleType) {
        Vehicle vehicle = VehicleFactory.createVehicle(licensePlate, vehicleType);
        ParkingSpot parkingSpot = this.parkingLot.parkVehicle(vehicle);

        if(parkingSpot != null) {
            System.out.println("Successfully parked at spot number :: " + parkingSpot.getSpotNumber());
            return new Ticket(parkingSpot, vehicle);
        } else {
            System.out.println("No available spots for this vehicle type");
        }
        return null;
    }

}
