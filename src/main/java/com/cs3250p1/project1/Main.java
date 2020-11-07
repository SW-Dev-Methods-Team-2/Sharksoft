package com.cs3250p1.project1;

//Main class of entry of this application

import java.awt.event.*;
import java.util.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;

import static java.lang.System.exit;
import static java.lang.Thread.*;

import org.springframework.boot.SpringApplication;
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
StringStreamer crudOutputStream = new StringStreamer(); //this streamer is used by the CRUDframe
String txtMainStatusBarOutput = "Status";
boolean simulationRunning = false;
int timeSeconds = 0;
int dayCounter = 0;

boolean crudRunning = false;
int crudRenderTest=0;

//GUI related objects and variables
GUI gui = new GUI(); //create the GUI object
int mainScreen = gui.addScreen(); //the different screens this program is divided into
int simScreen = gui.addScreen();
int crudScreen = gui.addScreen();

//buttons...
int btnMainRunProgram = gui.addButton(mainScreen,"center","Run Program");
int btnMainRunSim = gui.addButton(mainScreen,"center","Run Simulation");

int btnSimGoBack = gui.addButton(simScreen,"south","Go Back");
int btnSimCustomer = gui.addButton(simScreen,"south","Invoke Customer Order");
int btnSimSupplier = gui.addButton(simScreen,"south","Invoke Supplier Order");

int btnCrudGoBack = gui.addButton(crudScreen,"south","Go Back");
int btnCrudCreate = gui.addButton(crudScreen,"south","Create Product");
int btnCrudRead = gui.addButton(crudScreen,"south","Read Product");
int btnCrudUpdate = gui.addButton(crudScreen,"south","Update Product");
int btnCrudDelete = gui.addButton(crudScreen,"south","Delete Product");
int btnCrudOpenCustomerOrder = gui.addButton(crudScreen,"east","Open Customer Order");
int btnCrudOpenSupplierOrder = gui.addButton(crudScreen,"east","Open Supplier Order");
//end  buttons...

//labels...
int txtSharkSoft = gui.addLabel(mainScreen,"north","StuffBuySharks Co. Warehouse Portal");
int txtMainStatusBar = gui.addLabel(mainScreen,"south",txtMainStatusBarOutput);
int txtSimTime = gui.addLabel(simScreen,"north","Time");
int txtSimOutput = gui.addLabel(simScreen,"center","---");
int txtSimOutput2 = gui.addLabel(simScreen,"center","");
int txtCrudOutput = gui.addLabel(crudScreen,"center","CRUD output goes here");
//end labels...

//forms...
int frmCrudAddProduct = gui.addForm("Enter Product Details","Product;Quantity;Wholesale Cost;Sale Price;Supplier Id;",10);
int frmCrudUpdateProduct = gui.addForm("Enter Update Details","Which Product?;New Quantity;New Wholesale Cost;New Sale Price;New Supplier Id;",10);
int frmCrudDeleteProduct = gui.addForm("Which Product Would You Like to Delete?","Product:;",10);

//Database connection related variables
final String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
final String dbUsername = "admin";
final String dbPassword = "cs3250db1";
final String sharktable = "cs3250main.sharktable";
 String sales_order = "cs3250main.sales_orders";
 String user_data = "cs3250main.user_data";

InventorySimulator simulator01 = new InventorySimulator(); //create the simulator
Timer timer = new Timer(1000,null); //create the render timer
    FileHandler fHandle = new FileHandler();
ProductDAO p1 = new ProductDAO(dbURL, dbUsername, dbPassword);
Product pro1 = new Product();

SalesOrder order = new SalesOrder();
OrderDAO oDao = new OrderDAO(dbURL, dbUsername, dbPassword);

void appRender() {

   gui.setText(txtMainStatusBar,txtMainStatusBarOutput); //update the status bar of main frame

   if (crudRunning == true) {
       crudRenderTest++;
       if (crudRenderTest>10) crudRenderTest=0;
       crudOutputStream.push(Integer.toString(crudRenderTest));
       gui.setText(txtCrudOutput,crudOutputStream.getStream());
   }
   if (simulationRunning == true) {
       simulator01.runSim();
       gui.setText(txtSimTime,"Time=" + Integer.toString(simulator01.getTimeSeconds()));

       //...if day reaches 6, then simulation is over. This program simulates a week at a time.
       if (simulator01.getDayCounter() > 6) simulationRunning = false;

       //finalize and render all result at the end of the frame
       gui.setText(txtSimOutput,simulator01.getStream());
       gui.setText(txtSimOutput2,simulator01.printTotalResult());
   }
}
/**
 * A method that reconciles the routines that need to be called when starting up
 * this application.
 */
