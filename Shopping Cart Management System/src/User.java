import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {

    private String userName;
    private String passWord;
    private ShoppingCart shoppingCart;
    private boolean isFirstPurchase;

    public User(String userName, String passWord){
        this.userName=userName;
        this.passWord=passWord;
        this.shoppingCart=new ShoppingCart();
        this.isFirstPurchase = true;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassWord(){
        return passWord;
    }
    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }

    public Boolean getIsFirstPurchase(){
        return isFirstPurchase;
    }
    public void setIsFirstPurchase(Boolean isFirstPurchase){
        this.isFirstPurchase=isFirstPurchase;
    }
}
