public class Electronic extends Product{

    private String brand;
    private String warrantyPeriod;

    private String category;

    public Electronic(String productId,String productName,int availableItems,double price,String brand,String warrantyPeriod,String category){
        super(productId,productName,availableItems,price);
        this.brand=brand;
        this.warrantyPeriod=warrantyPeriod;
        this.category=category;
    }
    public String getBrand(){
        return brand;
    }
    public String getWarrantyPeriod(){
        return warrantyPeriod;
    }
    public void setBrand(String brand){
        this.brand=brand;
    }
    public void setWarrantyPeriod(String warrantyPeriod){
        this.warrantyPeriod=warrantyPeriod;
    }

    @Override
    public String toString(){
        return super.toString()+
                "Brand: "+brand+" , "+
                "Warranty Period: "+warrantyPeriod;
    }
}
