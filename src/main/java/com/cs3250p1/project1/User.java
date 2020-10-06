package com.cs3250p1.project1;

public class User {

    protected String user_ID;
    protected String first_name;
    protected String last_name;
    protected String email;
    protected String adress_line1;
    protected String adress_line2;
    protected String adress_line3;

 
    public User() {
    }
 
    public User(String user_ID) {
        this.user_ID = user_ID;
    }
 
    public User(String user_ID, String first_name, String last_name, String email, String adress_line1,
                    String adress_line2, String adress_line3) {
       

        this(first_name,last_name,email,adress_line1, adress_line2,adress_line3);
        this.user_ID = user_ID;
    }
     
    public User(String first_name, String last_name, String email, String adress_line1,
                String adress_line2, String adress_line3) {

       

        this.first_name= first_name;
        this.last_name = last_name;
        this.email = email;
        this.adress_line1 = adress_line1;
        this.adress_line2 = adress_line2;
        this.adress_line3 = adress_line3;
    }
 
    public String getuserId() {
        return user_ID;
    }
 
    public void setId(String user_id) {
        this.user_ID = user_id;
    }
 
    public String getfirst_Name() {
        return first_name;
    }
 
    public void setfirst_Name(String first_name) {
        this.first_name = first_name;
    }
 
    public String getlast_Name() {
        return last_name;
    }
 
    public void setlast_Name(String last_name) {
        this.last_name = last_name;
    }
 
    public String getadress_line1() {
        return adress_line1;
    }
 
    public void setadress_line1(String adress_line1) {
        this.adress_line1 = adress_line1;
    }

    public String getadress_line2 (){
        return adress_line2;
    }

    public void setadress_line2(String adress_line2){
        this.adress_line2 = adress_line2;
    }

    public String getaddress_line3(String adress_line3){
        return adress_line3;
    }

    public void setadress_line3(String adress_line3){
        this.adress_line3 = adress_line3;
    }
    
}
