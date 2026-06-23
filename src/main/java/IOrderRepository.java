public interface IOrderRepository {
    void saveHighValueOrder(Order order);

    void saveRegularOrder(Order order);
}