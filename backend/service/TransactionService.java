
package backend.service;

import backend.model.*;
import java.util.ArrayList;

public class TransactionService {

    private ArrayList<Transaction> transactions;
    private int nextTransactionId = 1;

    public TransactionService() {
        transactions = new ArrayList<>();
    }

    // Create a transaction
    public Transaction createTransaction(
            Student buyer, Product product) {

        if (product == null || !product.isAvailable()) {
            throw new IllegalArgumentException(
                "Invalid transaction: Product unavailable."
            );
        }

        if (product.getSellerId() == buyer.getUserId()) {
            throw new IllegalArgumentException(
                "Cannot purchase your own product."
            );
        }

        Student seller = null;
        // Seller will be resolved through the user registry later.

        // Temporary seller reference handling
        Transaction transaction = new Transaction(
                nextTransactionId++,
                buyer,
                seller,
                product,
                product.getPrice()
        );

        transactions.add(transaction);

        return transaction;
    }

    // Display all transactions
    public void displayTransactions() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    // Get transactions of a particular student
    public ArrayList<Transaction> getStudentTransactions(int userId) {

        ArrayList<Transaction> result = new ArrayList<>();

        for (Transaction transaction : transactions) {

            if (transaction.getBuyer().getUserId() == userId) {
                result.add(transaction);
            }
        }

        return result;
    }
}
