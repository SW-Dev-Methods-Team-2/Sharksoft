package com.cs3250p1.project1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;

public class Barchart {

    public CategoryDataset createDataset(OrderDAO dao) {

        /*final String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
        final String dbUsername = "admin";
        final String dbPassword = "cs3250db1";

        
        OrderDAO orders = new OrderDAO(dbURL, dbUsername, dbPassword);*/
        var dataset = new DefaultCategoryDataset();
        try {

            // we might need to move orderList outside of this class so that we can pass in different tables and dates 
            // that means we will be able to get rid of login credentials on this class 
            // We should eliminate logins from every class to avoid hacking 

            ArrayList<SalesOrder> orderList = dao.orderList("cs3250main.sales_orders" );
            for (int i = 0; i < orderList.size(); i++) {
                dataset.setValue(orderList.get(i).getquantity(), "quantity sold", orderList.get(i).getId());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //implementaion with jdbc thoughts:
        //first we need to order the top ten most purchased products by querying the database.
        //then we set the first result at the top with its quantity, and product id.


        return dataset;
    }
    
}
