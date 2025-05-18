package gate;

import parkinglot.ParkingLot;
import parkinglot.ParkingSpot;
import parkinglot.Ticket;
import payment.PaymentMethod;
import payment.PaymentService;
import vehicle.Vehicle;

public class ExitGate {
    ParkingLot parkingLot;
    PaymentService paymentService;
    Ticket ticket;

    public ExitGate(ParkingLot parkingLot, PaymentService paymentService, Ticket ticket) {
        this.parkingLot = parkingLot;
        this.paymentService = paymentService;
        this.ticket = ticket;
    }

    public void processExit(PaymentMethod paymentMethod) {
        ParkingSpot parkingSpot = this.ticket.getParkingSpot();

        if(parkingSpot == null || !parkingSpot.isOccupied()) {
            System.out.println("Invalid or vacant spot!");
            return;
        }

        int hoursStayed = this.ticket.calculateHours();

        Vehicle vehicle = this.ticket.getVehicle();

        if(vehicle == null) {
            System.out.println("No vehicle found in the spot!");
            return;
        }

        int fee = (int) vehicle.calculateFee(hoursStayed);

        System.out.println("Total fee: " + fee);

        this.paymentService.processPayment(fee, paymentMethod);
        this.parkingLot.vacateSpot(parkingSpot, vehicle);

    }
}
