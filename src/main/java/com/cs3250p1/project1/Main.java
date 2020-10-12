package com.cs3250p1.project1;

//Main class of entry of this application

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.Border;


import static java.lang.System.exit;
import static java.lang.Thread.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
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
int btnMainExit = gui.addButton(mainScreen,"center","Exit Program");

int btnSimGoBack = gui.addButton(simScreen,"south","Go Back");
int btnSimCustomer = gui.addButton(simScreen,"south","Invoke Customer Order");
int btnSimSupplier = gui.addButton(simScreen,"south","Invoke Supplier Order");

int btnCrudGoBack = gui.addButton(crudScreen,"south","Go Back");
int btnCrudCreate = gui.addButton(crudScreen,"south","Create Product");
int btnCrudRead = gui.addButton(crudScreen,"south","Read Product");
int btnCrudUpdate = gui.addButton(crudScreen,"south","Update Product");
int btnCrudDelete = gui.addButton(crudScreen,"south","Delete Product");
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
int frmCrudAddProduct = gui.addForm("Product;Quantity;Wholesale Cost;Sale Price;Supplier Id;",10);
int frmCrudUpdateProduct = gui.addForm("Which Product?;New Quantity;New Wholesale Cost;New Sale Price;New Supplier Id;",10);
int frmCrudDeleteProduct = gui.addForm("Product:;",10);

//Database connection related variables
 String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
 String dbUsername = "admin";
 String dbPassword = "cs3250db1";

InventorySimulator simulator01 = new InventorySimulator(); //create the simulator
Timer timer = new Timer(1000,null); //create the render timer
CRUDDB db1 = new CRUDDB("sharktable"); //create the crud object
Connection conn; //connection object used to connect with DB

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
void appStart(){
   gui.setupGUI();
   gui.screenChangeInto(mainScreen); //start by making the first screen visible
   gui.getButton(btnMainRunProgram).setEnabled(false);
   gui.getButton(btnMainRunSim).setEnabled(false);
   setupListeners();

   simulator01.initializeSimulatorData(); //initialize the simulator, creates database inside itself
   timer.start(); //start rendering

   //Establish connection to database
   try {

       conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

       if (conn != null) {
           //report status to the status bar in the main frame
           txtMainStatusBarOutput="Status: ******CONNECTING******";
           txtMainStatusBarOutput="Status: Connected to database";

           //enable buttons only after db connection has been established
           gui.getButton(btnMainRunProgram).setEnabled(true);
           gui.getButton(btnMainRunSim).setEnabled(true);
       }
   } catch (
           SQLException ex) {
       ex.printStackTrace();
   }
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

    gui. getButton(btnMainExit).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            simulator01.clearInventoryOnline();//clear out what you created in the online database
            System.exit(0); //and then exit the program
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
            String result="Waiting access...";
            try{
               gui.getForm(frmCrudAddProduct).showFormDialog("Enter Product Details"); //show the product creation form
                String P,Q,W,Sp,Su;
                P=gui.getForm(frmCrudAddProduct).getKeyValue("Product");
                Q=gui.getForm(frmCrudAddProduct).getKeyValue("Quantity");
                W=gui.getForm(frmCrudAddProduct).getKeyValue("Wholesale Cost");
                Sp=gui.getForm(frmCrudAddProduct).getKeyValue("Sale Price");
                Su=gui.getForm(frmCrudAddProduct).getKeyValue("Supplier Id");
                result=db1.addProduct(conn,P,Q,W, Sp, Su); //add product with user entered values
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            crudOutputStream.push(result); //send result to stream for render later
        }
    });
    gui.getButton(btnCrudRead).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String result="Waiting access...";
            try{
                result=db1.select(conn); //function passes result onto the string
                //this shows everything but supplier id... gotta fix later
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            crudOutputStream.push(result); //send result to stream for render later
        }
    });
    gui.getButton(btnCrudUpdate).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String result="Waiting access...";
            try{
                gui.getForm(frmCrudUpdateProduct).showFormDialog("Enter Update Details");
                String P,Q,W,Sp,Su;
                P=gui.getForm(frmCrudUpdateProduct).getKeyValue("Which Product?");
                Q=gui.getForm(frmCrudUpdateProduct).getKeyValue("New Quantity");
                W=gui.getForm(frmCrudUpdateProduct).getKeyValue("New Wholesale Cost");
                Sp=gui.getForm(frmCrudUpdateProduct).getKeyValue("New Sale Price");
                Su=gui.getForm(frmCrudUpdateProduct).getKeyValue("New Supplier Id");
                result=db1.update(conn,P,Q,W,Sp,Su); //function passes result onto the string
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            crudOutputStream.push(result); //send result to stream for render later
        }
    });
    gui.getButton(btnCrudDelete).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String result="Waiting access...";
            try{
                gui.getForm(frmCrudDeleteProduct).showFormDialog("Which Product Would You Like to Delete?");
                result=db1.delete(conn,
                        gui.getForm(frmCrudDeleteProduct).getKeyValue("Product:"));
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            crudOutputStream.push(result); //send result to stream for render later
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