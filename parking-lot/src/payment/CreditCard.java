package payment;

public class CreditCard implements PaymentStrategy {
    @Override
    public void processPayment(int amount) {
        System.out.println("Processing payment using creditcard " + amount);
    }
}
