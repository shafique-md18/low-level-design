package stockexchange;

public class StockHolding {
    private String stockId;
    private int quantity;
    private double averagePrice;

    public StockHolding(String stockId, int quantity, double price) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.averagePrice = price;
    }

    public void updateHolding(int newQuantity, double newPrice) {
        double totalValue = (quantity * averagePrice) + (newQuantity * newPrice);
        quantity += newQuantity;
        averagePrice = totalValue / quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
