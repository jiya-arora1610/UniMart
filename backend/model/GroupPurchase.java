
package backend.model;

import java.util.ArrayList;

public class GroupPurchase {

    private int groupId;
    private Product product;
    private ArrayList<Student> participants;
    private int minimumParticipants;
    private String status;

    public GroupPurchase(int groupId, Product product,
                         int minimumParticipants) {

        this.groupId = groupId;
        this.product = product;
        this.minimumParticipants = minimumParticipants;
        this.participants = new ArrayList<>();
        this.status = "Active";
    }

    public int getGroupId() {
        return groupId;
    }

    public Product getProduct() {
        return product;
    }

    public ArrayList<Student> getParticipants() {
        return new ArrayList<>(participants);
    }

    public String getStatus() {
        return status;
    }

    public synchronized void addParticipant(Student student){   

        if (!status.equals("Active")) {
            throw new IllegalStateException("Group is not active.");
        }

        for (Student participant : participants) {
            if (participant.getUserId() == student.getUserId()) {
                throw new IllegalArgumentException(
                    "Student has already joined this group."
                );
            }
        }

        participants.add(student);
    }

    public double calculateDiscount() {

        int count = participants.size();

        if (count >= 20) {
            return 15;
        } else if (count >= 10) {
            return 10;
        } else if (count >= 5) {
            return 5;
        }

        return 0;
    }

    public double getFinalPrice() {

        double discount = calculateDiscount();

        return product.getPrice() -
                (product.getPrice() * discount / 100);
    }

    public void closeGroup() {
        status = "Closed";
    }

    @Override
    public String toString() {
        return "\nGroup ID: " + groupId +
                "\nProduct: " + product.getName() +
                "\nParticipants: " + participants.size() +
                "\nDiscount: " + calculateDiscount() + "%" +
                "\nFinal Price: Rs. " + getFinalPrice() +
                "\nStatus: " + status;
    }
}
