public class DatabaseConnection {
    private final String tenantId;

    public DatabaseConnection(String tenantId) {
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("tenantId is required");
        }

        this.tenantId = tenantId;
    }

    public void saveHighValueOrder(Order order) {
        System.out.println("[" + tenantId + "] saving high value order: " + order.getOrderId());
    }

    public void saveRegularOrder(Order order) {
        System.out.println("[" + tenantId + "] saving regular order: " + order.getOrderId());
    }
}