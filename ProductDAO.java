import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection connection;

    public ProductDAO() throws SQLException {
        connection = Database.getConnection();
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        String query = "SELECT id, name, price, description, image_path FROM products";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String imagePath = rs.getString("image_path");
                Product product = new Product(id, name, price, description, imagePath);
                products.add(product);
            }
        }

        return products;
    }

    public Product getProductById(int id) throws SQLException {
        Product product = null;

        String query = "SELECT name, price, description, image_path FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imagePath = rs.getString("image_path");
                    product = new Product(id, name, price, description, imagePath);
                }
            }
        }

        return product;
    }
}
