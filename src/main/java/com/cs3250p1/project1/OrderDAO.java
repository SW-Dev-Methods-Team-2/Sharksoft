package com.cs3250p1.project1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import org.springframework.util.SystemPropertyUtils;


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
            System.out.println("Disconnected");
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
        statement.setTimestamp(5, order.getDate() );
   
         
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
            Timestamp date = resultSet.getTimestamp("date_");
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
        String sql = "UPDATE " + table + " SET quantity=?, email=?, date_=?, supplier_id=? WHERE product_id =? ";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, order.getId());
        statement.setInt(2, order.getquantity());
        statement.setString(3, order.getEmail());
        statement.setTimestamp(4, order.getDate());
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
            Timestamp date = resultSet.getTimestamp("date_");
            String supplier_id = resultSet.getString("supplier_id");
            
             order = new SalesOrder(quantity, email, date,supplier_id );
        }
         
        resultSet.close();
        statement.close();
         
        return order;
    }

    public ArrayList<SalesOrder> orderList(String table, String date) throws SQLException {
        ArrayList<SalesOrder> orderArray = new ArrayList<SalesOrder>();

        String print ="";
        String sql = "SELECT SUM(quantity) AS quantity, product_id AS product_id FROM cs3250main.sales_orders WHERE DATE(date_)='" + date+ "' GROUP BY product_id ORDER BY quantity DESC LIMIT 10";
         
        connect();
         
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
       
        while (resultSet.next()) {

            String id = resultSet.getString("product_id");
            System.out.print(id + " ");
            int quantity = resultSet.getInt("quantity");
            System.out.println(quantity);
            //String email = resultSet.getString("email");
           // Timestamp date = resultSet.getTimestamp("date_");
           // System.out.println(date);
           // String supplier_id = resultSet.getString("supplier_id");
           
            SalesOrder order = new SalesOrder(id, quantity);

            
            print+= "" + id + " " + quantity;
            orderArray.add(order);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();



        return orderArray;

    }

    public boolean insertBatch(List<SalesOrder> ordersList, String table) throws SQLException {

        String sql = "INSERT INTO " + table + "(email, shipping_address, product_id, quantity, date_) VALUES (?, ?, ?, ?, ?)";
        connect();
        int count = 0;
        int batchSize = 1000;
        boolean rowInserted = false;
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        jdbcConnection.setAutoCommit(false);
        try{
            
            for(int i =1; i < ordersList.size(); i++){
                statement.setString(1, ordersList.get(i).getEmail());
                statement.setString(2, ordersList.get(i).getShippingA());
                statement.setString(3, ordersList.get(i).getId());
                statement.setInt(4, ordersList.get(i).getquantity());
                statement.setTimestamp(5, ordersList.get(i).getDate() );
                statement.addBatch();
                
                if(++count % batchSize == 0){
                    System.out.println("Commit the batch");
                    int [] result = statement.executeBatch();
                    System.out.println("Number of rows inserted: "+ result.length);
                                    jdbcConnection.commit();
                }
            }
            int [] remaining = statement.executeBatch();
            System.out.println("The number of rows inserted:" + remaining.length);
            jdbcConnection.commit();
            rowInserted = true;
        }catch(SQLException x){
            x.printStackTrace();
            jdbcConnection.rollback();
        }finally{
            if(statement != null){
                statement.close();
            }
        }
       
        disconnect();
        return rowInserted;
       
    }

    public int countOrders(String table, String date) throws SQLException{
        
         
        String sql = "select count(*) as total from "+ table+ " where date(date_) = '" +date + "'";
         
        connect();
         
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         int count = 0;
        while(resultSet.next()){
            count = resultSet.getInt("total");
        }
        
         
        resultSet.close();
        statement.close();
        
        disconnect();
        return count;
    }
    

    

}