import java.io.Serializable;
public abstract class Product implements Serializable{

    private String productId;
    private String productName;
    private int availableItems;
    private double price;

    public Product(String productId, String productName, int availableItems, double price) {
        this.productId=productId;
        this.productName=productName;
        this.availableItems=availableItems;
        this.price=price;
    }
    public String getProductId(){
        return productId;
    }
    public String getProductName(){
        return productName;
    }
    public int getAvailableItems(){
        return availableItems;
    }
    public double getPrice(){
        return price;
    }
    public void setProductId(String productId){
        this.productId=productId;
    }
    public void setProductName(String productName){
        this.productName=productName;
    }
    public void setAvailableItems(int availableItems){
        this.availableItems=availableItems;
    }
    public void setPrice(double price){
        this.price=price;
    }

    @Override
    public String toString(){
        return "Product Id: "+productId+" , "+
                "Product Name: "+productName+" , "+
                "Available Items: "+availableItems+" , "+
                "Price: "+price+" , ";
    }

    public String getCategory() {
        return null;
    }

    public String getInfo() {
        return null;
    }
}