void appStart(){

   gui.setupGUI();

   //the following for loop is temporary, it will be replaced with a more efficient code
    for (int i=0;i<gui.screenList.size();i++) {
        gui.screenList.get(i).frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Clearing up simulator data...");
                simulator01.clearInventoryOnline();//clear out what you created in the online database
            }
        });
    }
    //---end for loop

   gui.screenChangeInto(mainScreen); //start by making the first screen visible
   setupListeners(); //setup listeners for button press events

   simulator01.initializeSimulatorData(); //initialize the simulator, creates database inside itself
   timer.start(); //start rendering
}

//PROGRAM ENTRY POINT----------------------------
public static void main(String[] args) {

    Main app = new Main(); //app is an instance of this main class, access all variables using this instance
   //Thank you Shane!

   app.appStart(); //reconciling all method calls into this method eliminates the need to
   // use "app." for everything, now the variables can be used by their own name
   //without "static" or "app." attached as prefix


}//END PROGRAM-----------------------------------------------------------

void setupListeners(){
   gui.getButton(btnMainRunProgram).addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e){
       gui.screenChangeInto(crudScreen);
       //initialize crud process
       crudRunning = true;
   }});

    gui.getButton(btnMainRunSim).addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            gui.screenChangeInto(simScreen);
            simulationRunning=true; //activate simulation
        }
    });

    gui.getButton(btnSimGoBack).addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            gui.screenChangeInto(mainScreen);
            simulationRunning = false; //deactivate simulation
        }
    });
    gui.getButton(btnSimCustomer).addActionListener(new ActionListener() {
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
    });


    //CRUD
    gui.getButton(btnCrudGoBack).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.screenChangeInto(mainScreen);
            crudRunning=false;
        }
    });

    gui.getButton(btnCrudCreate).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
               gui.getForm(frmCrudAddProduct).showFormDialog(); //show the product creation form
                
                pro1.setId(gui.getForm(frmCrudAddProduct).getKeyValue("Product"));
                pro1.setquantity(Integer.parseInt(gui.getForm(frmCrudAddProduct).getKeyValue("Quantity")));
                pro1.setwholesale_cost(Double.parseDouble(gui.getForm(frmCrudAddProduct).getKeyValue("Wholesale Cost")));
                pro1.setsale_price(Double.parseDouble(gui.getForm(frmCrudAddProduct).getKeyValue("Sale Price")));
                pro1.setsupplier_id(gui.getForm(frmCrudAddProduct).getKeyValue("Supplier Id"));
                try{
                    p1.insertProduct(pro1,sharktable);
                }catch(SQLException f){
                    f.printStackTrace();
                }
            
           crudOutputStream.push("Operation Complete."); //send result to stream for render later
        }
    });
    gui.getButton(btnCrudRead).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                crudOutputStream.push(p1.listAllProducts(sharktable));
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });
    gui.getButton(btnCrudUpdate).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                gui.getForm(frmCrudUpdateProduct).showFormDialog();
                
                pro1.setId(gui.getForm(frmCrudUpdateProduct).getKeyValue("Which Product?"));
                pro1.setquantity(Integer.parseInt(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Quantity")));
                pro1.setwholesale_cost(Double.parseDouble(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Wholesale Cost")));
                pro1.setsale_price(Double.parseDouble(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Sale Price")));
                pro1.setsupplier_id(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Supplier Id"));
                p1.updateProduct(pro1, sharktable);
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            crudOutputStream.push("Operation Complete.");
        }
    });
    gui.getButton(btnCrudDelete).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                gui.getForm(frmCrudDeleteProduct).showFormDialog();
                pro1.setId(gui.getForm(frmCrudDeleteProduct).getKeyValue("Product:"));
                p1.deleteProduct(pro1, sharktable);
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            crudOutputStream.push("Operation Complete.");
        }
    });

    gui.getButton(btnCrudOpenCustomerOrder).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> lines = gui.getStringFromFileDialog(); //lets user choose a file,
            // then loads it's string as a list onto the variable
            //parse that string and store it into a structure

            for(int i = 0; i < lines.size(); i++){
                String [] data = lines.get(i).split(",");
                //System.out.println(data);
                
                order.setDate(data[0]);
                order.setemail(data[1]);
                order.setShippingA(data[2]);
                order.setId(data[3]);
                order.setquantity(Integer.parseInt(data[4]));
                
                try{
                    oDao.insertOrder(order, "cs3250main.sales_orders");
                }
                catch(SQLException x){
                    x.printStackTrace();
                }

            }
           
        }
    });
    gui.getButton(btnCrudOpenSupplierOrder).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            /*List<String> lines = gui.getStringFromFileDialog(); //lets user choose a file,
            // then loads it's string as a list onto the variable
            //parse that string and store it into a structure

            ArrayList<SupplierOrderField> supOrdTable = fHandle.parseSupplierOrder(lines);
            //print what you got
            for (int i=0;i<supOrdTable.size();i++){
                crudOutputStream.pushLn(supOrdTable.get(i).printField());
            }*/



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

