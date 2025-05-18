package payment;

public class UPI implements PaymentStrategy{
    @Override
    public void processPayment(int amount) {
        System.out.println("processing payment using UPI " + amount);
    }
}

