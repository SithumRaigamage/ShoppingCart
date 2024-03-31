import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginGUI extends  JFrame{

    //initializing usernameField
    private JTextField usernameField;
    //initializing passwordField
    private JPasswordField passwordField;
    //initializing loginList arraylist
    private ArrayList<User> loginList;
    //initializing username
    static String  username;
    //initializing password
    static String password;

    //LoginGUI constructor
    public  LoginGUI(){
        //adding logging profile for the customer to access the gui
        loginList = new ArrayList<>();
        loginList.add(new User( "Sithum", "Sraig@123"));
        loginList.add(new User( "Sraig", "Sraig@123"));


        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        //limiting the components by word limit
        usernameField = new JTextField(5);
        passwordField = new JPasswordField(15);

        //loginButton
        JButton loginButton = new JButton("Login");


        // Set up layout
        setLayout(new GridLayout(3, 2));
        // Add components to the frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);

        // Create a panel for the login button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        add(new JLabel()); // Empty label as a placeholder
        add(buttonPanel);

        //eventHandler for loginButton when button is triggered
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getting username and password
                username = usernameField.getText();
                password = new String(passwordField.getPassword());

                //authenticating Validate credentials

                if (validateLogin(username, password)) {
                    // Redirect to another frame (you can replace this with your logic)
                    dispose(); // close the current frame
                    redirectToAnotherFrame();

                } else {
                    //incorrect login
                    //popup gui error messsage
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid login credentials");
                }
            }
        });

    }
    //method for validating username and password
    private boolean validateLogin(String username, String password) {
        for (User user : loginList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Login successful
            }
        }
        return false; // Login failed
    }
    //redirect to main shoppingCenter GUI after successful login has happened
    private void redirectToAnotherFrame() {

        //creating shoppingManager object
        Shopping shoppingManager=new ShoppingManager();

        //reading the file for the products to be added
        shoppingManager.readProductsFromFile("products.dat");

        Map<String, Integer> availableItemsMap = new HashMap<>();
        // 'getProducts' is a method that returns a list of all products
        for (Product product : shoppingManager.getDisplay()) {
            availableItemsMap.put(product.getProductId(), product.getAvailableItems());
        }

        //initializing all the classes that is needed to initialize for the gui to work
        ShoppingCart shoppingCart = new ShoppingCart();
        User currentUser = new User(username, password);
        PurchaseManager purchaseManager = new PurchaseManager();
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart, currentUser, purchaseManager);
        TableGUI tableGUI = new TableGUI(shoppingManager, availableItemsMap, shoppingCart, shoppingCartGUI, currentUser, purchaseManager);

        // Set JFrame properties
        tableGUI.setTitle("Shopping center ");
        tableGUI.setSize(1200, 800);
        tableGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tableGUI.setLocationRelativeTo(null);
        tableGUI.setVisible(true);


    }
}
