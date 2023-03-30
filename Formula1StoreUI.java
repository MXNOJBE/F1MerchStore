import javax.swing.*;
import java.awt.*;

public class Formula1StoreUI extends JFrame {

    private JPanel mainPanel;
    private JLabel titleLabel;
    private JList<String> productList;
    private JButton addToCartButton;

    public Formula1StoreUI() {
        super("Formula 1 Store");

        // Initialize UI components
        mainPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("Formula 1 Store");
        productList = new JList<>(new String[] {"T-Shirt", "Cap", "Jacket"});
        addToCartButton = new JButton("Add to Cart");

        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(productList, BorderLayout.CENTER);
        mainPanel.add(addToCartButton, BorderLayout.SOUTH);

        // Add main panel to frame
        setContentPane(mainPanel);

        // Set frame properties
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Formula1StoreUI();
    }
}