import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
    
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
     
    public ProductDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
     
    protected void connect() throws SQLException {

        /*try {

            Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            if (conn != null) {
                //report status to the status bar in the main frame
                System.out.println("Status: ******CONNECTING******");
                System.out.println("Status: Connected to database");
            }
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }*/
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
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
     
    public boolean insertProduct(Product product, String table) throws SQLException {
        String sql = "INSERT INTO " + table + "(product_id, quantity, wholsale_cost, sale_price, suplier_id) VALUES (?, ?, ?)";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, product.getId());
        statement.setInt(2, product.getquantity());
        statement.setDouble(3, product.getwholesale_cost());
        statement.setDouble(4, product.getsale_price());
        statement.setString(5, product.getsupplier_id());
        
         
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
            double wholsale_cost = resultSet.getDouble("wholesale_cost");
            double sale_price = resultSet.getDouble("sale_price");
            String supplier_id = resultSet.getString("supplier_id");
             
            Product product = new Product(id, quantity, wholsale_cost, sale_price, supplier_id);
            listProduct.add(product);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();
         
        return listProduct;
    }
     
    public boolean deleteProduct(Product product, String table) throws SQLException {
        String sql = "DELETE FROM " + table + " where product_id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, product.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public boolean updateProduct(Product product, String table) throws SQLException {
        String sql = "UPDATE " + table + " SET quantity=?, wholsale_cost=?, sale_price=?, suplier_id=? WHERE product_id =? ";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, product.getId());
        statement.setInt(2, product.getquantity());
        statement.setDouble(3, product.getwholesale_cost());
        statement.setDouble(4, product.getsale_price());
        statement.setString(5, product.getsupplier_id());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;     
    }
     
    public Product getProduct(String id, String table) throws SQLException {
        Product product = null;
        String sql = "SELECT * FROM " + table +" WHERE book_id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {

            int quantity = resultSet.getInt("quantity");
            double wholesale_cost = resultSet.getDouble("wholesale_cost");
            double sale_cost = resultSet.getDouble("sale_cost");
            String supplier_id = resultSet.getString("supplier_id");
            
             product = new Product(quantity, wholesale_cost, sale_cost,supplier_id );
        }
         
        resultSet.close();
        statement.close();
         
        return product;
    }



}