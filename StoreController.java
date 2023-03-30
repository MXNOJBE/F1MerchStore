import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreController {
    private ProductDAO productDAO;
    private List<Product> cart = new ArrayList<>();

    public StoreController() throws SQLException {
        productDAO = new ProductDAO();
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public void addToCart(Product product) {
        cart.add(product);
    }

    public List<Product> getCart() {
        return cart;
    }

    public void checkout() {
        cart.clear();
    }
}
