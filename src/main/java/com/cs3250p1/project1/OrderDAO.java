package com.cs3250p1.project1;
import java.sql.Connection;
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
        String sql = "INSERT INTO " + table + "(product_id, quantity, wholesale_cost, sale_price, supplier_id) VALUES (?, ?, ?, ?, ?)";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, order.getId());
        statement.setInt(2, order.getquantity());
        statement.setDouble(3, order.getwholesale_cost());
        statement.setDouble(4, order.getsale_price());
        statement.setString(5, order.getsupplier_id());
        
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
     
    public List<Product> listAllProducts(String table) throws SQLException {
        List<Product> listProduct = new ArrayList<>();
         
        String sql = "SELECT * FROM " + table;
         
        connect();
         
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {

            String id = resultSet.getString("product_id");
            int quantity = resultSet.getInt("quantiy");
            double wholesale_cost = resultSet.getDouble("wholesale_cost");
            double sale_price = resultSet.getDouble("sale_price");
            String supplier_id = resultSet.getString("supplier_id");
             
            Product product = new Product(id, quantity, wholesale_cost, sale_price, supplier_id);
            listProduct.add(product);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();
         
        return listProduct;
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
        String sql = "UPDATE " + table + " SET quantity=?, wholesale_cost=?, sale_price=?, supplier_id=? WHERE product_id =? ";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, order.getId());
        statement.setInt(2, order.getquantity());
        statement.setDouble(3, order.getwholesale_cost());
        statement.setDouble(4, order.getsale_price());
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
            double wholesale_cost = resultSet.getDouble("wholesale_cost");
            double sale_cost = resultSet.getDouble("sale_cost");
            String supplier_id = resultSet.getString("supplier_id");
            
             order = new SalesOrder(quantity, wholesale_cost, sale_cost,supplier_id );
        }
         
        resultSet.close();
        statement.close();
         
        return order;
    }



}