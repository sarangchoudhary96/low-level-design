# Table of Contents

- [Parking Lot](#parking-lot)

# Parking Lot
```java
import java.util.*;

enum VehicleType {
    CAR, BIKE
}

class Ticket {
    private final String ticketId;
    private final Date entryTime;
    private Date exitTime;

    public Ticket(String ticketId) {
        this.ticketId = ticketId;
        this.entryTime = new Date();
    }

    public String getTicketId() {
        return ticketId;
    }

    public void markExitTime() {
        this.exitTime = new Date();
    }

    public double calculateParkingTime(double ratePerHour) {
        long durationMillis = exitTime.getTime() - entryTime.getTime();
        return (durationMillis / 1000.0 / 60 / 60) * ratePerHour;
    }
}

abstract class Vehicle {
    private final Ticket ticket;
    private final String vehicleNumber;
    private final double ratePerHour;

    public Vehicle(String vehicleNumber, Ticket ticket, double ratePerHour) {
        this.vehicleNumber = vehicleNumber;
        this.ticket = ticket;
        this.ratePerHour = ratePerHour;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public double calculateParkingCost() {
        return ticket.calculateParkingTime(ratePerHour);
    }
}

class Car extends Vehicle {
    public Car(String vehicleNumber, Ticket ticket) {
        super(vehicleNumber, ticket, 50);
    }
}

class Bike extends Vehicle {
    public Bike(String vehicleNumber, Ticket ticket) {
        super(vehicleNumber, ticket, 30);
    }
}

interface ParkingSpot {
    Vehicle getVehicle();
    String getSpotId();
}

class CarParkingSpot implements ParkingSpot {
    private final String spotId;
    private final Vehicle vehicle;

    public CarParkingSpot(String spotId, Vehicle vehicle) {
        this.spotId = spotId;
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getSpotId() {
        return spotId;
    }
}

class BikeParkingSpot implements ParkingSpot {
    private final String spotId;
    private final Vehicle vehicle;

    public BikeParkingSpot(String spotId, Vehicle vehicle) {
        this.spotId = spotId;
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getSpotId() {
        return spotId;
    }
}

interface ParkingLot {
    boolean parkVehicle(ParkingSpot parkingSpot);
    boolean exitVehicle(String vehicleNumber);
    ParkingSpot getParkedSpotByVehicleNumber(String vehicleNumber);
}

class MallParkingLot implements ParkingLot {
    private int availableParkingSlots;
    private final Map<String, ParkingSpot> occupiedParkingSlots = new HashMap<>();
    private final Map<String, ParkingSpot> vehicleToParkingSpotMap = new HashMap<>();

    public MallParkingLot(int totalSlots) {
        this.availableParkingSlots = totalSlots;
    }

    public boolean parkVehicle(ParkingSpot parkingSpot) {
        if (!occupiedParkingSlots.containsKey(parkingSpot.getSpotId()) &&
            !vehicleToParkingSpotMap.containsKey(parkingSpot.getVehicle().getVehicleNumber())) {
            occupiedParkingSlots.put(parkingSpot.getSpotId(), parkingSpot);
            vehicleToParkingSpotMap.put(parkingSpot.getVehicle().getVehicleNumber(), parkingSpot);
            availableParkingSlots--;
            return true;
        }
        return false;
    }

    public boolean exitVehicle(String vehicleNumber) {
        ParkingSpot parkingSpot = vehicleToParkingSpotMap.get(vehicleNumber);
        if (parkingSpot == null) return false;

        occupiedParkingSlots.remove(parkingSpot.getSpotId());
        vehicleToParkingSpotMap.remove(vehicleNumber);
        parkingSpot.getVehicle().getTicket().markExitTime();
        availableParkingSlots++;
        return true;
    }

    public ParkingSpot getParkedSpotByVehicleNumber(String vehicleNumber) {
        return vehicleToParkingSpotMap.getOrDefault(vehicleNumber, null);
    }
}

class ParkingLotStrategy {
    private final ParkingLot parkingLot;

    public ParkingLotStrategy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public boolean parkVehicle(VehicleType vehicleType, String vehicleNumber) {
        Ticket ticket = new Ticket(String.valueOf(System.currentTimeMillis()));
        Vehicle vehicle;
        ParkingSpot parkingSpot;

        if (vehicleType == VehicleType.CAR) {
            vehicle = new Car(vehicleNumber, ticket);
            parkingSpot = new CarParkingSpot(getAvailableSpotId(vehicleType), vehicle);
        } else if (vehicleType == VehicleType.BIKE) {
            vehicle = new Bike(vehicleNumber, ticket);
            parkingSpot = new BikeParkingSpot(getAvailableSpotId(vehicleType), vehicle);
        } else {
            throw new IllegalArgumentException("Invalid vehicle type");
        }

        return parkingLot.parkVehicle(parkingSpot);
    }

    private String getAvailableSpotId(VehicleType vehicleType) {
        return vehicleType == VehicleType.CAR ? "CarSpot-" + System.currentTimeMillis() : "BikeSpot-" + System.currentTimeMillis();
    }

    public boolean exitVehicle(String vehicleNumber) {
        return parkingLot.exitVehicle(vehicleNumber);
    }

    public double calculateParkingRate(String vehicleNumber) {
        ParkingSpot parkingSpot = parkingLot.getParkedSpotByVehicleNumber(vehicleNumber);
        if (parkingSpot == null) {
            throw new IllegalArgumentException("Vehicle not parked");
        }
        return parkingSpot.getVehicle().calculateParkingCost();
    }
}

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = new MallParkingLot(10);
        ParkingLotStrategy parkingLotStrategy = new ParkingLotStrategy(parkingLot);

        System.out.println(parkingLotStrategy.parkVehicle(VehicleType.CAR, "KA 01 1234"));
        System.out.println(parkingLotStrategy.parkVehicle(VehicleType.BIKE, "KA 01 1235"));

        System.out.println(parkingLotStrategy.exitVehicle("KA 01 1234"));
        System.out.println(parkingLotStrategy.calculateParkingRate("KA 01 1234"));

        System.out.println(parkingLotStrategy.exitVehicle("KA 01 1235"));
        System.out.println(parkingLotStrategy.calculateParkingRate("KA 01 1235"));
    }
}
```
