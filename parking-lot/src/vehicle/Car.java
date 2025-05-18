package vehicle;

public class Car extends Vehicle {
    private final static double RATE_PER_HOUR = 20.0;

    public Car(String licensePlate, VehicleType vehicleType) {
        super(licensePlate, vehicleType);
    }

    @Override
    public double calculateFee(int hoursStayed) {
        return (double) hoursStayed * RATE_PER_HOUR;
    }
}
