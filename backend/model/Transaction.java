
package backend.model;

import java.time.LocalDateTime;

public class Transaction {

    private int transactionId;
    private Student buyer;
    private Student seller;
    private Product product;
    private double amount;
    private LocalDateTime transactionDate;
    private String status;

    public Transaction(int transactionId, Student buyer,
                       Student seller, Product product,
                       double amount) {

        this.transactionId = transactionId;
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
        this.status = "Completed";
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Student getBuyer() {
        return buyer;
    }

    public Student getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nTransaction ID: " + transactionId +
                "\nBuyer: " + buyer.getName() +
                "\nSeller: " + seller.getName() +
                "\nProduct: " + product.getName() +
                "\nAmount: Rs. " + amount +
                "\nDate: " + transactionDate +
                "\nStatus: " + status;
    }
}

