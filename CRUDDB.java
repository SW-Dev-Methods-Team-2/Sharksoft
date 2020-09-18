import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;

public class CRUDDB
{

    public static void main(String[] args){

        String dbURL = "jdbc:mysql://localhost:3306/cs3250";
        String dbUsername = "root";
        String dbPassword = "Colorado129#";


        Scanner scan = new Scanner(System.in);

        try {

            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            if (conn != null) {
                System.out.println("Connected to database");

            }
            System.out.println("Press 'c' to create product, 'r' to read, 'u' to update, 'd' to delete");
            String answer = scan.nextLine();

            if(answer.equalsIgnoreCase("c")){


                addProduct(conn);
            }
            else if(answer.equalsIgnoreCase("r")){
                select(conn);
            }
            else if(answer.equalsIgnoreCase("u")){
                update(conn);
            }
            else if(answer.equalsIgnoreCase("d")){
                delete(conn);
            }


            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }//end main

    public static void addProduct(Connection conn) throws SQLException {
        String sql = "INSERT INTO dbdb (product_id, quantity, wholesale_cost, sale_price, supplier_id) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, "FDSDKE57465EWDFG");
        statement.setString(2, "55555");
        statement.setString(3, "550.5");
        statement.setString(4, "560.8");
        statement.setString(5,"QWERTYUI");

        int rows = statement.executeUpdate();
        if (rows > 0){
            System.out.println("PRODUCT ADDED");
        }
    }

    public static void select(Connection conn) throws SQLException {
        String selectSql = "SELECT * FROM dbdb";
        Statement selectStatement = conn.createStatement();
        ResultSet result = selectStatement.executeQuery(selectSql);

        int count = 0;
        while (result.next()){
            String pId = result.getString("product_id");
            String quan = result.getString("quantity");
            String wCost = result.getString("wholesale_cost");
            String sPrice = result.getString("sale_price");
            String sId = result.getString("supplier_id");

            String output = "product #%d: %s - %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, pId, quan, wCost, sPrice, sId));
        }
    }

    public static void update(Connection conn) throws SQLException{
        String updateSQL = "UPDATE dbdb SET product_id =?, quantity=?, wholesale_cost=?, sale_price=? WHERE supplier_id=?";
        PreparedStatement statement3 = conn.prepareStatement(updateSQL);
        statement3.setString(1,"qwerty78");
        statement3.setString(2, "5344");
        statement3.setString(3,"55.0");
        statement3.setString(4,"35.0");
        statement3.setString(5,"FDSDKE57465EWDFG");

        int rowsUpdated = statement3.executeUpdate();
        if(rowsUpdated>0){
            System.out.println("An existing product updated successfully");
        }
    }

    public static void delete(Connection conn) throws SQLException{
        String deleteSql = "DELETE FROM dbdb WHERE product_id=?";
        PreparedStatement statement4 = conn.prepareStatement(deleteSql);
        statement4.setString(1,"FDSDKE57465EWDFG");
        int rowsDeleted = statement4.executeUpdate();
        if(rowsDeleted > 0 ){
            System.out.println("product Deleted successfully");
        }
    }


}// end class