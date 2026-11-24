
package backend.main;

import backend.model.*;
import backend.database.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class UniMartApp {

    private static Scanner sc = new Scanner(System.in);

    // Database connection
    private static Connection conn;

    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            conn = connection;

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
                        registerStudent(conn);
                        break;

                    case 2:
                        Student student = loginStudent(conn);

                        if (student != null) {
                            studentDashboard(conn, student);
                        }
                        break;

                    case 3:
                        System.out.println("Thank you for using UniMart!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database connection error!");
            e.printStackTrace();
        }

        sc.close();
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

    // ==========================================
    // STUDENT REGISTRATION
    // ==========================================

    private static void registerStudent(Connection conn) {

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

        String query = "INSERT INTO students "
                + "(student_id, name, email, password, university) "
                + "VALUES ('" + studentId + "', '" + name + "', '"
                + email + "', '" + password
                + "', 'Chitkara University')";

        try (Statement s = conn.createStatement()) {

            int rows = s.executeUpdate(query);

            if (rows > 0) {
                System.out.println("\nRegistration successful!");
            }

        } catch (SQLException e) {

            System.out.println("Registration failed. Email or Student ID may already exist.");
        }
    }

    // ==========================================
    // STUDENT LOGIN
    // ==========================================

    private static Student loginStudent(Connection conn) {

        System.out.println("\n========== STUDENT LOGIN ==========");

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String query = "SELECT * FROM students WHERE email = '"
                + email + "' AND password = '" + password + "'";

        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            if (rs.next()) {

                Student student = new Student(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("student_id"),
                        rs.getString("university")
                );

                System.out.println("\nLogin successful!");
                System.out.println("Welcome, " + student.getName());

                return student;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Invalid email or password.");
        return null;
    }

    // ==========================================
    // STUDENT DASHBOARD
    // ==========================================

    private static void studentDashboard(Connection conn, Student student) {

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
                    displayProducts(conn);
                    break;

                case 2:
                    searchProduct(conn);
                    break;

                case 3:
                    sellProduct(conn, student);
                    break;

                case 4:
                    purchaseProduct(conn, student);
                    break;

                case 5:
                    groupBuyingMenu(conn, student);
                    break;

                case 6:
                    viewTransactions(conn, student);
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

    // ==========================================
    // DISPLAY PRODUCTS
    // ==========================================

    private static void displayProducts(Connection conn) {

        String query = "SELECT * FROM products WHERE available = TRUE";

        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            System.out.println("\n========== AVAILABLE PRODUCTS ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("\nProduct ID: "
                        + rs.getInt("product_id"));

                System.out.println("Name: "
                        + rs.getString("name"));

                System.out.println("Category: "
                        + rs.getString("category"));

                System.out.println("Price: Rs. "
                        + rs.getDouble("price"));

                System.out.println("Condition: "
                        + rs.getString("product_condition"));

                System.out.println("Seller ID: "
                        + rs.getInt("seller_id"));

                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("No products available.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // SEARCH PRODUCTS
    // ==========================================

    private static void searchProduct(Connection conn) {

        System.out.print("Enter product name: ");
        String name = sc.nextLine().trim();

        String query = "SELECT * FROM products WHERE name LIKE '%"
                + name + "%' AND available = TRUE";

        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("\nProduct ID: "
                        + rs.getInt("product_id"));

                System.out.println("Name: "
                        + rs.getString("name"));

                System.out.println("Category: "
                        + rs.getString("category"));

                System.out.println("Price: Rs. "
                        + rs.getDouble("price"));

                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("Product not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // SELL A PRODUCT
    // ==========================================

    private static void sellProduct(Connection conn, Student student) {

        System.out.println("\n========== SELL PRODUCT ==========");
        System.out.println("1. Electronics");
        System.out.println("2. Books");
        System.out.println("3. Fashion");

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
                            0, name, price, condition,
                            student.getUserId(), brand
                    );
                    break;

                case 2:

                    System.out.print("Author: ");
                    String author = sc.nextLine();

                    System.out.print("Subject: ");
                    String subject = sc.nextLine();

                    product = new Book(
                            0, name, price, condition,
                            student.getUserId(), author, subject
                    );
                    break;

                case 3:

                    System.out.print("Size: ");
                    String size = sc.nextLine();

                    System.out.print("Brand: ");
                    String fashionBrand = sc.nextLine();

                    product = new Fashion(
                            0, name, price, condition,
                            student.getUserId(), size, fashionBrand
                    );
                    break;

                default:
                    System.out.println("Invalid category.");
                    return;
            }

            addProduct(conn, product);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // INSERT PRODUCT INTO DATABASE

    private static void addProduct(Connection conn, Product product) {

        String query = "INSERT INTO products "
                + "(name, category, price, product_condition, seller_id) "
                + "VALUES ('" + product.getName() + "', '"
                + product.getCategory() + "', "
                + product.getPrice() + ", '"
                + product.getCondition() + "', "
                + product.getSellerId() + ")";

        try (Statement s = conn.createStatement()) {

            int rows = s.executeUpdate(query);

            if (rows > 0) {
                System.out.println("Product listed successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // PURCHASE PRODUCT
    // ==========================================

    private static void purchaseProduct(Connection conn, Student student) {

        System.out.println("\n========== PURCHASE PRODUCT ==========");

        displayProducts(conn);

        int productId = readInt("\nEnter Product ID: ");

        String query = "SELECT * FROM products WHERE product_id = "
                + productId + " AND available = TRUE";

        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            if (!rs.next()) {
                System.out.println("Product not found or unavailable.");
                return;
            }

            int sellerId = rs.getInt("seller_id");
            double amount = rs.getDouble("price");

            if (sellerId == student.getUserId()) {
                System.out.println("You cannot purchase your own product.");
                return;
            }

            conn.setAutoCommit(false);

            try (Statement update = conn.createStatement();
                 Statement insert = conn.createStatement()) {

                String updateQuery =
                        "UPDATE products SET available = FALSE "
                        + "WHERE product_id = " + productId
                        + " AND available = TRUE";

                int updated = update.executeUpdate(updateQuery);

                if (updated == 0) {
                    conn.rollback();
                    System.out.println("Product is no longer available.");
                    return;
                }

                String insertQuery =
                        "INSERT INTO transactions "
                        + "(buyer_id, seller_id, product_id, amount, status) "
                        + "VALUES (" + student.getUserId() + ", "
                        + sellerId + ", " + productId + ", "
                        + amount + ", 'Completed')";

                insert.executeUpdate(insertQuery);

                conn.commit();

                System.out.println("Purchase successful!");
                System.out.println("Amount: Rs. " + amount);

            } catch (SQLException e) {

                conn.rollback();
                System.out.println("Purchase failed.");
                e.printStackTrace();

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // GROUP BUYING MENU
    // ==========================================

    private static void groupBuyingMenu(Connection conn, Student student) {

        while (true) {

            System.out.println("\n========== GROUP BUYING ==========");
            System.out.println("1. View Group Offers");
            System.out.println("2. Create Group Purchase");
            System.out.println("3. Join Group Purchase");
            System.out.println("4. Back to Dashboard");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    displayGroups(conn);
                    break;

                case 2:
                    createGroup(conn);
                    break;

                case 3:
                    joinGroup(conn, student);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ==========================================
    // CREATE GROUP PURCHASE
    // ==========================================

    private static void createGroup(Connection conn) {

        System.out.println("\n========== CREATE GROUP ==========");

        displayProducts(conn);

        int productId = readInt("\nEnter Product ID: ");

        int minimum = readInt("Minimum participants: ");

        if (minimum <= 0) {
            System.out.println("Minimum participants must be positive.");
            return;
        }

        String query = "INSERT INTO group_purchases "
                + "(product_id, minimum_participants) "
                + "SELECT product_id, " + minimum
                + " FROM products WHERE product_id = "
                + productId + " AND available = TRUE";

        try (Statement s = conn.createStatement()) {

            int rows = s.executeUpdate(query);

            if (rows > 0) {
                System.out.println("Group purchase created successfully!");
            } else {
                System.out.println("Product not found or unavailable.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // DISPLAY GROUP OFFERS
    // ==========================================

    private static void displayGroups(Connection conn) {

        String query =
                "SELECT g.group_id, p.name, p.price, "
                + "g.minimum_participants, g.status, "
                + "COUNT(gm.student_id) AS participants "
                + "FROM group_purchases g "
                + "JOIN products p ON g.product_id = p.product_id "
                + "LEFT JOIN group_members gm ON g.group_id = gm.group_id "
                + "GROUP BY g.group_id, p.name, p.price, "
                + "g.minimum_participants, g.status";

        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                int count = rs.getInt("participants");

                double price = rs.getDouble("price");

                double discount = calculateDiscount(count);

                double finalPrice = price - (price * discount / 100);

                System.out.println("\nGroup ID: "
                        + rs.getInt("group_id"));

                System.out.println("Product: "
                        + rs.getString("name"));

                System.out.println("Original Price: Rs. " + price);

                System.out.println("Participants: " + count);

                System.out.println("Minimum Required: "
                        + rs.getInt("minimum_participants"));

                System.out.println("Discount: " + discount + "%");

                System.out.println("Final Price: Rs. " + finalPrice);

                System.out.println("Status: "
                        + rs.getString("status"));

                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("No group purchases available.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // JOIN GROUP PURCHASE
    // ==========================================

    private static void joinGroup(Connection conn, Student student) {

        System.out.println("\n========== JOIN GROUP ==========");

        displayGroups(conn);

        int groupId = readInt("\nEnter Group ID: ");

        String query = "INSERT INTO group_members "
                + "(group_id, student_id) "
                + "SELECT group_id, " + student.getUserId()
                + " FROM group_purchases "
                + "WHERE group_id = " + groupId
                + " AND status = 'Active'";

        try (Statement s = conn.createStatement()) {

            int rows = s.executeUpdate(query);

            if (rows > 0) {

                System.out.println("Successfully joined group!");

                displayGroupProgress(conn, groupId);

            } else {
                System.out.println("Group not found or inactive.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to join. You may already be a participant."
            );
        }
    }

    // ==========================================
    // GROUP BUYING DISCOUNT
    // ==========================================

    private static double calculateDiscount(int participants) {

        if (participants >= 20) {
            return 15;
        } else if (participants >= 10) {
            return 10;
        } else if (participants >= 5) {
            return 5;
        }

        return 0;
    }

    // ==========================================
    // DISPLAY GROUP PROGRESS
    // ==========================================

    private static void displayGroupProgress(
            Connection conn, int groupId) {

        String query =
                "SELECT COUNT(*) AS participants "
                + "FROM group_members WHERE group_id = " + groupId;

        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            if (rs.next()) {

                int count = rs.getInt("participants");

                System.out.println("Current participants: " + count);

                System.out.println("Current discount: "
                        + calculateDiscount(count) + "%");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // VIEW TRANSACTION HISTORY
    // ==========================================

    private static void viewTransactions(
            Connection conn, Student student) {

        System.out.println("\n========== MY TRANSACTIONS ==========");

        String query =
                "SELECT t.*, p.name AS product_name "
                + "FROM transactions t "
                + "JOIN products p ON t.product_id = p.product_id "
                + "WHERE t.buyer_id = " + student.getUserId()
                + " OR t.seller_id = " + student.getUserId();

        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("\nTransaction ID: "
                        + rs.getInt("transaction_id"));

                System.out.println("Product: "
                        + rs.getString("product_name"));

                System.out.println("Amount: Rs. "
                        + rs.getDouble("amount"));

                System.out.println("Status: "
                        + rs.getString("status"));

                System.out.println("Date: "
                        + rs.getTimestamp("transaction_date"));

                if (rs.getInt("buyer_id") == student.getUserId()) {
                    System.out.println("Type: Purchase");
                } else {
                    System.out.println("Type: Sale");
                }

                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("No transactions found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
