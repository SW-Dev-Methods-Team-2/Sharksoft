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

public class Main{

    //variables that control the status of the main program
     StringStreamer simOutputStream = new StringStreamer(); //this streamer is used by simulator
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
     int btnSimDummy = gui.addButton(simScreen,"south","dummy");
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
     int txtSimOutput = gui.addLabel(simScreen,"center",simOutputStream.getStream());
     int txtSimOutput2 = gui.addLabel(simScreen,"center","");
     int txtCrudOutput = gui.addLabel(crudScreen,"center","CRUD output goes here");
    //end labels...

    //Database connection related variables
      String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
      String dbUsername = "admin";
      String dbPassword = "cs3250db1";

     InventorySimulator simulator01 = new InventorySimulator(); //create the simulator
     Timer timer = new Timer(1000,null); //create the render timer
     CRUDDB db1 = new CRUDDB(); //create the crud object
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
            timeSeconds++; //add time
            gui.setText(txtSimTime,"Time=" + Integer.toString(timeSeconds)); //print the time

            //process the time
            if (timeSeconds >= 86400) {
                simOutputStream.push("Day Over!-------------------------------------------");
                simulator01.resetAllSuppliers();
                timeSeconds = 0;
                dayCounter++;
                if (dayCounter < 7) simOutputStream.push("Starting day " + dayCounter);
            }
            //...advance day counter each time the seconds hits 86400, then reset the seconds
            //...if day reaches 6, then simulation is over. This program simulates a week at a time.
            if (dayCounter > 6) simulationRunning = false;

            //finalize and render all result at the end of the frame
            gui.setText(txtSimOutput,simOutputStream.getStream());
            gui.setText(txtSimOutput2,simulator01.printTotalResult());
        }
    }
    void appStart(){
        gui.setupGUI();
        gui.screenChangeInto(mainScreen); //start by making the first screen visible
        gui.getButton(btnMainRunProgram).setEnabled(false);
        gui.getButton(btnMainRunSim).setEnabled(false);
        setupListeners();

        simulator01.initializeSimulatorData(); //initialize the simulator
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
         gui.getButton(btnSimGoBack).addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 gui.screenChangeInto(mainScreen);
                 simulationRunning = false; //deactivate simulation
             }
         });
         gui.getButton(btnSimCustomer).addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 simOutputStream.push(simulator01.processBuyer(timeSeconds));
             }
         });

         gui.getButton(btnSimSupplier).addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 simOutputStream.push(simulator01.processSupplier(timeSeconds,dayCounter));
             }
         });
         gui.getButton(btnSimDummy).addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 //dummy
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
                     JPanel panel = new JPanel();
                     JTextField productIdField = new JTextField(20);
                     JTextField quantityField = new JTextField(20);
                     JTextField wholesaleCostField = new JTextField(20);
                     JTextField salePriceField = new JTextField(20);
                     JTextField supplierIdField = new JTextField(20);
                     panel.add(productIdField);
                     panel.add(quantityField);
                     panel.add(wholesaleCostField);
                     panel.add(salePriceField);
                     panel.add(supplierIdField);
                     JOptionPane.showMessageDialog(null,panel,"Information",JOptionPane.INFORMATION_MESSAGE);
                     result=db1.addProduct(conn,productIdField.getText(),
                             quantityField.getText(),
                             wholesaleCostField.getText(),
                             salePriceField.getText(),
                             supplierIdField.getText()); //function passes result onto the string
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
                     result=db1.update(conn); //function passes result onto the string
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
                     result=db1.delete(conn); //function passes result onto the string
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
    }//end setupListeners

    }