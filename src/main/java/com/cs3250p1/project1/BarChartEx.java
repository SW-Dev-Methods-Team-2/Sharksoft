package com.cs3250p1.project1;

/**
 * @author Noel Corrales
 * Dont forget to add the jfree library files into the project first
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartEx extends JFrame{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    BarChartEx() {
        final GraphPanel gPanel = new GraphPanel();
		gPanel.create();
		JButton button = new JButton(new AbstractAction("Update") {
			/**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
			public void actionPerformed(ActionEvent e) {
				String a = JOptionPane.showInputDialog(rootPane, "ENTER DATE IN yyyy-mm-dd", "", JOptionPane.PLAIN_MESSAGE);
				
				gPanel.update(a);
			}
		});
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(gPanel, BorderLayout.CENTER);
		this.add(button, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
        
    }

    private static class GraphPanel extends JPanel{
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private DefaultCategoryDataset gData = new DefaultCategoryDataset();

		void create() {
			update("");
			JFreeChart chart = ChartFactory.createBarChart("", "", "", gData, PlotOrientation.VERTICAL, false, false,
					false);
			ChartPanel chartPanel = new ChartPanel(chart);
			this.add(chartPanel);
        }

		private void update(String date) {
            gData.clear();
            final String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
            final String dbUsername = "admin";
            final String dbPassword = "cs3250db1";
            OrderDAO orders = new OrderDAO(dbURL, dbUsername, dbPassword);
    
            try {
    
                ArrayList<SalesOrder> orderList = orders.orderList("cs3250main.sales_orders", date);
                for (int i = 0; i < orderList.size(); i++) {
                    gData.setValue(orderList.get(i).getquantity(), "quantity sold", orderList.get(i).getId());
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        
    }
   
}
