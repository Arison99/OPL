public class OrderProcessor {
    private final IOrderRepository repository;

    public OrderProcessor(IOrderRepository repository) {
        this.repository = repository;
    }

    public void process(Order order) {
        if (order.getAmount() > 1000) {
            repository.saveHighValueOrder(order);
        } else {
            repository.saveRegularOrder(order);
        }

        EmailSender.send(order.getCustomerEmail(), "Order Processed");
    }

    public void processOrder(String orderId, double discount) {
        if (orderId == null) {
            return;
        }

        // This method is retained from Part 1 so public behavior remains available.
    }
}