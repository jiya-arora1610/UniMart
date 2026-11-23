package backend.model;

public class Fashion extends Product {

    private String size;
    private String brand;

    public Fashion(int productId, String name, double price,
                   String condition, int sellerId,
                   String size, String brand) {

        super(productId, name, price, condition, sellerId);
        this.size = size;
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String getCategory() {
        return "Fashion & Lifestyle";
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nSize: " + size +
                "\nBrand: " + brand;
    }
}

