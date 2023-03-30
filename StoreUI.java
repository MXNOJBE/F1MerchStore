import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StoreUI extends JFrame {

    private StoreController storeController;
    private final DefaultTableModel model;
    private final JTextField quantityField;
    private final JLabel totalLabel;

    public StoreUI() {
        super("Formula 1 Store");

        try {
            storeController = new StoreController();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JPanel productsPanel = new JPanel(new GridLayout(0, 3));
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        List<Product> products = null;
        try {
            products = storeController.getAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Product product : products) {
            JPanel productPanel = new JPanel(new BorderLayout());
            productsPanel.add(productPanel);

            JLabel nameLabel = new JLabel(product.getName());
            productPanel.add(nameLabel, BorderLayout.NORTH);

            JLabel priceLabel = new JLabel(String.format("$%.2f", product.getPrice()));
            productPanel.add(priceLabel, BorderLayout.SOUTH);

            JButton addToCartButton = new JButton("Add to cart");
            addToCartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String quantityString = quantityField.getText();
                    if (quantityString.isEmpty()) {
                        // handle empty input, e.g. show an error message
                        return;
                    }
                    int quantity = Integer.parseInt(quantityString);
                    for (int i = 0; i < quantity; i++) {
                        storeController.addToCart(product);
                    }
                    updateCart();
                }
            });

            productPanel.add(addToCartButton, BorderLayout.CENTER);
        }

        JPanel cartPanel = new JPanel(new BorderLayout());
        mainPanel.add(cartPanel, BorderLayout.EAST);

        model = new DefaultTableModel(new String[] {"Name", "Price"}, 0);
        JTable cartTable = new JTable(model);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel checkoutPanel = new JPanel(new FlowLayout());
        cartPanel.add(checkoutPanel, BorderLayout.SOUTH);

        JLabel quantityLabel = new JLabel("Quantity:");
        checkoutPanel.add(quantityLabel);

        quantityField = new JTextField(5);
        checkoutPanel.add(quantityField);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeController.checkout();
                updateCart();
            }
        });
        checkoutPanel.add(checkoutButton);

        totalLabel = new JLabel();
        checkoutPanel.add(totalLabel);

        updateCart();
    }

    private void updateCart() {
        List<Product> cart = storeController.getCart();
        model.setRowCount(0);
        double total = 0;
        for (Product product : cart) {
            model.addRow(new Object[] {product.getName(), product.getPrice()});
            total += product.getPrice();
        }
        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    public static void main(String[] args) {
        StoreUI storeUI = new StoreUI();
        storeUI.setVisible(true);
    }
}

