package com.cs3250p1.project1;

//Main class of entry of this application

import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * The main class of the application. This class creates the main objects needed to
 * run the application, for example the GUI object to run graphics, the DAO object
 *  to run connection between application and the online database, and the simulator
 * object to conduct simulations.
 */
public class Main{

//variables that control the status of the main program
StringStreamer mainOutputStream = new StringStreamer(); //this streamer is used by the CRUDframe
boolean simulationMode = false;
int timeSeconds = 0;
int dayCounter = 0;

boolean crudRunning = false;
int crudRenderTest=0;

//GUI related objects and variables
GUI gui = new GUI(); //create the GUI object

//forms...
int frmCrudAddProduct = gui.addForm("Enter Product Details","Product;Quantity;Wholesale Cost;Sale Price;Supplier Id;",10);
int frmCrudUpdateProduct = gui.addForm("Enter Update Details","Which Product?;New Quantity;New Wholesale Cost;New Sale Price;New Supplier Id;",10);
int frmCrudDeleteProduct = gui.addForm("Which Product Would You Like to Delete?","Product:;",10);

//Database connection related variables
final String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
final String dbUsername = "admin";
final String dbPassword = "cs3250db1";
final String sharktable = "cs3250main.sharktable";
String simsharktable = "cs3250main.simsharktable";
String sales_order = "cs3250main.sales_orders";
String supplier_order = "cs3250main.supplier_orders";
String simsales_order = "cs3250main.simsales_orders";

InventorySimulator simulator01 = new InventorySimulator(); //create the simulator
Timer timer = new Timer(1000,null); //create the render timer
AppFileHandler fHandle = new AppFileHandler();
BarChartEx chart01 = new BarChartEx();
SQLTableAccessor acc1 = new SQLTableAccessor(dbURL, dbUsername, dbPassword);
    JTable mainTable = new JTable();
ProductDAO p1 = new ProductDAO(dbURL, dbUsername, dbPassword);
Product pro1 = new Product();
SalesOrder order = new SalesOrder();
OrderDAO oDao = new OrderDAO(dbURL, dbUsername, dbPassword);

/**
 * Method that runs the display of text in a timed fashion. Once the timer hits every second,
 * the display updates.
 */
void appRender() {

    if (simulationMode==true) {
        gui.chBoxSimulationStatus.setText("Simulation On");
    }
    else{
        gui.chBoxSimulationStatus.setText("Simulation Off");
    }

 //each loop, update simulation variable according to checkbox status
    simulationMode= gui.chBoxSimulationStatus.isSelected();

    gui.consoleArea.setText(mainOutputStream.getStream());
}
/**
 * A method that contains routines to initialize the program and its objects.
 */
void appStart(){

    try {
        mainTable = new JTable(acc1.getTableModel("cs3250main.sharktable"));
    } catch (SQLException e) {
    }

    gui.GUIinit(mainTable);

    gui.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                simulator01.clearInventoryOnline();//clear out what you created in the online database
                System.out.println("Simulator data cleared!");
            }
        });
    //---end for loop
   setupListeners(); //setup listeners for button press events

   simulator01.initializeSimulatorData(); //initialize the simulator, creates database inside itself
   timer.start(); //start rendering
}

