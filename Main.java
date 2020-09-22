import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){
                String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
                String dbUsername = "admin";
                String dbPassword = "cs3250db1";


                CRUDDB db1 = new CRUDDB();

                Scanner scan = new Scanner(System.in);

                try {

                    Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

                    if (conn != null) {
                        System.out.println("******CONNECTING******");
                        System.out.println("Connected to database");

                    }
                    System.out.println("Press 'c' to create product, 'r' to read, 'u' to update, 'd' to delete");
                    String answer = scan.nextLine();

                    if(answer.equalsIgnoreCase("c")){


                        db1.addProduct(conn);
                    }
                    else if(answer.equalsIgnoreCase("r")){
                        db1.select(conn);
                    }
                    else if(answer.equalsIgnoreCase("u")){
                        db1.update(conn);
                    }
                    else if(answer.equalsIgnoreCase("d")){
                        db1.delete(conn);
                    }


            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }
}