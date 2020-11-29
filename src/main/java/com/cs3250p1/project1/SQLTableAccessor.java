package com.cs3250p1.project1;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

class TableData{
    String[] columnNames;
    String[][] data;
}

public class SQLTableAccessor {

    String jdbcURL;
    String jdbcUsername;
    String jdbcPassword;
    private Connection jdbcConnection;

    SQLTableAccessor(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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

    public DefaultTableModel getTableModel(String table) throws SQLException {

        String selectSql = "SELECT * FROM " + table;
        connect();
        Statement selectStatement = jdbcConnection.createStatement();
        ResultSet result = selectStatement.executeQuery(selectSql);

        ResultSetMetaData rsmd = result.getMetaData();
        int columnCount = rsmd.getColumnCount();

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        int rowCounter = 0;
        while (result.next()) {
            data.add(new ArrayList<String>());

            for(int i=1;i<=columnCount;i++) {
                data.get(rowCounter).add(result.getString(rsmd.getColumnName(i)));
            }
            rowCounter++;
        }

        TableData tableData = new TableData();
        tableData.columnNames = new String[columnCount]; //initialize this array to the number of column names
        tableData.data = new String[rowCounter+1][columnCount];

        //convert table data to JTable model readable format i.e. converting arraylist to regular arrays
        for(int j=0;j<rowCounter;j++){//this nested loop is for filling data[][]
            for(int k=0;k<columnCount;k++){
                tableData.data[j][k]=data.get(j).get(k);
            }
        }

        for (int l=1;l<columnCount;l++){
            String str = rsmd.getColumnName(l);//column name in sql start from index 1
            tableData.columnNames[l]=str; //fill columnNames
        }


        result.close();
        selectStatement.close();

        disconnect();
        System.out.println("Data retrieval complete...");
        DefaultTableModel model = new DefaultTableModel(tableData.data,tableData.columnNames);
        return model;
    }


}