//PROGRAM ENTRY POINT----------------------------

    /**
     * The program entry point, creates an instance of the Main class and calls the appStart() method
     * @param args Arguments to start the program with (no usage)
     */
    public static void main(String[] args) {

    Main app = new Main(); //app is an instance of this main class, access all variables using this instance
   //Thank you Shane!

   app.appStart(); //reconciling all method calls into this method eliminates the need to
   // use "app." for everything, now the variables can be used by their own name
   //without "static" or "app." attached as prefix


}//END PROGRAM-----------------------------------------------------------

    /**
     * Method to assign action listeners to buttons and checkboxes
     */
    void setupListeners(){
    /* gui.getButton(btnSimCustomer).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            simulator01.processBuyer(timeSeconds);
        }
    });
    gui.getButton(btnSimSupplier).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            simulator01.processSupplier(timeSeconds,dayCounter);
        }
    }); */
    //CRUD
    gui.btnCrudCreate.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            //first get data from the form
            gui.getForm(frmCrudAddProduct).showFormDialog(); //show the product creation form
                
            pro1.setId(gui.getForm(frmCrudAddProduct).getKeyValue("Product"));
            pro1.setquantity(Integer.parseInt(gui.getForm(frmCrudAddProduct).getKeyValue("Quantity")));
            pro1.setwholesale_cost(Double.parseDouble(gui.getForm(frmCrudAddProduct).getKeyValue("Wholesale Cost")));
            pro1.setsale_price(Double.parseDouble(gui.getForm(frmCrudAddProduct).getKeyValue("Sale Price")));
            pro1.setsupplier_id(gui.getForm(frmCrudAddProduct).getKeyValue("Supplier Id"));
            //data from form is stored in object pro1

            try {
                //check if simulation is off, if so use sharktable, else use simsharktable
                if (simulationMode == false) {
                    p1.insertProduct(pro1, sharktable);
                    mainOutputStream.push("Sharktable: Operation Complete.\n");
                    updateTable(sharktable);
                } else
                {
                    p1.insertProduct(pro1, simsharktable);
                    mainOutputStream.push("SimSharktable: Operation Complete.\n");
                    updateTable(simsharktable);

                }
            }catch(SQLException f){
                f.printStackTrace();
            }
        }
    });

    gui.btnCrudUpdate.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.getForm(frmCrudUpdateProduct).showFormDialog();

            pro1.setId(gui.getForm(frmCrudUpdateProduct).getKeyValue("Which Product?"));
            pro1.setquantity(Integer.parseInt(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Quantity")));
            pro1.setwholesale_cost(Double.parseDouble(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Wholesale Cost")));
            pro1.setsale_price(Double.parseDouble(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Sale Price")));
            pro1.setsupplier_id(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Supplier Id"));

            try{
                if (simulationMode==false) {
                    p1.updateProduct(pro1, sharktable);
                    mainOutputStream.pushLn("Sharktable: Product updated.");
                    updateTable(sharktable);
                }
                else{
                    p1.updateProduct(pro1,simsharktable);
                    mainOutputStream.pushLn("Simsharktable: Product updated.");
                    updateTable(simsharktable);
                }
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            mainOutputStream.push("Operation Complete.");
        }
    });
    gui.btnCrudDelete.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.getForm(frmCrudDeleteProduct).showFormDialog();
            pro1.setId(gui.getForm(frmCrudDeleteProduct).getKeyValue("Product:"));

            try{
                if (simulationMode==false) {
                    p1.deleteProduct(pro1, sharktable);
                    mainOutputStream.pushLn("Sharktable: Product deleted.");
                    updateTable(sharktable);
                }
                else
                {
                    p1.deleteProduct(pro1,simsharktable);
                    mainOutputStream.pushLn("Simsharktable: Product deleted.");
                    updateTable(simsharktable);
                }
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            mainOutputStream.push("Operation Complete.");
        }
    });

    gui.btnCustomerOrder.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> lines = gui.getStringFromFileDialog(); //lets user choose a file,
            // then loads it's string as a list onto the variable
            //parse that string and store it into a structure
            List<SalesOrder> bulkOrder = new ArrayList<SalesOrder>();
            
            System.out.println("Start sending sales orders");
            for(int i = 2; i < lines.size(); i++){
                String [] data = lines.get(i).split(",");
                //String [] date = data[0].replaceAll(target, replacement)
                String date = data[0].replaceAll("/","-");
                String [] dateArray = date.split("-");
                if(dateArray[0].length()<2){
                    dateArray[0]= "0" + dateArray[0];
                }
                if(dateArray[1].length()<2){
                    dateArray[1]= "0" + dateArray[1];
                }
                String output = dateArray[2] + "-" + dateArray[0] + "-" + dateArray[1];
                Timestamp nextTime = Timestamp.valueOf(LocalDate.parse(output).atStartOfDay());

                SalesOrder temp = new SalesOrder();
                temp.setDate(nextTime);
                temp.setemail(data[1]);
                temp.setShippingA(data[2]);
                temp.setId(data[3]);
                temp.setquantity(Integer.parseInt(data[4].trim()));
                bulkOrder.add(temp);
                //System.out.println(bulkOrder.get(i));
                System.out.print(nextTime+ " ");
                System.out.print(data[1] + " ");
                System.out.print(data[2] + " ");
                System.out.print(data[3] + " ");
                System.out.println(data[4]);

                mainOutputStream.push(data[0]);
                mainOutputStream.push(data[1]);
                mainOutputStream.push(data[2]);
                mainOutputStream.push(data[3]);
                mainOutputStream.push(data[4]+"\n");

                
            }
            try{
                if (simulationMode==false){
                    oDao.insertBatch( bulkOrder,"cs3250main.sales_orders");
                    mainOutputStream.pushLn("Order inserted into cs3250main.sales_orders.");
                    p1.updateQuantity(bulkOrder,"cs3250main.sharktable" );
                    System.out.println("Operation complete");
                    updateTable(sales_order);
                }
                else{
                    oDao.insertBatch(bulkOrder,"cs3250main.simsales_orders");
                    mainOutputStream.pushLn("Order insertd into cs3250main.simsales_orders");
                    updateTable(simsales_order);
                }

            }
            catch(SQLException x){
                x.printStackTrace();
                
            }
               

        }
    });
    gui.btnSupplierOrder.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> lines = gui.getStringFromFileDialog(); //lets user choose a file,
            // then loads it's string as a list onto the variable
            //parse that string and store it into a structure

            SupplierOrder supOrder= new SupplierOrder();
            SupplierOrderDAO supOrdDao = new SupplierOrderDAO(dbURL, dbUsername, dbPassword);

            System.out.println("Start sending supplier orders");
            for(int i = 0; i < lines.size(); i++){
                String [] data = lines.get(i).split(",");
                
                supOrder.setDate(data[0]);
                supOrder.setId(data[1]);
                supOrder.setsupplier_id(data[2]);
                supOrder.setquantity(Integer.parseInt(data[3].trim()));

                mainOutputStream.pushLn("Data sent to supplier orders in database...");
                mainOutputStream.pushLn(data[0]);
                mainOutputStream.pushLn(data[1]);
                mainOutputStream.pushLn(data[2]);
                mainOutputStream.pushLn(data[3]);
                
                try{
                    if (simulationMode==false) {
                        supOrdDao.insertOrder(supOrder, "cs3250main.supplier_orders");
                        mainOutputStream.pushLn("Supplier order inserted into cs3250main.supplier_orders");
                        updateTable(supplier_order);
                    }
                    else
                    {

                    }
                }
                catch(SQLException x){
                    x.printStackTrace();
                }

            }
        }
    });
    gui.tableSelector.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           updateTable((String) gui.tableSelector.getSelectedItem());
        }
    });
    //SETUP RENDER TIMER FOR SIMULATION OUTPUT
    timer.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { //this timer handles our frames
            appRender();
        }
    });
} //end setupListeners
void updateTable(String _table){
    try {
        mainTable.setModel(acc1.getTableModel(_table));
    } catch (SQLException f) {
    }
    mainTable.repaint();
}
}
