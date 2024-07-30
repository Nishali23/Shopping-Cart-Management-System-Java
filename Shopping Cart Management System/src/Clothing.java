public class Clothing extends Product{

    private String size;
    private String color;
    private String category;

    public Clothing(String productId,String productName,int availableItems,double price,String size,String color,String category){
        super(productId,productName,availableItems,price);
        this.size=size;
        this.color=color;
        this.category=category;
    }
    public String getSize(){
        return size;
    }
    public String getColor(){
        return color;
    }
    public void setSize(String size){
        this.size=size;
    }
    public void setColor(String color){
        this.color=color;
    }

    @Override
    public String toString(){
        return super.toString()+
                "Size: "+size+" , "+
                "Color: "+color;
    }
}
