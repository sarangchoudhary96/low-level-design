package parkinglot;

import vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParkingFloor {
    private final List<ParkingSpot> parkingSpotList;
    private final int floorNumber;

    public ParkingFloor(int floorNumber, int noOfCarSpots, int noOfBikeSpots) {
        this.floorNumber = floorNumber;

        this.parkingSpotList = new ArrayList<>();

        for(int i = 1; i <= noOfBikeSpots; i++) {
            this.parkingSpotList.add(new BikeParkingSpot(i, VehicleType.BIKE));
        }

        for(int i = noOfBikeSpots + 1; i <= noOfBikeSpots + noOfCarSpots; i++) {
            this.parkingSpotList.add(new CarParkingSpot(i, VehicleType.CAR));
        }
    }

    public ParkingSpot findParkingSpot(VehicleType vehicleType) {
        for(ParkingSpot parkingSpot: parkingSpotList) {
            if(!parkingSpot.isOccupied() && Objects.equals(parkingSpot.getSpotType(), vehicleType)) {
                return parkingSpot;
            }
        }
        System.out.println("All Spots are occupied");
        return null;
    }

    public List<ParkingSpot> getParkingSpotList() {
        return parkingSpotList;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
