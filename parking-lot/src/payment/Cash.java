package payment;

public class Cash implements PaymentStrategy {
    @Override
    public void processPayment(int amount) {
        System.out.println("processing payment using cash " + amount);
    }
}
