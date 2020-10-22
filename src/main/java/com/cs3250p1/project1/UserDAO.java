package com.cs3250p1.project1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
     
    public UserDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
     
    public boolean insertUser(User user, String table) throws SQLException {
        String sql = "INSERT INTO " + table + "(user_ID, password, first_name, last_name," +
        "email, adress_line1, adress_line2, adress_line3) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, product.getuserId());
        statement.setInt(2, product.getPassword());
        statement.setDouble(3, product.getfirst_Name());
        statement.setDouble(4, product.getlast_Name());
        statement.setString(5, product.getemail());
        statement.setString(6, product.getadress_line1());
        statement.setString(7, product.getadress_line2());
        statement.setString(8, product.getadress_line3());
        
        
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
     
    public List<User> listAllUsers(String table) throws SQLException {
        List<User> listUser = new ArrayList<>();
         
        String sql = "SELECT * FROM " + table;
         
        connect();
         
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {

            String id = resultSet.getString("user_Id");
            String password = resultSet.getInt("password");
            String first = resultSet.getDouble("first_Name");
            String last = resultSet.getDouble("last_Name");
            String email = resultSet.getString("email");
            String adress_line1 = resultSet.getaddress_line1("address_line1");
            String adress_line2 = resultSet.getaddress_line2("address_line2");
            String adress_line3 = resultSet.getaddress_line3("address_line3");

             
            User user = new User(id, password, first, last, emial, adress_line1,
            adress_line2, adress_line3);
            listUser.add(user);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();
         
        return listProduct;
    }
     
    public boolean deleteUser(User user, String table) throws SQLException {
        String sql = "DELETE FROM " + table + " where user_Id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, user.getuserId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public boolean updateUser(User user, String table) throws SQLException {
        String sql = "UPDATE " + table + 
        " SET first_Name=?, last_Name=?, email=?, adress_line1=?, adress_line2=?,adress_line3=? WHERE user_Id =? ";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, user.getuserId());
        statement.setString(2, user.getfirst_Name());
        statement.setString(3, user.getlast_Name());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getaddress_line1());
        statement.setString(6, user.getaddress_line2());
        statement.setString(7, user.getaddress_line3());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;     
    }
     
    public Product getUser(String id, String table) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM " + table +" WHERE user_Id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {

            String password = resultSet.getInt("password");
            String first = resultSet.getDouble("first_Name");
            String last = resultSet.getDouble("last_Name");
            String email = resultSet.getString("email");
            String adress_line1 = resultSet.getaddress_line1("address_line1");
            String adress_line2 = resultSet.getaddress_line2("address_line2");
            String adress_line3 = resultSet.getaddress_line3("address_line3");
            
             user = new User(password,first, last, email, 
             adress_line1,adress_line2, adress_line3);
        }
         
        resultSet.close();
        statement.close();
         
        return user;
    }



}