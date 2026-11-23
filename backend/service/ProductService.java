
package backend.service;

import backend.model.*;
import java.util.ArrayList;

public class ProductService {

    private ArrayList<Product> products;

    public ProductService() {
        products = new ArrayList<>();
    }

    // Add a product
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Product listed successfully!");
    }

    // Display all available products
    public void displayProducts() {

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products) {
            if (product.isAvailable()) {
                System.out.println(product);
            }
        }
    }

    // Search product by name
    public Product searchProduct(String name) {

        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)
                    && product.isAvailable()) {
                return product;
            }
        }

        return null;
    }

    // Search product by ID
    public Product getProductById(int productId) {

        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }

        return null;
    }

    // Purchase a product
    public boolean purchaseProduct(int productId, Student buyer) {

        Product product = getProductById(productId);

        if (product == null || !product.isAvailable()) {
            throw new IllegalArgumentException(
                "Product not found or unavailable."
            );
        }

        if (product.getSellerId() == buyer.getUserId()) {
            throw new IllegalArgumentException(
                "You cannot purchase your own product."
            );
        }

        product.setAvailable(false);
        System.out.println("Purchase successful!");

        return true;
    }

    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
