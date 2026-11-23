
package backend.main;

import backend.model.*;
import backend.service.*;

import java.util.ArrayList;
import java.util.Scanner;

public class UniMartApp {

    private static Scanner sc = new Scanner(System.in);

    // Application data
    private static ArrayList<Student> students = new ArrayList<>();

    // Services
    private static ProductService productService = new ProductService();
    private static TransactionService transactionService =
            new TransactionService();
    private static GroupBuyingService groupService =
            new GroupBuyingService();

    private static int nextUserId = 1;
    private static int nextProductId = 101;

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("               UNIMART");
        System.out.println("       THE UNIVERSITY COMMERCE NETWORK");
        System.out.println("     One Campus. Endless Possibilities.");
        System.out.println("========================================");

        while (true) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    registerStudent();
                    break;

                case 2:
                    Student student = loginStudent();

                    if (student != null) {
                        studentDashboard(student);
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using UniMart!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Safe integer input
    private static int readInt(String message) {

        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Safe decimal input
    private static double readDouble(String message) {

        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(sc.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    // Student Registration
    private static void registerStudent() {

        System.out.println("\n========== REGISTRATION ==========");

        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();

        System.out.print("University email: ");
        String email = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Student ID: ");
        String studentId = sc.nextLine().trim();

        if (name.isEmpty() || email.isEmpty()
                || password.isEmpty() || studentId.isEmpty()) {

            System.out.println("All fields are required.");
            return;
        }

        for (Student s : students) {

            if (s.getEmail().equalsIgnoreCase(email)
                    || s.getStudentId().equalsIgnoreCase(studentId)) {

                System.out.println("Account already exists.");
                return;
            }
        }

        Student student = new Student(
                nextUserId++,
                name,
                email,
                password,
                studentId,
                "Chitkara University"
        );

        students.add(student);

        System.out.println("\nRegistration successful!");
        System.out.println("Your User ID: " + student.getUserId());
    }

    // Student Login
    private static Student loginStudent() {

        System.out.println("\n========== STUDENT LOGIN ==========");

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine();

        for (Student student : students) {

            if (student.getEmail().equalsIgnoreCase(email)
                    && student.checkPassword(password)) {

                System.out.println("\nLogin successful!");
                System.out.println("Welcome, " + student.getName());

                return student;
            }
        }

        System.out.println("Invalid email or password.");
        return null;
    }

    // Student Dashboard
    private static void studentDashboard(Student student) {

        while (true) {

            System.out.println("\n========== UNIMART DASHBOARD ==========");
            System.out.println("Welcome, " + student.getName());
            System.out.println("--------------------------------------");
            System.out.println("1. Browse Products");
            System.out.println("2. Search Products");
            System.out.println("3. Sell a Product");
            System.out.println("4. Purchase a Product");
            System.out.println("5. Group Buying");
            System.out.println("6. My Transactions");
            System.out.println("7. My Profile");
            System.out.println("8. Logout");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    productService.displayProducts();
                    break;

                case 2:
                    searchProduct();
                    break;

                case 3:
                    sellProduct(student);
                    break;

                case 4:
                    purchaseProduct(student);
                    break;

                case 5:
                    groupBuyingMenu(student);
                    break;

                case 6:
                    viewTransactions(student);
                    break;

                case 7:
                    System.out.println(student);
                    break;

                case 8:
                    System.out.println("Logged out successfully.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Search Products
    private static void searchProduct() {

        System.out.print("Enter product name: ");
        String name = sc.nextLine().trim();

        Product product = productService.searchProduct(name);

        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("Product not found.");
        }
    }

    // Sell a Product
    private static void sellProduct(Student student) {

        System.out.println("\n========== SELL PRODUCT ==========");
        System.out.println("1. Electronics & Gadgets");
        System.out.println("2. Books, Notes & Stationery");
        System.out.println("3. Fashion & Lifestyle");

        int category = readInt("Select category: ");

        System.out.print("Product name: ");
        String name = sc.nextLine().trim();

        double price = readDouble("Price: ");

        System.out.print("Condition (New/Second-hand): ");
        String condition = sc.nextLine().trim();

        if (name.isEmpty() || condition.isEmpty() || price <= 0) {
            System.out.println("Invalid product details.");
            return;
        }

        Product product = null;

        try {

            switch (category) {

                case 1:
                    System.out.print("Brand: ");
                    String brand = sc.nextLine();

                    product = new Electronics(
                            nextProductId++, name, price,
                            condition, student.getUserId(), brand
                    );
                    break;

                case 2:
                    System.out.print("Author: ");
                    String author = sc.nextLine();

                    System.out.print("Subject: ");
                    String subject = sc.nextLine();

                    product = new Book(
                            nextProductId++, name, price,
                            condition, student.getUserId(),
                            author, subject
                    );
                    break;

                case 3:
                    System.out.print("Size: ");
                    String size = sc.nextLine();

                    System.out.print("Brand: ");
                    String fashionBrand = sc.nextLine();

                    product = new Fashion(
                            nextProductId++, name, price,
                            condition, student.getUserId(),
                            size, fashionBrand
                    );
                    break;

                default:
                    System.out.println("Invalid category.");
                    return;
            }

            productService.addProduct(product);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Purchase a Product
    private static void purchaseProduct(Student student) {

        System.out.println("\n========== PURCHASE PRODUCT ==========");

        productService.displayProducts();

        int productId = readInt("\nEnter Product ID: ");

        try {

            productService.purchaseProduct(productId, student);

        } catch (IllegalArgumentException e) {

            System.out.println("Purchase failed: " + e.getMessage());
        }
    }

    // Group Buying Menu
    private static void groupBuyingMenu(Student student) {

        while (true) {

            System.out.println("\n========== GROUP BUYING ==========");
            System.out.println("1. View Group Offers");
            System.out.println("2. Create Group Purchase");
            System.out.println("3. Join Group Purchase");
            System.out.println("4. Back to Dashboard");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    groupService.displayGroups();
                    break;

                case 2:
                    createGroup();
                    break;

                case 3:
                    joinGroup(student);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Create Group Purchase
    private static void createGroup() {

        System.out.println("\n========== CREATE GROUP ==========");

        productService.displayProducts();

        int productId = readInt("\nEnter Product ID: ");

        Product product = productService.getProductById(productId);

        int minimum = readInt("Minimum participants: ");

        try {

            GroupPurchase group =
                    groupService.createGroup(product, minimum);

            System.out.println("Group created successfully!");
            System.out.println("Group ID: " + group.getGroupId());

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Join Group Purchase
    private static void joinGroup(Student student) {

        System.out.println("\n========== JOIN GROUP ==========");

        groupService.displayGroups();

        int groupId = readInt("\nEnter Group ID: ");

        try {

            groupService.joinGroup(groupId, student);

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            System.out.println("Unable to join: " + e.getMessage());
        }
    }

    // View Transactions
    private static void viewTransactions(Student student) {

        System.out.println("\n========== MY TRANSACTIONS ==========");

        // TransactionService integration will be completed
        // after resolving seller references in the service layer.

        System.out.println("Transaction history integration pending.");
    }
}

