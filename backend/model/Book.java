
package backend.model;

public class Book extends Product {

    private String author;
    private String subject;

    public Book(int productId, String name, double price,
                String condition, int sellerId,
                String author, String subject) {

        super(productId, name, price, condition, sellerId);
        this.author = author;
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String getCategory() {
        return "Books";
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nAuthor: " + author +
                "\nSubject: " + subject;
    }
}

