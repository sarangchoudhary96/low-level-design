# Table of Contents

- [Parking Lot](#parking-lot)
- [Tic Tac Toe](#tic-tac-toe)

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

# Tic Tac Toe

```java
class Board {
    private final char[][] board;

    public Board(int size) {
        this.board = new char[size][size];
        this.initialize(size);
    }

    public void initialize(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = '-';
            }
        }
    }

    public void updateCell(int row, int col, char symbol) {
        this.board[row][col] = symbol;
    }

    public boolean isCellEmpty(int row, int col) {
        return this.board[row][col] == '-';
    }

    public char getSymbol(int row, int col) {
        return this.board[row][col];
    }

    public int[] getBoardDimensions() {
        return new int[]{this.board.length, this.board[0].length};
    }
}

// Player information
class Player {
    private final String name;
    private final char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}

class Game {
    private final Board board;
    Player player1;
    Player player2;
    Player currentPlayer;

    public Game(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

    public void playTurn(Player player, int row, int col) {
        // Check if it's the player's turn
        if(currentPlayer != null && currentPlayer != player) {
            System.out.println(player.getName() + ", it's not your turn!");
            return;
        }
        if(this.board.isCellEmpty(row, col)) {
            this.board.updateCell(row, col, player.getSymbol());
            boolean isDraw = this.checkDraw();
            if(isDraw) {
                System.out.println("It's a draw!");
                return;
            }
            boolean isWinner = this.checkWinner(row, col, player);
            if(isWinner) {
                System.out.println(player.getName() + " wins!");
            }
            switchPlayer(player);
        } else {
            System.out.println("Cell is already occupied. Choose another cell.");
        }
    }

    private void switchPlayer(Player lastPlayer) {
        currentPlayer = lastPlayer == player1 ? player2 : player1;
    }

    public boolean checkWinner(int row, int col, Player player) {
        int[][] directions = {
                {0, -1},{0, 1}, // horizontal(left, right)
                {-1, 0},{1, 0}, // vertical(up, down)
                {1, 1},{-1, -1}, // diagonally
                {1, -1},{-1, 1} // diagonally
        };
        int n = this.board.getBoardDimensions()[0];
        for(int[] dir: directions) {
            int count = 1;
            int r = dir[0];
            int c = dir[1];

            int newRow = row + r;
            int newCol = col + c;
            while(newRow >= 0 && newRow < n && newCol >= 0 && newCol < n && this.board.getSymbol(newRow, newCol) == player.getSymbol()) {
                count++;
                newRow += r;
                newCol += c;
            }

            newRow = row - r;
            newCol = col - c;
            while(newRow >= 0 && newRow < n && newCol >= 0 && newCol < n && this.board.getSymbol(newRow, newCol) == player.getSymbol()) {
                count++;
                newRow -= r;
                newCol -= c;
            }
            if(count == n) return true;
        }

        return false;
    }

    public boolean checkDraw() {
        int n = this.board.getBoardDimensions()[0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.board.isCellEmpty(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
}

class Main {
    public static void main(String[] args) {
        int size = 3;
        Player player1 = new Player("Jhon", 'X');
        Player player2 = new Player("Alice", 'O');

        Board board = new Board(size);

        Game game = new Game(player1, player2, board);


        // Simulate a few moves
        game.playTurn(player1, 0, 0);
        game.playTurn(player2, 1, 1);
        game.playTurn(player1, 0, 1);
        game.playTurn(player2, 2, 2);
        game.playTurn(player1, 0, 2); // Jhon wins here
    }
}
```
