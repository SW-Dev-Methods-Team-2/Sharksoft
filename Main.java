


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

    //declare all variables and objects needed to run the program

    static StringStreamer simOutputStream = new StringStreamer(); //this streamer is used by simulator
    static StringStreamer crudOutputStream = new StringStreamer(); //this streamer is used by the CRUDframe
    static String txtMainStatusBarOutput = "Status";
    static boolean simulationRunning = false;
    static int timeSeconds = 0;
    static int dayCounter = 0;

    static boolean crudRunning = false;
    static int crudRenderTest=0;

    //GUI component declarations
    static GUI gui = new GUI();
    static int mainScreen = gui.addScreen(); //the different screens this program is divided into
    static int simScreen = gui.addScreen();
    static int crudScreen = gui.addScreen();

    //buttons...
    static int btnMainRunProgram = gui.addButton(mainScreen,"center","Run Program");
    static int btnMainRunSim = gui.addButton(mainScreen,"center","Run Simulation");
    static int btnSimGoBack = gui.addButton(simScreen,"south","Go Back");
    static int btnSimCustomer = gui.addButton(simScreen,"south","Invoke Customer Order");
    static int btnSimSupplier = gui.addButton(simScreen,"south","Invoke Supplier Order");
    static int btnSimDummy = gui.addButton(simScreen,"south","dummy");
    static int btnCrudGoBack = gui.addButton(crudScreen,"south","Go Back");
    static int btnCrudCreate = gui.addButton(crudScreen,"south","Create Product");
    static int btnCrudRead = gui.addButton(crudScreen,"south","Read Product");
    static int btnCrudUpdate = gui.addButton(crudScreen,"south","Update Product");
    static int btnCrudDelete = gui.addButton(crudScreen,"south","Delete Product");
    //end  buttons...

    //labels...
    static int txtSharkSoft = gui.addLabel(mainScreen,"north","StuffBuySharks Co. Warehouse Portal");
    static int txtMainStatusBar = gui.addLabel(mainScreen,"south",txtMainStatusBarOutput);
    static int txtSimTime = gui.addLabel(simScreen,"north","Time");
    static int txtSimOutput = gui.addLabel(simScreen,"center",simOutputStream.getStream());
    static int txtSimOutput2 = gui.addLabel(simScreen,"center","");
    static int txtCrudOutput = gui.addLabel(crudScreen,"center","CRUD output goes here");
    //end labels...

    //database connection parameters
    static  String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
    static  String dbUsername = "admin";
    static  String dbPassword = "cs3250db1";

    static InventorySimulator simulator01 = new InventorySimulator(); //create the simulator
    static Timer timer = new Timer(1000,null); //create the render timer
    static CRUDDB db1 = new CRUDDB(); //create the crud object
    static Connection conn; //connection object used to connect with DB

    static void appRender() {

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


    //PROGRAM ENTRY POINT----------------------------
    public static void main(String[] args) {

        gui.setupGUI();
        gui.screenChangeInto(mainScreen); //start by making the first screen visible
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
            }
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }


    }//END PROGRAM-----------------------------------------------------------

    static void setupListeners(){
        gui.getButton(btnMainRunProgram).addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            gui.screenChangeInto(crudScreen);
            //initialize crud process
            crudRunning = true;
        }});

    //SETUP RENDER TIMER FOR SIMULATION OUTPUT
        timer.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { //this timer handles our frames
            appRender();
        }
    });
    }//end setupListeners
    //METHOD FOR PROGRAM SETUP-----------------------------------------------

        /*//ACTION LISTENERS, these codes binds the methods to the buttons
        //RUN SIMULATION BUTTON EVENT


        btnMainRunSim.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gui.screenChangeInto(simScreen);
                simulationRunning=true; //activate simulation
            }
        });

        //SIMULATION RETURN BUTTON EVENT
        btnSimGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.screenChangeInto(mainScreen);
                simulationRunning = false; //deactivate simulation
            }
        });
        btnSimCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simOutputStream.push(simulator01.processBuyer(timeSeconds));
            }
        });

        btnSimSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simOutputStream.push(simulator01.processSupplier(timeSeconds,dayCounter));
            }
        });
        btnSimDummy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dummy
            }
        });

        btnCrudGoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.screenChangeInto(mainScreen);
                crudRunning=false;
            }
        });

        btnCrudCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result="Waiting access...";
                try{
                    result=db1.addProduct(conn); //function passes result onto the string
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                crudOutputStream.push(result); //send result to stream for render later
            }
        });
        btnCrudRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result="Waiting access...";
                try{
                    result=db1.select(conn); //function passes result onto the string
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                crudOutputStream.push(result); //send result to stream for render later
            }
        });
        btnCrudUpdate.addActionListener(new ActionListener() {
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
        btnCrudDelete.addActionListener(new ActionListener() {
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
        });*/
    }