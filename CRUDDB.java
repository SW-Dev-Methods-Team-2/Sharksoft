//Author: Noel Corrales
//Version 1.1
//test commit


import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;

public class CRUDDB
{


    public static String addProduct(Connection conn) throws SQLException {
        String print="";
        String sql = "INSERT INTO cs3250main.sharktable (product_id, quantity, wholesale_cost, sale_price, supplier_id) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, "FDSDKE57465EWDFG");
        statement.setString(2, "55555");
        statement.setString(3, "550.5");
        statement.setString(4, "560.8");
        statement.setString(5,"QWERTYUI");

        int rows = statement.executeUpdate();
        if (rows > 0){
            print+="PRODUCT ADDED<br>";
        }
        return print;
    }

    public static String select(Connection conn) throws SQLException {
        String print="";
        String selectSql = "SELECT * FROM cs3250main.sharktable";
        Statement selectStatement = conn.createStatement();
        ResultSet result = selectStatement.executeQuery(selectSql);

        int count = 0;
        while (result.next()){
            String pId = result.getString("product_id");
            String quan = result.getString("quantity");
            String wCost = result.getString("wholesale_cost");
            String sPrice = result.getString("sale_price");
            String sId = result.getString("supplier_id");

            String output = "product: %s- %s- %s- %s- %s<br>";
            print+=String.format(output, count++, pId, quan, wCost, sPrice, sId);

        }
        return print;
    }

    public static String update(Connection conn) throws SQLException{
        String print="";
        String updateSQL = "UPDATE cs3250main.sharktable SET quantity=?, wholesale_cost=?, sale_price=?, supplier_id=? WHERE product_id =? ";
        PreparedStatement statement3 = conn.prepareStatement(updateSQL);
        statement3.setString(1,"5344");
        statement3.setString(2, "54.0");
        statement3.setString(3,"55.0");
        statement3.setString(4,"qwerty78");
        statement3.setString(5,"FDSDKE57465EWDFG");

        int rowsUpdated = statement3.executeUpdate();
        if(rowsUpdated>0){
            print+="An existing product updated successfully<br>";
        }
        return print;
    }

    public static String delete(Connection conn) throws SQLException{
        String print="";
        String deleteSql = "DELETE FROM cs3250main.sharktable WHERE product_id=?";
        PreparedStatement statement4 = conn.prepareStatement(deleteSql);
        statement4.setString(1,"FDSDKE57465EWDFG");
        int rowsDeleted = statement4.executeUpdate();
        if(rowsDeleted > 0 ){
            print+="product Deleted successfully<br>";
        }
        else
        {
            print+="Product not found...<br>";
        }
        return print;
    }


}// end class
