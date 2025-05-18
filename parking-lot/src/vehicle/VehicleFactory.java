package vehicle;

public class VehicleFactory {
    public static Vehicle createVehicle(String licensePlate, VehicleType vehicleType) {
        switch (vehicleType) {
            case BIKE:
                return new Bike(licensePlate, vehicleType);
            case CAR:
                return new Car(licensePlate, vehicleType);
            default:
                throw new IllegalArgumentException("Unsupported vehicle type");
        }
    }
}