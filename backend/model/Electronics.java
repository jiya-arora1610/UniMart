
package backend.model;

public class Electronics extends Product {

    private String brand;

    public Electronics(int productId, String name, double price,
                       String condition, int sellerId, String brand) {

        super(productId, name, price, condition, sellerId);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nBrand: " + brand;
    }
}

