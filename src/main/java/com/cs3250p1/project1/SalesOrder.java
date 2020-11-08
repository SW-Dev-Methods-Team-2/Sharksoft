package com.cs3250p1.project1;
import java.util.*;

public class SalesOrder {
    /*protected String id;
    protected double wholesale_cost;
    protected double sale_price;
    protected int quantity;*/

    protected String supplier_id;
    protected String date; //dates are long so needs double
    protected String email;
    protected String shipping_address;
    protected String product_id;
    protected int quantity;
 
    public SalesOrder() {
    }

    public SalesOrder(String id, int quantity){
        this.product_id = id;
        this.quantity = quantity;
    }
    
 
    public SalesOrder(String id) {
        this.product_id = id;
    }
 
    public SalesOrder(String id, int quantity, String email, String date, String supplier_id) {
        this(quantity,  email, date, supplier_id);
        this.product_id = id;
    }
     
    public SalesOrder(int quantity, String email, String date, String supplier_id) {
        this.email = email;
        this.date = date;
        this.quantity = quantity;
        this.supplier_id = supplier_id;
    }
 
    public String getId() {
        return product_id;
    }
 
    public void setId(String id) {
        this.product_id = id;
    }
 
    public String getDate() {
        return date;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setemail(String email) {
        this.email = email;
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

    public String getShippingA(){
        return shipping_address;
    }

    public String setShippingA(String shipping_adress){
       return this.shipping_address = shipping_adress;
    }
}
