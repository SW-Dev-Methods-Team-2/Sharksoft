package com.cs3250p1.project1;

//Main class of entry of this application

import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


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
//Using this class to currently store any sales order received from opening
    //a file, after sql connection is set up, we'll have the sales order
    //directly go to the database
class SalesOrderStorage{
    double date; //dates are long so needs double
    String email;
    String shipping_address;
    String product_id;
    int quantity;
    String printField(){
        return Double.toString(date)+","+email+","+shipping_address+","+
                product_id+","+Integer.toString(quantity);
    }
}
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
int btnCrudOpenFile = gui.addButton(crudScreen,"east","Open File");
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
ProductDAO p1 = new ProductDAO(dbURL, dbUsername, dbPassword);
Product pro1 = new Product();

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
                /*result=db1.delete(conn,
                        gui.getForm(frmCrudDeleteProduct).getKeyValue("Product:"));*/
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            crudOutputStream.push("Operation Complete.");
        }
    });

    gui.getButton(btnCrudOpenFile).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> lines = gui.getStringFromFileDialog(); //lets user choose a file,
            // then loads it's string as a list onto the variable
            //parse that string and store it into a structure

            ArrayList<SalesOrderStorage> storage = parseSalesOrder(lines);
            //print what you got
            storage.get(2).quantity=300;
            for (int i=0;i<storage.size();i++){
                crudOutputStream.pushLn(storage.get(i).printField());
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

    ArrayList<SalesOrderStorage> parseSalesOrder(List<String> lines){
        ArrayList<SalesOrderStorage> storage= new ArrayList<>();

        for (int i=0;i<lines.size();i++){
            //each item in this list is a single line from the string

            String thisLine= lines.get(i);//get a single line of string
            SalesOrderStorage temp = new SalesOrderStorage();

            int commaCounter=0; String str="", output="";
            for (int j=0; j<thisLine.length();j++) {
                str = thisLine.substring(j, j + 1);
                if (str.equals(" ")) {
                    continue;
                } //ignore spaces

                if (str.equals(",")) {
                    commaCounter++;
                    if (commaCounter == 1) {
                        temp.date = Double.parseDouble(output);
                        output = ""; //reset this variable
                        continue;
                    } else if (commaCounter == 2) {
                        temp.email = output;
                        output = "";
                        continue;
                    } else if (commaCounter == 3) {
                        temp.shipping_address = output;
                        output = "";
                        continue;
                    } else if (commaCounter == 4) {
                        temp.product_id = output;
                        output = "";
                        continue;
                    } else {
                    }
                }
                    output = output + str;
                }//when loop ends only quantity is left over.
                temp.quantity = Integer.parseInt(output);
                output = ""; //just for safety
                storage.add(temp); //add this new created field to storage
            }
        return storage; //return the newly created data structure
        }

    }
