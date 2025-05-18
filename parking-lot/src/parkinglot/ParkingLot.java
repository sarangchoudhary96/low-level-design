package parkinglot;

import vehicle.Vehicle;
import vehicle.VehicleType;

import java.util.List;

public class ParkingLot {
    List<ParkingFloor> parkingFloorList;

    public ParkingLot(List<ParkingFloor> parkingFloorList) {
        this.parkingFloorList = parkingFloorList;
    }

    private ParkingSpot findAvailableSpot(VehicleType vehicleType) {
        for(ParkingFloor parkingFloor: parkingFloorList) {
            ParkingSpot parkingSpot = parkingFloor.findParkingSpot(vehicleType);
            if(parkingSpot != null) {
                return parkingSpot;
            }
        }
        System.out.println("All spots are occupied");
        return null;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {
        ParkingSpot parkingSpot = this.findAvailableSpot(vehicle.getVehicleType());
        if(parkingSpot != null) {
            parkingSpot.parkVehicle(vehicle);
            System.out.println("Vehicle parked successfully");
            return parkingSpot;
        }
        System.out.println("vehicle not found in any spot");
        return null;
    }

    public void vacateSpot(ParkingSpot parkingSpot, Vehicle vehicle) {
        if(parkingSpot != null && parkingSpot.isOccupied() && parkingSpot.getVehicle() == vehicle) {
            parkingSpot.vacate();
            System.out.println("Vehicle vacated from spot number :: " + parkingSpot.getSpotNumber());
        } else {
            System.out.println("Invalid operation! Either the spot is already vacant or the vehicle does not match.");
        }
    }

    public ParkingSpot getSpotByVehicleNumber(int spotNumber) {
        for(ParkingFloor parkingFloor: parkingFloorList) {
            for(ParkingSpot parkingSpot: parkingFloor.getParkingSpotList()) {
                if(parkingSpot.getSpotNumber() == spotNumber) {
                    return parkingSpot;
                }
            }
        }
        return null;
    }
}
