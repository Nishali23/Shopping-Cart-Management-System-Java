import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ShoppingCart implements Serializable {
    private Map<Product, Integer> shoppingList = new HashMap<>();


    public void addProduct(Product selectedProduct) {
        shoppingList.put(selectedProduct, shoppingList.getOrDefault(selectedProduct, 0) + 1);
    }

    public Map<Product, Integer> getProducts() {
        return shoppingList;
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : shoppingList.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public double calculateDiscount() {
        Map<String, Integer> categoryCounts = new HashMap<>();

        // Count products in each category
        for (Map.Entry<Product, Integer> entry : shoppingList.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            String category = product instanceof Electronic ? "Electronics" : "Clothing";
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + quantity);
        }

        // Apply discount for categories with at least three products
        for (int count : categoryCounts.values()) {
            if (count >= 3) {
                return calculateTotal() * 0.20; // 20% discount
            }
        }
        return 0;
    }



    public double calculateFinalTotal() {
        return calculateTotal() - calculateDiscount();
    }

    public void clear() {
        shoppingList.clear();
    }
}