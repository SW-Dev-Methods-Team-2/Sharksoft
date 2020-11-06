package com.cs3250p1.project1;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class OrderDAO {
    
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
     
    public OrderDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
     
    protected void connect() throws SQLException {

       
        
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            if (conn != null) {
                //report status to the status bar in the main frame
                System.out.println("Status: ******CONNECTING******");
                System.out.println("Status: Connected to database");
            }
            }  catch (
                SQLException ex) {
            ex.printStackTrace();
            }
            jdbcConnection = DriverManager.getConnection(
                                        jdbcURL, jdbcUsername, jdbcPassword);
        }
    }
     
    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
     
    public boolean insertOrder(SalesOrder order, String table) throws SQLException {
        String sql = "INSERT INTO " + table + "(email, shipping_address, product_id, quantity, date_) VALUES (?, ?, ?, ?, ?)";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, order.getEmail());
        statement.setString(2, order.getShippingA());
        statement.setString(3, order.getId());
        statement.setInt(4, order.getquantity());
        statement.setString(5, order.getDate() );
       
        
        
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
     
    public List<SalesOrder> listAllOrders(String table) throws SQLException {
        List<SalesOrder> listOrders = new ArrayList<>();
         
        String sql = "SELECT * FROM " + table;
         
        connect();
         
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {

            String id = resultSet.getString("product_id");
            int quantity = resultSet.getInt("quantiy");
            String email = resultSet.getString("email");
            String date = resultSet.getString("date");
            String supplier_id = resultSet.getString("supplier_id");
             
            SalesOrder order = new SalesOrder(id, quantity, email, date, supplier_id);
            listOrders.add(order);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();
         
        return listOrders;
    }
     
    public boolean deleteOrder(SalesOrder order, String table) throws SQLException {
        String sql = "DELETE FROM " + table + " where product_id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, order.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public boolean updateOrder(SalesOrder order, String table) throws SQLException {
        String sql = "UPDATE " + table + " SET quantity=?, email=?, date=?, supplier_id=? WHERE product_id =? ";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, order.getId());
        statement.setInt(2, order.getquantity());
        statement.setString(3, order.getEmail());
        statement.setString(4, order.getDate());
        statement.setString(5, order.getsupplier_id());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;     
    }
     
    public SalesOrder getOrder(String id, String table) throws SQLException {
        SalesOrder order = null;
        String sql = "SELECT * FROM " + table +" WHERE product_id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {

            int quantity = resultSet.getInt("quantity");
            String email = resultSet.getString("email");
            String date = resultSet.getString("date");
            String supplier_id = resultSet.getString("supplier_id");
            
             order = new SalesOrder(quantity, email, date,supplier_id );
        }
         
        resultSet.close();
        statement.close();
         
        return order;
    }

    



}