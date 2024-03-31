import javax.swing.*;

import java.util.Scanner;

// Importing the Scanner class from java.util package


// Main class definition
public class Main {

    // Creating a static Scanner object for user input
    public static Scanner input = new Scanner(System.in);

    // Main method, the entry point of the program
    public static void main(String[] args) {


        // Creating an instance of WestminsterShoppingManager
        Shopping shoppingManager = new ShoppingManager();

        // Creating a new Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        while(true){
            try{

                // Prompting the user to enter whether they are a Manager (M) or Customer (C)
                System.out.print("Are you the Manager(M) or the Customer(C): ");
                String choice = scanner.nextLine();

                // Checking the user's choice
                if (choice.toUpperCase().equals("M")) {
                    // If the user is a Manager, run the shopping manager's menu
                    shoppingManager.runMenu();
                    break;
                }
                else if (choice.toUpperCase().equals("C")) {
                    // If the user is a Customer, create and display a LoginGUI window
                    LoginGUI loginGUI = new LoginGUI();
                    loginGUI.setTitle("Login Screen");
                    loginGUI.setSize(300, 300);
                    loginGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    loginGUI.setVisible(true);
                    loginGUI.setLocationRelativeTo(null);
                    break;
                }
                else {
                    // If the user enters an invalid choice, display an error message
                    System.out.println("Invalid Choice");
                }

            }
            catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // to consume the incorrect input
            }
        }
    }
}