/*
package com.cs3250p1.project1;

//Main class of entry of this application

import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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
boolean simulationRunning = false;
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
 String sales_order = "cs3250main.sales_orders";
 String user_data = "cs3250main.user_data";

InventorySimulator simulator01 = new InventorySimulator(); //create the simulator
Timer timer = new Timer(1000,null); //create the render timer
    AppFileHandler fHandle = new AppFileHandler();
ProductDAO p1 = new ProductDAO(dbURL, dbUsername, dbPassword);
Product pro1 = new Product();

void appRender() {

       gui.setText(mainOutputStream.getStream());

   if (simulationRunning == true) {
       simulator01.runSim();
       mainOutputStream.pushLn("Time=" + Integer.toString(simulator01.getTimeSeconds()));

       //...if day reaches 6, then simulation is over. This program simulates a week at a time.
       if (simulator01.getDayCounter() > 6) simulationRunning = false;

       //finalize and render all result at the end of the frame
       mainOutputStream.pushLn(simulator01.getStream());
       mainOutputStream.pushLn(simulator01.printTotalResult());
   }
}
/**
 * A method that reconciles the routines that need to be called when starting up
 * this application.
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
    //---end for loop
   setupListeners(); //setup listeners for button press events

   simulator01.initializeSimulatorData(); //initialize the simulator, creates database inside itself
   timer.start(); //start rendering
}

//PROGRAM ENTRY POINT----------------------------
public static void main(String[] args) {

    Main app = new Main(); //app is an instance of this main class, access all variables using this instance
   //Thank you Shane!

   app.appStart(); //reconciling all method calls into this method eliminates the need to
   // use "app." for everything, now the variables can be used by their own name
   //without "static" or "app." attached as prefix


}//END PROGRAM-----------------------------------------------------------

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
            
               gui.getForm(frmCrudAddProduct).showFormDialog(); //show the product creation form
                
                pro1.setId(gui.getForm(frmCrudAddProduct).getKeyValue("Product"));
                pro1.setquantity(Integer.parseInt(gui.getForm(frmCrudAddProduct).getKeyValue("Quantity")));
                pro1.setwholesale_cost(Double.parseDouble(gui.getForm(frmCrudAddProduct).getKeyValue("Wholesale Cost")));
                pro1.setsale_price(Double.parseDouble(gui.getForm(frmCrudAddProduct).getKeyValue("Sale Price")));
                pro1.setsupplier_id(gui.getForm(frmCrudAddProduct).getKeyValue("Supplier Id"));
                try{
                    p1.insertProduct(pro1,sharktable);
                    if (simulation==true){
                    p1.insertProduct(pro1,simsharktable);
                    }
                    else
                }catch(SQLException f){
                    f.printStackTrace();
                }
            
           mainOutputStream.push("Operation Complete."); //send result to stream for render later
        }
    });
    gui.btnCrudRead.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                mainOutputStream.push(p1.listAllProducts(sharktable));
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });
    gui.btnCrudUpdate.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                gui.getForm(frmCrudUpdateProduct).showFormDialog();
                
                pro1.setId(gui.getForm(frmCrudUpdateProduct).getKeyValue("Which Product?"));
                pro1.setquantity(Integer.parseInt(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Quantity")));
                pro1.setwholesale_cost(Double.parseDouble(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Wholesale Cost")));
                pro1.setsale_price(Double.parseDouble(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Sale Price")));
                pro1.setsupplier_id(gui.getForm(frmCrudUpdateProduct).getKeyValue("New Supplier Id"));
                p1.updateProduct(pro1, sharktable);
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            mainOutputStream.push("Operation Complete.");
        }
    });
    gui.btnCrudDelete.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                gui.getForm(frmCrudDeleteProduct).showFormDialog();
                pro1.setId(gui.getForm(frmCrudDeleteProduct).getKeyValue("Product:"));
                p1.deleteProduct(pro1, sharktable);
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

            ArrayList<CustomerOrderField> cusOrdTable = fHandle.parseCustomerOrder(lines);
            //print what you got
            for (int i=0;i<cusOrdTable.size();i++){
                mainOutputStream.pushLn(cusOrdTable.get(i).printField());
            }

        }
    });
    gui.btnCrudOpenSupplierOrder.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> lines = gui.getStringFromFileDialog(); //lets user choose a file,
            // then loads it's string as a list onto the variable
            //parse that string and store it into a structure

            ArrayList<SupplierOrderField> supOrdTable = fHandle.parseSupplierOrder(lines);
            //print what you got
            for (int i=0;i<supOrdTable.size();i++){
                mainOutputStream.pushLn(supOrdTable.get(i).printField());
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
*/
