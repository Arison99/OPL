public class DatabaseOrderRepository implements IOrderRepository {
    private final DatabaseConnection db;

    public DatabaseOrderRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public void saveHighValueOrder(Order order) {
        db.saveHighValueOrder(order);
    }

    @Override
    public void saveRegularOrder(Order order) {
        db.saveRegularOrder(order);
    }
}