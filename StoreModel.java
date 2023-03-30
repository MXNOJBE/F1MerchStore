import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreModel {

    private final Connection connection;

    public StoreModel() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/store";
        String user = "root";
        String password = "pass";
        connection = DriverManager.getConnection(url, user, password);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                String imagePath = resultSet.getString("image_url");
                products.add(new Product(id, name, price, description, imagePath));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

}
