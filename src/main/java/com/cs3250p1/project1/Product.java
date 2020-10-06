public class Product {
    protected String id;
    protected double wholesale_cost;
    protected double sale_price;
    protected int quantity;
    protected String supplier_id;
 
    public Product() {
    }
 
    public Product(String id) {
        this.id = id;
    }
 
    public Product(String id, int quantity, double wholesale_cost, double sale_price,  String supplier_id) {
        this(quantity, wholesale_cost, sale_price, supplier_id);
        this.id = id;
    }
     
    public Product(int quantity, double wholesale_cost, double sale_price, String supplier_id) {
        this.wholesale_cost = wholesale_cost;
        this.sale_price = sale_price;
        this.quantity = quantity;
        this.supplier_id = supplier_id;
    }
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public double getwholesale_cost() {
        return wholsale_cost;
    }
 
    public void setwholesale_cost(int wholesale_cost) {
        this.wholesale_cost = wholesale_cost;
    }
 
    public double getsale_price() {
        return sale_price;
    }
 
    public void setsale_price(int sale_price) {
        this.sale_price = sale_price;
    }
 
    public int getquantity() {
        return quantity;
    }
 
    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    public String getsupplier_id (){
        return supplier_id;
    }

    public void setsupplier_id(String supplier_id){
        this.supplier_id = supplier_id;
    }
}
