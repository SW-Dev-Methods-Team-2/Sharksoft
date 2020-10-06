import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

public class Controller {

    static Scanner scan = new Scanner(System.in);
    static Scanner scan1 = new Scanner (System.in);

    public static void main(String[] args) {

        final String jdbcURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
        final String jdbcUsername = "admin";
        final String jdbcPassword = "cs3250db1";
        final String sharktabkle = "cs3250main.sharktable";

        ProductDAO p1 = new ProductDAO(jdbcURL, jdbcUsername, jdbcPassword);
        Product pro1 = createProduct();

        try {
            p1.insertProduct(pro1, sharktabkle);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
    public static Product createProduct(){
        System.out.println("Eneter product id");
        String pId = scan.nextLine();
        System.out.println("Enter quantity");
        int quan = scan.nextInt();
        System.out.println("Enter wholesale cost");
        double wCost = scan.nextDouble();
        System.out.println("Enter sale cost");
        double sCost = scan.nextDouble();
        System.out.println("Enter supplier ID");
        String sID = scan1.nextLine();

        Product p1 = new Product(pId,quan,wCost,sCost,sID);

        return p1;



    }
    
}