package payment;

public class PaymentService {
    public void processPayment(int fee, PaymentMethod paymentMethod) {
        PaymentStrategy paymentStrategy;
        switch (paymentMethod) {
            case UPI:
                paymentStrategy = new UPI();
                break;
            case CASH:
                paymentStrategy = new Cash();
                break;
            case CREDIT_CARD:
                paymentStrategy = new CreditCard();
                break;
            default:
                throw new IllegalArgumentException("Invalid payment method");
        }

        paymentStrategy.processPayment(fee);
    }
}
