import java.io.IOException;
import java.util.List;

public interface ShoppingManager {

    void addProduct(Product product);
    void deleteProduct(String deleteId);
    void printList();
    void saveToFile(String filename) throws IOException;
    void loadFromFile(String filename) throws IOException, ClassNotFoundException;
    void saveUsersToFile(String filename) throws IOException;
    void loadUsersFromFile(String filename) throws IOException, ClassNotFoundException;
    List<Product> getProductList();
    Product getProductById(String key);
    void addUser(User user);
    User getUser(User user);
}


