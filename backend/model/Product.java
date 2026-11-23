
package backend.model;

public abstract class Product {

    private int productId;
    private String name;
    private double price;
    private String condition;
    private int sellerId;
    private boolean available;

    public Product(int productId, String name, double price,
                   String condition, int sellerId) {

        this.productId = productId;
        this.name = name;
        this.price = price;
        this.condition = condition;
        this.sellerId = sellerId;
        this.available = true;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public int getSellerId() {
        return sellerId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Invalid product price.");
        }
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract String getCategory();

    @Override
    public String toString() {
        return "\nProduct ID: " + productId +
                "\nName: " + name +
                "\nCategory: " + getCategory() +
                "\nPrice: Rs. " + price +
                "\nCondition: " + condition +
                "\nSeller ID: " + sellerId +
                "\nAvailable: " + available;
    }
}

