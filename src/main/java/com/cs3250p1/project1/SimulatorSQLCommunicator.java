package com.cs3250p1.project1;

//Author: Noel Corrales
//Version 1.1
//test commit


import java.sql.*;
import java.sql.Statement;

public class SimulatorSQLCommunicator
{

    static String table="";
    public SimulatorSQLCommunicator(String _table){ //CRUDDB is used in many places with their own table, so
        //specify destination table during construction of object
        table = _table;
    }

    public String addProduct(Connection conn,
                                    String _productId,
                                    String _quantity,
                                    String _wholesaleCost,
                                    String _salePrice,
                                    String _supplierId) throws SQLException {
        String print="";
        if(_productId.equals("") || _quantity.equals("") || _wholesaleCost.equals("")
        || _salePrice.equals("")
        || _supplierId.equals("")){// this is so that empty forms won't crash this function
            return print;
        }

        String sql = "INSERT INTO cs3250main."+table+" (product_id, quantity, wholesale_cost, sale_price, supplier_id) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, _productId);
        statement.setString(2, _quantity);
        statement.setString(3, _wholesaleCost);
        statement.setString(4, _salePrice);
        statement.setString(5,_supplierId);

        int rows = statement.executeUpdate();
        if (rows > 0){
            print+="PRODUCT ADDED<br>";
        }
        return print;
    }

    public String select(Connection conn) throws SQLException { //wait for noel, add product
        // name identifier to search for specific product
        String print="";
        String selectSql = "SELECT * FROM cs3250main."+table;
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

    public String update(Connection conn, String _productId,
                                String _quantity,
                                String _wholesaleCost,
                                String _salePrice,
                                String _supplierId) throws SQLException{
        String print="";
        String updateSQL = "UPDATE cs3250main."+table+" SET quantity=?, wholesale_cost=?, sale_price=?, supplier_id=? WHERE product_id =? ";
        PreparedStatement statement3 = conn.prepareStatement(updateSQL);
        statement3.setString(1,_quantity);
        statement3.setString(2, _wholesaleCost);
        statement3.setString(3,_salePrice);
        statement3.setString(4,_supplierId);
        statement3.setString(5,_productId);

        int rowsUpdated = statement3.executeUpdate();
        if(rowsUpdated>0){
            print+="An existing product updated successfully<br>";
        }
        return print;
    }

    public String delete(Connection conn, String _productId) throws SQLException{
        String print="";
        String deleteSql = "DELETE FROM cs3250main."+table+" WHERE product_id=?";
        PreparedStatement statement4 = conn.prepareStatement(deleteSql);
        statement4.setString(1,_productId);
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
