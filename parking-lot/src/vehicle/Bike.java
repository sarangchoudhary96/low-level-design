package vehicle;

public class Bike extends Vehicle{
    private final static double RATE_PER_HOUR = 10.0;

    public Bike(String licensePlate, VehicleType vehicleType) {
        super(licensePlate, vehicleType);
    }

    @Override
    public double calculateFee(int hoursStayed) {
        return (double) hoursStayed * RATE_PER_HOUR;
    }
}
