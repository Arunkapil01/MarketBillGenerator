import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarketBillGenerator {
    private JFrame frame;
    private JTextField usernameField, quantityField, searchField, priceField;
    private JPasswordField passwordField;
    private JTextArea cartArea;
    private JComboBox<String> itemComboBox;
    private ArrayList<Item> cart = new ArrayList<>();
    private double totalAmount = 0;
    private final Map<String, Double> itemPriceMap = new HashMap<>();
    private Connection connection;

    public MarketBillGenerator() {
        populateItemPriceMap();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./billsDB", "sa", "");
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Bills (id IDENTITY, customerName VARCHAR, mobileNumber VARCHAR, totalAmount DOUBLE, billDetails VARCHAR)");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createLoginPage();
    }
private void populateItemPriceMap() {
    // Beverages
    itemPriceMap.put("Apple Juice", 50.0);
    itemPriceMap.put("Orange Juice", 30.0);
    itemPriceMap.put("Mango Juice", 45.0);
    itemPriceMap.put("Grape Juice", 55.0);
    itemPriceMap.put("Lemonade", 25.0);
    itemPriceMap.put("Cola", 20.0);
    itemPriceMap.put("Diet Cola", 25.0);
    itemPriceMap.put("Sparkling Water", 15.0);
    itemPriceMap.put("Still Water", 10.0);
    itemPriceMap.put("Iced Tea", 30.0);
    itemPriceMap.put("Energy Drink", 60.0);
    itemPriceMap.put("Sports Drink", 50.0);
    itemPriceMap.put("Almond Milk", 80.0);
    itemPriceMap.put("Soy Milk", 70.0);
    itemPriceMap.put("Oat Milk", 90.0);
    itemPriceMap.put("Coffee", 120.0);
    itemPriceMap.put("Tea", 100.0);
    itemPriceMap.put("Hot Chocolate", 110.0);
    itemPriceMap.put("Coconut Water", 40.0);
    
    // Snacks
    itemPriceMap.put("Biscuit", 10.0);
    itemPriceMap.put("Chocolate", 10.0);
    itemPriceMap.put("Candy", 5.0);
    itemPriceMap.put("Chips", 20.0);
    itemPriceMap.put("Popcorn", 30.0);
    itemPriceMap.put("Nuts", 100.0);
    itemPriceMap.put("Trail Mix", 120.0);
    itemPriceMap.put("Granola Bar", 25.0);
    itemPriceMap.put("Protein Bar", 50.0);
    itemPriceMap.put("Pretzels", 30.0);
    itemPriceMap.put("Crackers", 40.0);
    itemPriceMap.put("Fruit Snacks", 15.0);
    itemPriceMap.put("Rice Cakes", 35.0);
    itemPriceMap.put("Pita Chips", 45.0);
    itemPriceMap.put("Cheese Puffs", 25.0);
    itemPriceMap.put("Beef Jerky", 150.0);
    
    // Bakery
    itemPriceMap.put("Bread", 15.0);
    itemPriceMap.put("Bagels", 40.0);
    itemPriceMap.put("Croissants", 50.0);
    itemPriceMap.put("Donuts", 20.0);
    itemPriceMap.put("Muffins", 30.0);
    itemPriceMap.put("Cake", 200.0);
    itemPriceMap.put("Cupcakes", 120.0);
    itemPriceMap.put("Cookies", 50.0);
    itemPriceMap.put("Brownies", 80.0);
    itemPriceMap.put("Pie", 250.0);
    itemPriceMap.put("Pastries", 60.0);
    itemPriceMap.put("Tortillas", 40.0);
    
    // Dairy
    itemPriceMap.put("Butter", 50.0);
    itemPriceMap.put("Cheese", 100.0);
    itemPriceMap.put("Yogurt", 30.0);
    itemPriceMap.put("Sour Cream", 40.0);
    itemPriceMap.put("Cottage Cheese", 60.0);
    itemPriceMap.put("Cream Cheese", 70.0);
    itemPriceMap.put("Whipped Cream", 90.0);
    itemPriceMap.put("Ice Cream", 120.0);
    itemPriceMap.put("Milk", 30.0);
    itemPriceMap.put("Eggs", 5.0);
    itemPriceMap.put("Greek Yogurt", 50.0);
    itemPriceMap.put("Heavy Cream", 70.0);
    
    // Meat
    itemPriceMap.put("Chicken Breast", 150.0);
    itemPriceMap.put("Ground Beef", 200.0);
    itemPriceMap.put("Pork Chops", 250.0);
    itemPriceMap.put("Bacon", 120.0);
    itemPriceMap.put("Sausage", 100.0);
    itemPriceMap.put("Ham", 80.0);
    itemPriceMap.put("Turkey", 180.0);
    itemPriceMap.put("Steak", 300.0);
    itemPriceMap.put("Salmon", 350.0);
    itemPriceMap.put("Shrimp", 400.0);
    itemPriceMap.put("Lamb", 450.0);
    itemPriceMap.put("Duck", 500.0);
    
    // Produce
    itemPriceMap.put("Apple", 10.0);
    itemPriceMap.put("Banana", 5.0);
    itemPriceMap.put("Orange", 8.0);
    itemPriceMap.put("Grapes", 30.0);
    itemPriceMap.put("Strawberries", 50.0);
    itemPriceMap.put("Blueberries", 70.0);
    itemPriceMap.put("Raspberries", 80.0);
    itemPriceMap.put("Pineapple", 60.0);
    itemPriceMap.put("Mango", 20.0);
    itemPriceMap.put("Watermelon", 40.0);
    itemPriceMap.put("Lettuce", 15.0);
    itemPriceMap.put("Spinach", 20.0);
    itemPriceMap.put("Broccoli", 30.0);
    itemPriceMap.put("Carrots", 10.0);
    itemPriceMap.put("Tomatoes", 20.0);
    itemPriceMap.put("Cucumber", 15.0);
    itemPriceMap.put("Onions", 12.0);
    itemPriceMap.put("Garlic", 8.0);
    itemPriceMap.put("Potatoes", 10.0);
    itemPriceMap.put("Sweet Potatoes", 25.0);
    itemPriceMap.put("Peppers", 30.0);
    itemPriceMap.put("Avocado", 50.0);
    itemPriceMap.put("Zucchini", 20.0);
    itemPriceMap.put("Cauliflower", 40.0);
    itemPriceMap.put("Mushrooms", 60.0);
    itemPriceMap.put("Eggplant", 30.0);
    itemPriceMap.put("Cabbage", 15.0);
    itemPriceMap.put("Green Beans", 25.0);
    
    // Grains
    itemPriceMap.put("Rice", 40.0);
    itemPriceMap.put("Quinoa", 100.0);
    itemPriceMap.put("Oats", 50.0);
    itemPriceMap.put("Pasta", 60.0);
    itemPriceMap.put("Spaghetti", 70.0);
    itemPriceMap.put("Noodles", 10.0);
    itemPriceMap.put("Flour", 30.0);
    itemPriceMap.put("Cornmeal", 40.0);
    itemPriceMap.put("Cereal", 120.0);
    itemPriceMap.put("Bread", 15.0);
    itemPriceMap.put("Tortillas", 40.0);
    itemPriceMap.put("Pita Bread", 35.0);
    itemPriceMap.put("Bagels", 40.0);
    itemPriceMap.put("English Muffins", 50.0);
    itemPriceMap.put("Croissants", 50.0);
    
    // Frozen Foods
    itemPriceMap.put("Frozen Pizza", 200.0);
    itemPriceMap.put("Frozen Vegetables", 50.0);
    itemPriceMap.put("Frozen Fruit", 60.0);
    itemPriceMap.put("Frozen Waffles", 70.0);
    itemPriceMap.put("Frozen Dinners", 150.0);
    itemPriceMap.put("Frozen Desserts", 120.0);
    itemPriceMap.put("Frozen Fish", 250.0);
    itemPriceMap.put("Frozen Meatballs", 180.0);
    itemPriceMap.put("Frozen Bread", 90.0);
    itemPriceMap.put("Frozen Yogurt", 80.0);
    itemPriceMap.put("Frozen Chicken", 220.0);
    itemPriceMap.put("Frozen Beef", 300.0);
    
    // Household Items
    itemPriceMap.put("Detergent", 50.0);
    itemPriceMap.put("Dish Soap", 20.0);
    itemPriceMap.put("Cleaning Spray", 60.0);
    itemPriceMap.put("Sponges", 15.0);
    itemPriceMap.put("Paper Towels", 30.0);
    itemPriceMap.put("Toilet Paper", 40.0);
    itemPriceMap.put("Trash Bags", 50.0);
    itemPriceMap.put("Aluminum Foil", 20.0);
    itemPriceMap.put("Plastic Wrap", 25.0);
    itemPriceMap.put("Light Bulbs", 30.0);
    itemPriceMap.put("Batteries", 100.0);
    itemPriceMap.put("Laundry Detergent", 70.0);
    itemPriceMap.put("Fabric Softener", 60.0);
    itemPriceMap.put("Bleach", 40.0);
    itemPriceMap.put("Disinfectant Wipes", 50.0);
    itemPriceMap.put("Mop", 150.0);
    itemPriceMap.put("Broom", 100.0);
    itemPriceMap.put("Dustpan", 40.0);
    itemPriceMap.put("Vacuum Cleaner", 300.0);
    
    // Personal Care
    itemPriceMap.put("Shampoo", 80.0);
    itemPriceMap.put("Conditioner", 90.0);
    itemPriceMap.put("Toothpaste", 40.0);
    itemPriceMap.put("Toothbrush", 25.0);
    itemPriceMap.put("Mouthwash", 60.0);
    itemPriceMap.put("Deodorant", 70.0);
    itemPriceMap.put("Soap", 20.0);
    itemPriceMap.put("Body Wash", 50.0);
    itemPriceMap.put("Hand Sanitizer", 30.0);
    itemPriceMap.put("Face Wash", 70.0);
    itemPriceMap.put("Moisturizer", 90.0);
    itemPriceMap.put("Sunscreen", 100.0);
    itemPriceMap.put("Razor", 50.0);
    itemPriceMap.put("Shaving Cream", 60.0);
    itemPriceMap.put("Hair Gel", 40.0);
    itemPriceMap.put("Hair Spray", 60.0);
    itemPriceMap.put("Lotion", 80.0);
    itemPriceMap.put("Lip Balm", 20.0);
    itemPriceMap.put("Cotton Swabs", 30.0);
    itemPriceMap.put("Cotton Balls", 25.0);
    itemPriceMap.put("Feminine Hygiene Products", 120.0);
    itemPriceMap.put("Perfume", 300.0);
    itemPriceMap.put("Aftershave", 70.0);
    itemPriceMap.put("Nail Polish", 50.0);
    itemPriceMap.put("Nail Polish Remover", 40.0);
    itemPriceMap.put("Hairbrush", 80.0);
    itemPriceMap.put("Comb", 30.0);
    itemPriceMap.put("Tweezers", 20.0);
    itemPriceMap.put("Makeup Remover", 60.0);
    
    // Baby Products
    itemPriceMap.put("Diapers", 150.0);
    itemPriceMap.put("Baby Wipes", 40.0);
    itemPriceMap.put("Baby Lotion", 60.0);
    itemPriceMap.put("Baby Shampoo", 50.0);
    itemPriceMap.put("Baby Powder", 30.0);
    itemPriceMap.put("Baby Formula", 200.0);
    itemPriceMap.put("Baby Food", 25.0);
    itemPriceMap.put("Baby Bottles", 70.0);
    itemPriceMap.put("Baby Pacifier", 20.0);
    itemPriceMap.put("Baby Soap", 40.0);
    itemPriceMap.put("Baby Oil", 50.0);
    itemPriceMap.put("Baby Crib", 1000.0);
    itemPriceMap.put("Baby Stroller", 1500.0);
    itemPriceMap.put("Baby Monitor", 800.0);
    
    // Pet Supplies
    itemPriceMap.put("Dog Food", 200.0);
    itemPriceMap.put("Cat Food", 180.0);
    itemPriceMap.put("Bird Food", 100.0);
    itemPriceMap.put("Fish Food", 50.0);
    itemPriceMap.put("Cat Litter", 70.0);
    itemPriceMap.put("Dog Leash", 150.0);
    itemPriceMap.put("Dog Collar", 100.0);
    itemPriceMap.put("Cat Collar", 90.0);
    itemPriceMap.put("Pet Shampoo", 60.0);
    itemPriceMap.put("Pet Toys", 50.0);
    itemPriceMap.put("Pet Bed", 300.0);
    itemPriceMap.put("Pet Crate", 500.0);
    itemPriceMap.put("Pet Carrier", 400.0);
    
    // Canned Goods
    itemPriceMap.put("Canned Beans", 40.0);
    itemPriceMap.put("Canned Corn", 30.0);
    itemPriceMap.put("Canned Peas", 35.0);
    itemPriceMap.put("Canned Tomatoes", 40.0);
    itemPriceMap.put("Canned Tuna", 50.0);
    itemPriceMap.put("Canned Chicken", 70.0);
    itemPriceMap.put("Canned Soup", 60.0);
    itemPriceMap.put("Canned Fruit", 50.0);
    itemPriceMap.put("Canned Vegetables", 45.0);
    itemPriceMap.put("Canned Chili", 80.0);
    itemPriceMap.put("Canned Pasta", 70.0);
    
    // Baking & Cooking
    itemPriceMap.put("Flour", 30.0);
    itemPriceMap.put("Sugar", 40.0);
    itemPriceMap.put("Brown Sugar", 45.0);
    itemPriceMap.put("Powdered Sugar", 50.0);
    itemPriceMap.put("Baking Soda", 20.0);
    itemPriceMap.put("Baking Powder", 25.0);
    itemPriceMap.put("Yeast", 15.0);
    itemPriceMap.put("Cornstarch", 20.0);
    itemPriceMap.put("Cooking Oil", 120.0);
    itemPriceMap.put("Olive Oil", 150.0);
    itemPriceMap.put("Vegetable Oil", 100.0);
    itemPriceMap.put("Salt", 10.0);
    itemPriceMap.put("Pepper", 20.0);
    itemPriceMap.put("Cinnamon", 30.0);
    itemPriceMap.put("Vanilla Extract", 80.0);
    itemPriceMap.put("Honey", 90.0);
    itemPriceMap.put("Maple Syrup", 100.0);
    itemPriceMap.put("Soy Sauce", 50.0);
    itemPriceMap.put("Tomato Sauce", 70.0);
    itemPriceMap.put("Pasta Sauce", 80.0);
    itemPriceMap.put("Barbecue Sauce", 60.0);
    itemPriceMap.put("Hot Sauce", 40.0);
    itemPriceMap.put("Ketchup", 30.0);
    itemPriceMap.put("Mustard", 25.0);
    itemPriceMap.put("Mayonnaise", 50.0);
    
    // Condiments & Spices
    itemPriceMap.put("Salt", 10.0);
    itemPriceMap.put("Pepper", 20.0);
    itemPriceMap.put("Garlic Powder", 30.0);
    itemPriceMap.put("Onion Powder", 25.0);
    itemPriceMap.put("Paprika", 35.0);
    itemPriceMap.put("Cumin", 40.0);
    itemPriceMap.put("Chili Powder", 30.0);
    itemPriceMap.put("Oregano", 25.0);
    itemPriceMap.put("Basil", 30.0);
    itemPriceMap.put("Thyme", 35.0);
    itemPriceMap.put("Rosemary", 40.0);
    itemPriceMap.put("Parsley", 20.0);
    itemPriceMap.put("Bay Leaves", 50.0);
    itemPriceMap.put("Curry Powder", 60.0);
    itemPriceMap.put("Turmeric", 70.0);
    itemPriceMap.put("Ginger", 50.0);
    itemPriceMap.put("Cloves", 60.0);
    itemPriceMap.put("Nutmeg", 80.0);
    itemPriceMap.put("Coriander", 30.0);
    itemPriceMap.put("Fennel", 35.0);
    
    // Ready-to-Eat & Instant Foods
    itemPriceMap.put("Instant Noodles", 20.0);
    itemPriceMap.put("Instant Soup", 25.0);
    itemPriceMap.put("Microwave Popcorn", 30.0);
    itemPriceMap.put("Instant Oatmeal", 40.0);
    itemPriceMap.put("Instant Rice", 50.0);
    itemPriceMap.put("Frozen Pizza", 200.0);
    itemPriceMap.put("Frozen Dinners", 150.0);
    itemPriceMap.put("Frozen Waffles", 70.0);
    itemPriceMap.put("Frozen Vegetables", 50.0);
    itemPriceMap.put("Frozen Fruit", 60.0);
    itemPriceMap.put("Frozen Yogurt", 80.0);
    
    // Miscellaneous
    itemPriceMap.put("Canned Coconut Milk", 60.0);
    itemPriceMap.put("Canned Pumpkin", 70.0);
    itemPriceMap.put("Canned Artichokes", 80.0);
    itemPriceMap.put("Canned Beans", 40.0);
    itemPriceMap.put("Canned Corn", 30.0);
    itemPriceMap.put("Canned Peas", 35.0);
    itemPriceMap.put("Canned Tomatoes", 40.0);
    itemPriceMap.put("Canned Tuna", 50.0);
    itemPriceMap.put("Canned Chicken", 70.0);
    itemPriceMap.put("Canned Soup", 60.0);
    itemPriceMap.put("Canned Fruit", 50.0);
    itemPriceMap.put("Canned Vegetables", 45.0);
    
    // Office Supplies
    itemPriceMap.put("Pens", 15.0);
    itemPriceMap.put("Pencils", 10.0);
    itemPriceMap.put("Notebooks", 50.0);
    itemPriceMap.put("Sticky Notes", 25.0);
    itemPriceMap.put("Paper Clips", 20.0);
    itemPriceMap.put("Stapler", 40.0);
    itemPriceMap.put("Staples", 15.0);
    itemPriceMap.put("Tape", 30.0);
    itemPriceMap.put("Scissors", 50.0);
    itemPriceMap.put("Envelopes", 30.0);
    itemPriceMap.put("Folders", 20.0);
    itemPriceMap.put("Binders", 60.0);
    itemPriceMap.put("Printer Paper", 100.0);
    itemPriceMap.put("Highlighters", 40.0);
    itemPriceMap.put("Markers", 50.0);
    
    // Electronics
    itemPriceMap.put("Smartphone", 15000.0);
    itemPriceMap.put("Laptop", 50000.0);
    itemPriceMap.put("Tablet", 20000.0);
    itemPriceMap.put("Earbuds", 1500.0);
    itemPriceMap.put("Headphones", 3000.0);
    itemPriceMap.put("Smartwatch", 10000.0);
    itemPriceMap.put("Charger", 500.0);
    itemPriceMap.put("Power Bank", 1500.0);
    itemPriceMap.put("USB Cable", 300.0);
    itemPriceMap.put("Memory Card", 1000.0);
    itemPriceMap.put("Portable Speaker", 2500.0);
    itemPriceMap.put("Bluetooth Speaker", 3000.0);
    itemPriceMap.put("Camera", 30000.0);
    itemPriceMap.put("Television", 50000.0);
    itemPriceMap.put("Gaming Console", 40000.0);
    itemPriceMap.put("External Hard Drive", 5000.0);
    itemPriceMap.put("Flash Drive", 800.0);
    itemPriceMap.put("Mouse", 600.0);
    itemPriceMap.put("Keyboard", 1000.0);
    itemPriceMap.put("Monitor", 15000.0);
	//Dal
	itemPriceMap.put("Toor Dal", 80.0);
    itemPriceMap.put("Chana Dal", 70.0);
	itemPriceMap.put("Moong Dal", 90.0);
	itemPriceMap.put("Masoor Dal", 75.0);
	itemPriceMap.put("Urad Dal", 85.0);
	itemPriceMap.put("Lobiya Dal", 65.0);
	itemPriceMap.put("Rajma", 120.0);
	itemPriceMap.put("Kabuli Chana", 110.0);
	itemPriceMap.put("Kala Chana", 100.0);
	itemPriceMap.put("Moth Dal", 60.0);
	itemPriceMap.put("Masoor Malka Dal", 80.0);
	itemPriceMap.put("Moong Chilka Dal", 85.0);
	itemPriceMap.put("Urad Chilka Dal", 90.0);
	itemPriceMap.put("Dhuli Moong Dal", 95.0);
	itemPriceMap.put("Urad Dhuli Dal", 100.0);
	itemPriceMap.put("Masoor Whole Dal", 80.0);	
	itemPriceMap.put("Sabut Urad Dal", 90.0);
	itemPriceMap.put("Sabut Moong Dal", 100.0);
	itemPriceMap.put("Mung Dal", 90.0);
	itemPriceMap.put("Horse Gram", 60.0);
}

private JPanel createBackgroundPanel() {
    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            String[] backgrounds = {"background1.jpg", "background.jpg","background2.jpg"};
            int randomIndex = (int) (Math.random() * backgrounds.length);
            ImageIcon icon = new ImageIcon(backgrounds[randomIndex]);
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };
    return panel;
}

    private void createLoginPage() {
        frame = new JFrame("Market Bill Generator");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen mode
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = createBackgroundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;

        Font largeFont = new Font("Arial", Font.BOLD, 24);

        usernameField = new JTextField(20);
        usernameField.setFont(largeFont);
        passwordField = new JPasswordField(20);
        passwordField.setFont(largeFont);
        JButton loginButton = new JButton("Login");
        loginButton.setFont(largeFont);
        loginButton.setPreferredSize(new Dimension(150, 50));
        loginButton.addActionListener(e -> {
            if (validateLogin()) createEnterItemsPage();
            else JOptionPane.showMessageDialog(frame, "Invalid Credentials");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:") {{
            setFont(largeFont);
        }}, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:") {{
            setFont(largeFont);
        }}, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);
		

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private boolean validateLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Hardcoded credentials for demonstration
        String validUsername = "Arun";
        String validPassword = "1234";

        // Validate username and password
        return username.equals(validUsername) && password.equals(validPassword);
    }

    private void createEnterItemsPage() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel panel = createBackgroundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel searchLabel = new JLabel("Search Item:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(searchLabel, gbc);

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterItems();
            }
        });
        gbc.gridx = 1;
        panel.add(searchField, gbc);

        JLabel itemLabel = new JLabel("Select Item:");
        itemLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(itemLabel, gbc);

        itemComboBox = new JComboBox<>(itemPriceMap.keySet().toArray(new String[0]));
        itemComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        itemComboBox.addActionListener(e -> updatePriceField());
        gbc.gridx = 1;
        panel.add(itemComboBox, gbc);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(quantityLabel, gbc);

        quantityField = new JTextField("1", 10); // Default quantity is 1
        quantityField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        panel.add(quantityField, gbc);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(priceLabel, gbc);

        priceField = new JTextField(10);
        priceField.setFont(new Font("Arial", Font.PLAIN, 18));
        priceField.setEditable(false);
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        JButton addButton = new JButton("Add to Cart");
        addButton.setFont(new Font("Arial", Font.PLAIN, 18));
        addButton.addActionListener(e -> addToCart());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(addButton, gbc);

        cartArea = new JTextArea(10, 30);
        cartArea.setFont(new Font("Arial", Font.PLAIN, 18));
        cartArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(cartArea);
        gbc.gridy = 5;
        panel.add(scrollPane, gbc);

        JButton generateBillButton = new JButton("Generate Bill");
        generateBillButton.setFont(new Font("Arial", Font.PLAIN, 18));
        generateBillButton.addActionListener(e -> createBillPage());
        gbc.gridy = 6;
        panel.add(generateBillButton, gbc);

        JButton viewAllBillsButton = new JButton("View All Bills");
        viewAllBillsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        viewAllBillsButton.addActionListener(e -> viewAllBills());
        gbc.gridy = 7;
        panel.add(viewAllBillsButton, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void filterItems() {
        String searchText = searchField.getText().toLowerCase();
        itemComboBox.removeAllItems();
        for (String item : itemPriceMap.keySet()) {
            if (item.toLowerCase().contains(searchText)) {
                itemComboBox.addItem(item);
            }
        }
    }

    private void updatePriceField() {
        String selectedItem = (String) itemComboBox.getSelectedItem();
        if (selectedItem != null) {
            double price = itemPriceMap.get(selectedItem);
            priceField.setText(String.format("%.2f", price));
        }
    }

    private void addToCart() {
        String selectedItem = (String) itemComboBox.getSelectedItem();
        if (selectedItem == null) return;

        String quantityText = quantityField.getText();
        if (quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter quantity.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            double price = itemPriceMap.get(selectedItem);
            double totalPrice = quantity * price;

            cart.add(new Item(selectedItem, quantity, price, totalPrice));
            totalAmount += totalPrice;
            cartArea.append(String.format("%s\t%d\t₹%.2f\t₹%.2f\n", selectedItem, quantity, price, totalPrice));

            quantityField.setText("1"); // Reset quantity to default
            priceField.setText(String.format("%.2f", price));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid quantity.");
        }
    }

    private void createBillPage() {
        String name = JOptionPane.showInputDialog(frame, "Enter Customer Name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Customer name cannot be empty.");
            return;
        }

        String phoneNumber = JOptionPane.showInputDialog(frame, "Enter Phone Number:");
        if (phoneNumber == null || phoneNumber.trim().isEmpty() || !phoneNumber.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(frame, "Invalid phone number. It should be 10 digits.");
            return;
        }

        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel panel = createBackgroundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;

        JTextArea billArea = new JTextArea(20, 40);
        billArea.setFont(new Font("Arial", Font.PLAIN, 18));
        billArea.setEditable(false);

        StringBuilder billDetails = new StringBuilder();
        billDetails.append("Arun's Grocery Store\n");
        billDetails.append("AGS\n\n");
        billDetails.append(String.format("Customer: %s\nPhone: %s\n\n", name, phoneNumber));
        billDetails.append(String.format("Date: %s\n\n", new java.util.Date()));
        billDetails.append("Item\tQuantity\tPrice\tTotal\n");
        billDetails.append("----------------------------------------\n");

        for (Item item : cart) {
            billDetails.append(String.format("%s\t%d\t₹%.2f\t₹%.2f\n", item.getName(), item.getQuantity(), item.getPrice(), item.getTotalPrice()));
        }

        billDetails.append("----------------------------------------\n");
        billDetails.append(String.format("Total Amount: ₹%.2f\n", totalAmount));

        billArea.setText(billDetails.toString());

        JButton printButton = new JButton("Print Bill");
        printButton.setFont(new Font("Arial", Font.PLAIN, 18));
        printButton.addActionListener(e -> printBill(billArea));

        JButton saveButton = new JButton("Save Bill");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 18));
        saveButton.addActionListener(e -> saveBill(name, phoneNumber, billDetails.toString()));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JScrollPane(billArea), gbc);

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.addActionListener(e -> createEnterItemsPage());

        gbc.gridy = 2;
        panel.add(backButton, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void printBill(JTextArea billArea) {
        try {
            billArea.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    private void saveBill(String customerName, String mobileNumber, String billDetails) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Bills (customerName, mobileNumber, totalAmount, billDetails) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, customerName);
            pstmt.setString(2, mobileNumber);
            pstmt.setDouble(3, totalAmount);
            pstmt.setString(4, billDetails);
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(frame, "Bill saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cart.clear();
        totalAmount = 0;
        cartArea.setText("");
    }

    private void viewAllBills() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel panel = createBackgroundPanel();
        panel.setLayout(new BorderLayout());

        JTextArea billsArea = new JTextArea(20, 60);
        billsArea.setFont(new Font("Arial", Font.PLAIN, 18));
        billsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(billsArea);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Bills");
            StringBuilder bills = new StringBuilder();
            while (rs.next()) {
                bills.append("ID: ").append(rs.getInt("id")).append("\n");
                bills.append("Customer Name: ").append(rs.getString("customerName")).append("\n");
                bills.append("Phone Number: ").append(rs.getString("mobileNumber")).append("\n");
                bills.append("Total Amount: ₹").append(rs.getDouble("totalAmount")).append("\n");
                bills.append("Details:\n").append(rs.getString("billDetails")).append("\n");
                bills.append("----------------------------------------------------\n");
            }
            billsArea.setText(bills.toString());
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.addActionListener(e -> createEnterItemsPage());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MarketBillGenerator::new);
    }
}

class Item {
    private final String name;
    private final int quantity;
    private final double price;
    private final double totalPrice;

    public Item(String name, int quantity, double price, double totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
