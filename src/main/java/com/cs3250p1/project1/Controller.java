package com.cs3250p1.project1;

import java.util.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
//import java.sql.*;

public class Controller {

    static Scanner scan = new Scanner(System.in);
    static Scanner scan1 = new Scanner (System.in);

    public static void main(String[] args) {

        final String jdbcURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
        final String jdbcUsername = "admin";
        final String jdbcPassword = "cs3250db1";
        final String sharktabkle = "cs3250main.sharktable";

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        String dates = dateFormat.format(date);
        System.out.println(dates);

        /*ProductDAO p1 = new ProductDAO(jdbcURL, jdbcUsername, jdbcPassword);
        Product pro1 = createProduct();

        try {
            p1.updateProduct(pro1, sharktabkle);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        */


    }
    public static Product createProduct(){
        System.out.println("Eneter product id");
        String pId = scan.nextLine();
        System.out.println("Enter quantity");
        int quan = scan.nextInt();
        System.out.println("Enter wholesale cost");
        double wCost = scan.nextDouble();
        System.out.println("Enter sale cost");
        double sCost = scan.nextDouble();
        System.out.println("Enter supplier ID");
        String sID = scan1.nextLine();

        Product p1 = new Product(pId,quan,wCost,sCost,sID);

        return p1;



    }
    
}