
package backend.service;

import backend.model.*;
import java.util.ArrayList;

public class GroupBuyingService {

    private ArrayList<GroupPurchase> groups;
    private int nextGroupId = 1;

    public GroupBuyingService() {
        groups = new ArrayList<>();
    }

    // Create a group purchase
    public GroupPurchase createGroup(
            Product product, int minimumParticipants) {

        if (product == null || !product.isAvailable()) {
            throw new IllegalArgumentException(
                "Product is unavailable."
            );
        }

        if (minimumParticipants <= 0) {
            throw new IllegalArgumentException(
                "Minimum participants must be positive."
            );
        }

        GroupPurchase group = new GroupPurchase(
                nextGroupId++,
                product,
                minimumParticipants
        );

        groups.add(group);

        return group;
    }

    // Join an existing group
    public void joinGroup(int groupId, Student student) {

        GroupPurchase group = getGroupById(groupId);

        if (group == null) {
            throw new IllegalArgumentException(
                "Group not found."
            );
        }

        group.addParticipant(student);

        System.out.println("Successfully joined group!");
        System.out.println("Current participants: "
                + group.getParticipants().size());
        System.out.println("Current discount: "
                + group.calculateDiscount() + "%");
        System.out.println("Final price: Rs. "
                + group.getFinalPrice());
    }

    // Find group by ID
    public GroupPurchase getGroupById(int groupId) {

        for (GroupPurchase group : groups) {
            if (group.getGroupId() == groupId) {
                return group;
            }
        }

        return null;
    }

    // Display all groups
    public void displayGroups() {

        if (groups.isEmpty()) {
            System.out.println("No group purchases available.");
            return;
        }

        for (GroupPurchase group : groups) {
            System.out.println(group);
        }
    }

    public ArrayList<GroupPurchase> getGroups() {
        return new ArrayList<>(groups);
    }
}

