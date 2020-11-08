package com.cs3250p1.project1;

public class SupplierOrder {

    
    protected String date; //dates are long so needs double
    protected String supplier_id;
    protected String product_id;
    protected int quantity;
    
    public SupplierOrder() {
    }
     
    public SupplierOrder(String date, String product_id, String supplier_id, int quantity) {
        this.date = date;
        this.product_id= product_id;
        this.supplier_id = supplier_id;
        this.quantity = quantity;
    }

 public String getDate() {
        return date;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
    public String getId() {
        return product_id;
    }
 
    public void setId(String id) {
        this.product_id = id;
    }

    public String getsupplier_id (){
        return supplier_id;
    }

    public void setsupplier_id(String supplier_id){
        this.supplier_id = supplier_id;
    }

    public int getquantity() {
        return quantity;
    }
 
    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

}