import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WestminsterShoppingManager implements ShoppingManager {
    private static ArrayList<Product> productList = new ArrayList<>();
    private static ArrayList<User> userList = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        int items = 0;
        for(Product j : productList) {
            items += j.getAvailableItems();
        }
        int count = items + product.getAvailableItems();

        int space = 50 - items;

        if(count > 50) {
            System.out.println("Storage is full. You can add only " + space + " items.");
        } else {
            productList.add(product);
            System.out.println("Product is added successfully.");
        }
    }

    @Override
    public void deleteProduct(String deleteId) {
        boolean productFound = false;
        Iterator<Product> iterator = productList.iterator();

        while(iterator.hasNext()) {
            Product product = iterator.next();
            if(deleteId.equals(product.getProductId())) {
                iterator.remove();
                System.out.println("Deleted product successfully.");
                productFound = true;
                break;
            }
        }
        if(!productFound) {
            System.out.println("Product cannot be found.");
        }
    }

    @Override
    public void printList() {
        if(productList.isEmpty()) {
            System.out.println("The product list is empty.");
        } else {
            for(Product product : productList) {
                System.out.println(product.toString());
            }
        }
    }

    @Override
    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(productList);
            System.out.println("Product list saved to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving to file: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            productList = (ArrayList<Product>) in.readObject();
            System.out.println("Product list loaded from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while loading from file: " + e.getMessage());
        }
    }

    @Override
    public void saveUsersToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(userList);
            System.out.println("User list saved to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving to file: " + e.getMessage());
        }
    }

    @Override
    public void loadUsersFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            userList = (ArrayList<User>) in.readObject();
            System.out.println("User list loaded from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while loading from file: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Product getProductById(String key) {
        for (Product product : productList) {
            if (product.getProductId().equals(key)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        userList.add(user);
    }

    @Override
    public User getUser(User user) {
        if (userList.isEmpty()) {
            return null;
        } else {
            for (User i : userList) {
                if (i.getUserName().equals(user.getUserName())) {
                    return i;  // Return the matching user
                }
            }
            return null;  // Return null if no matching user is found
        }
    }
}
