import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Product {
    String name;
    double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class User {
    String username;
    String password;
    List<Product> cart;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new ArrayList<>();
    }
}

class Order {
    User user;
    List<Product> products;
    double totalAmount;

    public Order(User user, List<Product> products, double totalAmount) {
        this.user = user;
        this.products = products;
        this.totalAmount = totalAmount;
    }
}

class ECommercePlatform {
    private Map<String, Product> products;
    Map<String, User> users;
    private List<Order> orders;

    public ECommercePlatform() {
        products = new HashMap<>();
        users = new HashMap<>();
        orders = new ArrayList<>();
    }

    public void addProduct(String name, double price) {
        products.put(name, new Product(name, price));
    }

    public void registerUser(String username, String password) {
        users.put(username, new User(username, password));
    }

    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.password.equals(password);
    }

    public void addToCart(User user, String productName) {
        Product product = products.get(productName);
        if (product != null) {
            user.cart.add(product);
            System.out.println(productName + " added to the cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void removeFromCart(User user, String productName) {
        Product product = products.get(productName);
        if (product != null) {
            user.cart.remove(product);
            System.out.println(productName + " removed from the cart.");
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    public void displayCart(User user) {
        System.out.println("Shopping Cart for " + user.username + ":");
        for (Product product : user.cart) {
            System.out.println(product.name + " - $" + product.price);
        }
        System.out.println("Total Amount: $" + calculateCartTotal(user));
    }

    public void checkout(User user) {
        double totalAmount = calculateCartTotal(user);
        Order order = new Order(user, new ArrayList<>(user.cart), totalAmount);
        orders.add(order);

        // Perform secure payment processing here (not implemented in this example)

        // Clear the user's cart after successful checkout
        user.cart.clear();

        System.out.println("Order placed successfully. Total amount: $" + totalAmount);
    }

    private double calculateCartTotal(User user) {
        return user.cart.stream().mapToDouble(product -> product.price).sum();
    }
}

public class eCommerce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ECommercePlatform platform = new ECommercePlatform();

        // Adding some sample products
        platform.addProduct("Laptop", 899.99);
        platform.addProduct("Smartphone", 499.99);
        platform.addProduct("Headphones", 99.99);

        // Registering a sample user
        platform.registerUser("user1", "password");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Log In");
            System.out.println("3. Display Products");
            System.out.println("4. Add to Cart");
            System.out.println("5. Remove from Cart");
            System.out.println("6. Display Cart");
            System.out.println("7. Checkout");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String regUsername = scanner.next();
                    System.out.print("Enter password: ");
                    String regPassword = scanner.next();
                    platform.registerUser(regUsername, regPassword);
                    System.out.println("User registered successfully.");
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String logUsername = scanner.next();
                    System.out.print("Enter password: ");
                    String logPassword = scanner.next();

                    if (platform.authenticateUser(logUsername, logPassword)) {
                        System.out.println("Login successful.");
                        User user = platform.users.get(logUsername);
                        handleUserActions(scanner, platform, user);
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 3:
                    platform.displayCart(new User("guest", ""));
                    break;
                case 4:
                    System.out.print("Enter product name to add to cart: ");
                    String addToCartProduct = scanner.next();
                    platform.addToCart(new User("guest", ""), addToCartProduct);
                    break;
                case 5:
                    System.out.print("Enter product name to remove from cart: ");
                    String removeFromCartProduct = scanner.next();
                    platform.removeFromCart(new User("guest", ""), removeFromCartProduct);
                    break;
                case 6:
                    platform.displayCart(new User("guest", ""));
                    break;
                case 7:
                    platform.checkout(new User("guest", ""));
                    break;
                case 8:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleUserActions(Scanner scanner, ECommercePlatform platform, User user) {
        while (true) {
            System.out.println("\n1. Display Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. Remove from Cart");
            System.out.println("4. Display Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Log Out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    platform.displayCart(user);
                    break;
                case 2:
                    System.out.print("Enter the product name to add to the cart: ");
                    String addToCartProduct = scanner.next();
                    platform.addToCart(user, addToCartProduct);
                    break;
                case 3:
                    System.out.print("Enter the product name to remove from the cart: ");
                    String removeFromCartProduct = scanner.next();
                    platform.removeFromCart(user, removeFromCartProduct);
                    break;
                case 4:
                    platform.displayCart(user);
                    break;
                case 5:
                    platform.checkout(user);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
