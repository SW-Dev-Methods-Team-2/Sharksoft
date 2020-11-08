package com.cs3250p1.project1;

/**
 * @author Noel Coralles
 * Dont forget to add the jfree library files into the project first
 */


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

public class BarChartEx {

    CategoryDataset dataset = createDataset();
    JFreeChart chart = createChart(dataset);
    ChartPanel chartPanel = new ChartPanel(chart);

    BarChartEx() {
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
    }

    ChartPanel getChartPanel() {
        return chartPanel;
    }

    private CategoryDataset createDataset() {

        final String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
        final String dbUsername = "admin";
        final String dbPassword = "cs3250db1";

        var dataset = new DefaultCategoryDataset();
        OrderDAO orders = new OrderDAO(dbURL, dbUsername, dbPassword);

        try {
            ArrayList<SalesOrder> orderList = orders.orderList("cs3250main.sales_orders");
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

    private JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "Most sold product all time",
                "",
                "Quantity",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }
}