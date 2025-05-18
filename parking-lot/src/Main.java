import gate.EntranceGate;
import gate.ExitGate;
import parkinglot.ParkingFloor;
import parkinglot.ParkingLot;
import parkinglot.Ticket;
import payment.PaymentMethod;
import payment.PaymentService;
import vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ParkingFloor parkingFloor = new ParkingFloor(1, 2, 2);
        List<ParkingFloor> parkingFloorList = new ArrayList<>();
        parkingFloorList.add(parkingFloor);
        ParkingLot parkingLot = new ParkingLot(parkingFloorList);


        EntranceGate entranceGate = new EntranceGate(parkingLot);
        Ticket ticket = entranceGate.processEntrance("DL9CBB6391", VehicleType.CAR);

        PaymentService paymentService = new PaymentService();
        ExitGate exitGate = new ExitGate(parkingLot, paymentService, ticket);
        exitGate.processExit(PaymentMethod.UPI);
    }
}