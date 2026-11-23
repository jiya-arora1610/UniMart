
package backend.model;

public class Student extends User implements Buyer, Seller {

    private String studentId;
    private String university;

    public Student(int userId, String name, String email,
                   String password, String studentId,
                   String university) {

        super(userId, name, email, password);

        this.studentId = studentId;
        this.university = university;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getUniversity() {
        return university;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public void purchaseProduct(Product product) {
        if (product.isAvailable()) {
            product.setAvailable(false);
            System.out.println("Purchase successful: " + product.getName());
        } else {
            System.out.println("Product is no longer available.");
        }
    }

    @Override
    public void listProduct(Product product) {
        System.out.println("Product listed: " + product.getName());
    }
}

