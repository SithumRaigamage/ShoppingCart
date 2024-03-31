import java.io.*;
import java.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ShoppingManager implements Shopping {

    public static Scanner input = new Scanner(System.in);
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<User> ManagerLogin;

    // Implementation of the addProductToSystem method from the interface
    @Override
    public void addProductToSystem(Product product) {
        try {
            if (products.size() < 50) {
                products.add(product);
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Cannot add more than 50 products.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of the deleteProductFromSystem method from the interface
    @Override
    public void deleteProductFromSystem(String productID) {
        try {
            for (Product product : products) {
                if (product.getProductId().equals(productID)) {
                    products.remove(product);
                    System.out.println("Product deleted successfully. Total products left: " + products.size());
                    return;
                }
            }
            System.out.println("Product with ID " + productID + " not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of the printProductsInSystem method from the interface
    @Override
    public void printProductsInSystem() {
        try {
            Collections.sort(products, (p1, p2) -> p1.getProductId().compareTo(p2.getProductId()));
            for (Product product : products) {
                System.out.println(product);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of the saveProductsToFile method from the interface
    @Override
    public void saveProductsToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName, true))) {
            oos.writeObject(products);
            System.out.println("Products appended to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Implementation of the readProductsFromFile method from the interface
    @Override
    public ArrayList<Product> readProductsFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            products = (ArrayList<Product>) ois.readObject();
            System.out.println("Products read from file: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Implementation of the getDisplay method from the interface
    @Override
    public ArrayList<Product> getDisplay() {
        return new ArrayList<>(products);
    }

    // Implementation of the getProductById method from the interface
    public Product getProductById(String productId) {
        try {
            for (Product product : products) {
                if (product.getProductId().equals(productId)) {
                    return product;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Implementation of the runMenu method from the interface
    public void runMenu() {
        try {
            ManagerLogin = new ArrayList<>();
            ManagerLogin.add(new User("Sithum", "Sithum123"));
            boolean loop = true;
            while (loop) {
                System.out.println("Choose Option ");
                System.out.println("1. Register");
                System.out.println("2. Login");

                System.out.print("Enter Choice :");
                Scanner scanner = new Scanner(System.in);

                try {
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            // Registration code
                            System.out.println(" ");
                            System.out.println("Register");
                            System.out.println(" ");

                            System.out.print("Enter UserName :");
                            String username = scanner.next();

                            System.out.print("Enter Password :");
                            String password = scanner.next();

                            ManagerLogin.add(new User(username, password));
                            System.out.println("User Successfully Added");
                            break;
                        case 2:
                            // Login code
                            System.out.println("Login");
                            System.out.println(" ");
                            System.out.print("Enter UserName :");
                            String userName = scanner.next();

                            System.out.print("Enter Password :");
                            String Password = scanner.next();

                            if (ValidateLogin(userName, Password)) {
                                loop = false;
                                System.out.println(" ");
                                System.out.println("Successfully Logged in");
                                MenuOption();
                            } else {
                                System.out.println("Login Failed, Please check Username or Password");
                                continue;
                            }
                            break;
                        default:
                            System.out.println("Enter Valid Choice");
                            break;
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.nextLine(); // Clear the buffer
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // Implementation of the ValidateLogin method
    public boolean ValidateLogin(String username, String password) {
        try {
            for (User user : ManagerLogin) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return true; // Login successful
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }

    // Implementation of the MenuOption method
    public void MenuOption() {
        try {
            boolean condition = true;
            while (condition) {
                System.out.println(" ");
                printProductsInSystem();

                System.out.println("Please select one of the options below ");
                System.out.println(" ");
                System.out.println("1. Add Product ");
                System.out.println("2. Delete Product");
                System.out.println("3. Display Products");
                System.out.println("4. Save in a File");
                System.out.println("5. Exit");

                System.out.println(" ");
                System.out.print("Enter choice :");
                int option = input.nextInt();

                switch (option) {
                    case 1:
                        System.out.println(" ");
                        System.out.print("Clothing(C) Or Electronic(E) :");
                        String choice = input.next();

                        if (choice.toUpperCase().equals("E")) {
                            System.out.print("Enter ProductId of the Electronic :");
                            String ProductId = input.next();
                            System.out.print("Enter Electronic Product Name :");
                            String ProductName = input.next();
                            System.out.print("Enter Electronic AvailableItems :");
                            int AvailableItems = input.nextInt();
                            System.out.print("Enter  Price of the Electronic :");
                            double ProductPrice = input.nextDouble();
                            System.out.print("Enter Electronic Brand :");
                            String ProductBrand = input.next();
                            System.out.print("Enter Warranty Period of the Electronic :");
                            int warrantyPeriod = input.nextInt();
                            Product electronic = new Electronic(ProductId, ProductName, AvailableItems, ProductPrice, ProductBrand, warrantyPeriod);
                            addProductToSystem(electronic);
                            System.out.println("Electronic Product Successfully added");
                            break;
                        } else if (choice.toUpperCase().equals("C")) {
                            System.out.print("Enter ProductId of the Clothing :");
                            String ProductId = input.next();
                            System.out.print("Enter Clothing Product Name :");
                            String ProductName = input.next();
                            System.out.print("Enter AvailableItems of the Clothing :");
                            int AvailableItems = input.nextInt();
                            System.out.print("Enter Price of the Clothing :");
                            double ProductPrice = input.nextDouble();
                            System.out.print("Enter Size of the Clothing :");
                            String ProductSize = input.next();
                            System.out.print("Enter Color of the Clothing :");
                            String ProductColor = input.next();
                            Product clothing = new Clothing(ProductId, ProductName, AvailableItems, ProductPrice, ProductSize, ProductColor);
                            addProductToSystem(clothing);
                            System.out.println("Clothing Product Successfully added");
                        } else {
                            System.out.println("Enter A Valid Character");
                        }
                        break;
                    case 2:
                        System.out.print("Enter ProductId That you want to Delete :");
                        String ProductId = input.next();
                        deleteProductFromSystem(ProductId);
                        break;
                    case 3:
                        printProductsInSystem();
                        break;
                    case 4:
                        saveProductsToFile("products.dat");
                        break;
                    case 5:
                        System.out.println("Program Exit");
                        condition = false;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to generate random data for electronics

    // Implementation of the AppendableObjectOutputStream class


}
