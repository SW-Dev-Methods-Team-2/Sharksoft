package com.cs3250p1.project1;

//import sun.security.util.Password;

public class User {

    protected String user_ID;
    protected String first_name;
    protected String last_name;
    protected String email;
    protected String address_line1;
    protected String address_line2;
    protected String address_line3;
    protected String password;

 
    public User() {
    }
 
    public User(String user_ID) {
        this.user_ID = user_ID;
    }
 
    public User(String user_ID, String password, String first_name, String last_name, String email, String address_line1,
                    String address_line2, String address_line3) {
       

        this(password, first_name,last_name,email,address_line1, address_line2,address_line3);
        this.user_ID = user_ID;
    }
     
    public User(String password, String first_name, String last_name, String email, String address_line1,
                String address_line2, String address_line3) {

       

        this.first_name= first_name;
        this.last_name = last_name;
        this.email = email;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.address_line3 = address_line3;
    }
 
    public String getuserId() {
        return user_ID;
    }
 
    public void setId(String user_id) {
        this.user_ID = user_id;
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
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
 
    public String getaddress_line1() {
        return address_line1;
    }
 
    public void setaddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getaddress_line2 (){
        return address_line2;
    }

    public void setaddress_line2(String address_line2){
        this.address_line2 = address_line2;
    }

    public String getaddress_line3(){
        return address_line3;
    }

    public void setaddress_line3(String address_line3){
        this.address_line3 = address_line3;
    }
    
}
