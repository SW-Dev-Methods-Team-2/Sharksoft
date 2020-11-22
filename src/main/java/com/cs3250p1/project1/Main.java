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
final String simsharktable = "cs3250main.simsharktable";
 String sales_order = "cs3250main.sales_orders";
 String user_data = "cs3250main.user_data";

InventorySimulator simulator01 = new InventorySimulator(); //create the simulator
Timer timer = new Timer(1000,null); //create the render timer
    AppFileHandler fHandle = new AppFileHandler();
ProductDAO p1 = new ProductDAO(dbURL, dbUsername, dbPassword);
Product pro1 = new Product();

/**
 * Method that runs the display of text in a timed fashion. Once the timer hits every second,
 * the display updates.
 */
void appRender() {

       gui.setText(mainOutputStream.getStream());
       if (simulationMode==true) {
           gui.chBoxSimulation.setText("Simulation On");
       }
       else{
           gui.chBoxSimulation.setText("Simulation Off");
       }

    //each loop, update simulation variable according to checkbox status
       simulationMode= gui.chBoxSimulation.isSelected();

   /*if (simulationMode == true) {
       simulator01.runSim();
       mainOutputStream.pushLn("Time=" + Integer.toString(simulator01.getTimeSeconds()));

       //...if day reaches 6, then simulation is over. This program simulates a week at a time.
       if (simulator01.getDayCounter() > 6) simulationMode = false;

       //finalize and render all result at the end of the frame
       mainOutputStream.pushLn(simulator01.getStream());
       mainOutputStream.pushLn(simulator01.printTotalResult());
   }*/
}
/**
 * A method that contains routines to initialize the program and its objects.
 */
void appStart(){

   gui.setupGUI();

    gui.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                simulator01.clearInventoryOnline();//clear out what you created in the online database
                System.out.println("Simulator data cleared!");
            }
        });
    gui.barChartFrame.addWindowListener(new WindowAdapter(){
        @Override
        public void windowClosing(WindowEvent e){
            simulator01.clearInventoryOnline();;
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
                    } else
                    {
                        p1.insertProduct(pro1, simsharktable);
                        mainOutputStream.push("SimSharktable: Operation Complete.\n");

                    }
                }catch(SQLException f){
                    f.printStackTrace();
                }
        }
    });
    gui.btnCrudRead.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                if(simulationMode==false) {
                    mainOutputStream.push(p1.listAllProducts(sharktable));
                    mainOutputStream.pushLn("Main Inventory Product listings complete.");
                }
                else{
                    mainOutputStream.push(p1.listAllProducts(simsharktable));
                }
            }catch (SQLException ex) {
                ex.printStackTrace();
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
                }
                else{
                    p1.updateProduct(pro1,simsharktable);
                    mainOutputStream.pushLn("Simsharktable: Product updated.");
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
                }
                else
                {
                    p1.deleteProduct(pro1,simsharktable);
                    mainOutputStream.pushLn("Simsharktable: Product deleted.");
                }
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            mainOutputStream.push("Operation Complete.");
        }
    });

    gui.btnCrudOpenCustomerOrder.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> lines = gui.getStringFromFileDialog(); //lets user choose a file,
            // then loads it's string as a list onto the variable
            //parse that string and store it into a structure

            SalesOrder order = new SalesOrder();
            OrderDAO oDao = new OrderDAO(dbURL, dbUsername, dbPassword);

            System.out.println("Start sending sales orders");
            for(int i = 1; i < lines.size(); i++){
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

                order.setDate(nextTime);//
                order.setemail(data[1]);
                order.setShippingA(data[2]);
                order.setId(data[3]);System.out.print(data[4]+"\n");
                order.setquantity(Integer.parseInt(data[4].trim()));

                mainOutputStream.push(data[0]);
                mainOutputStream.push(data[1]);
                mainOutputStream.push(data[2]);
                mainOutputStream.push(data[3]);
                mainOutputStream.push(data[4]+"\n");

                System.out.print(data[0]);
                System.out.print(data[1]);
                System.out.print(data[2]);
                System.out.print(data[3]);
                System.out.print(data[4]);
                
                
                try{
                    if (simulationMode==false){
                        oDao.insertOrder(order, "cs3250main.sales_orders");
                        mainOutputStream.pushLn("Order inserted into cs3250main.sales_orders.");
                    }
                    else{
                        oDao.insertOrder(order,"cs3250main.simsales_orders");
                        mainOutputStream.pushLn("Order insertd into cs3250main.simsales_orders");
                    }

                }
                catch(SQLException x){
                    x.printStackTrace();
                }

            }

        }
    });
    gui.btnCrudOpenSupplierOrder.addActionListener(new ActionListener() {
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
                    }
                    else
                    {
                        mainOutputStream.pushLn("Sorry! Bulk Supplier Order simulation not added yet!");
                    }
                }
                catch(SQLException x){
                    x.printStackTrace();
                }

            }
        }
    });

    gui.chBoxBarChart.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gui.barChartFrame.isVisible()){
                gui.barChartFrame.setVisible(true);
            }
            else
            {
                gui.barChartFrame.setVisible(false);
            }
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
}
