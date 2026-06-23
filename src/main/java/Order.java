public class Order {
    private final String orderId;
    private final double amount;
    private final String customerEmail;

    public Order(String orderId, double amount, String customerEmail) {
        this.orderId = orderId;
        this.amount = amount;
        this.customerEmail = customerEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}