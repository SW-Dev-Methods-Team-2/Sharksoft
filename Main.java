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
    static JFrame mainFrame = new JFrame(); //the different frames this program is divided into
    static JFrame simFrame = new JFrame();
    static  JFrame crudFrame = new JFrame();
    //buttons...
    static JButton btnMainRunProgram = new JButton("Run Program");
    static JButton btnMainRunSim = new JButton("Run Simulation");
    static JButton btnSimGoBack = new JButton("Go back");
    static JButton btnSimCustomer = new JButton("Invoke Customer Order");
    static JButton btnSimSupplier = new JButton("Invoke Supplier Order");
    static JButton btnSimDummy = new JButton("Dummy");
    static JButton btnCrudGoBack = new JButton("Go back");
    static JButton btnCrudCreate = new JButton("Create Product");
    static JButton btnCrudRead = new JButton("Read Product");
    static JButton btnCrudUpdate = new JButton("Update Product");
    static JButton btnCrudDelete = new JButton("Delete Product");
    //end  buttons...
    //labels...
    static JLabel txtMainStatusBar = new JLabel(txtMainStatusBarOutput);
    static JLabel txtSimTime = new JLabel("Time");
    static JLabel txtSimOutput = new JLabel(simOutputStream.getStream());//this JLabel is output for the simulator
    static JLabel txtSimOutput2 = new JLabel("");
    static JLabel txtCrudOutput = new JLabel("CRUD output goes here");
    //end labels...

    //database connection parameters
    static  String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
    static  String dbUsername = "admin";
    static  String dbPassword = "cs3250db1";

    static InventorySimulator simulator01 = new InventorySimulator(); //create the simulator
    static Timer timer = new Timer(1000,null); //create the render timer
    static CRUDDB db1 = new CRUDDB(); //create the crud object
    static Connection conn; //connection object used to connect with DB

    //METHODS FOR PROGRAM FUNCTIONS---------------------------------------------------------------

    static void btnMainRunProgramMethod (){
        mainFrame.setVisible(false);
        crudFrame.setVisible(true);

        //initialize crud process
        crudRunning = true;
    }
    static void btnMainRunSimMethod (){
        mainFrame.setVisible(false);
        simFrame.setVisible(true);
        simulationRunning=true; //activate simulation
    }
    static void btnSimGoBackMethod (){
        simFrame.setVisible(false);
        mainFrame.setVisible(true);
        simulationRunning = false; //deactivate simulation
    }
    static void btnSimCustomerMethod (){
        simOutputStream.push(simulator01.processBuyer(timeSeconds));
    }
    static void btnSimSupplierMethod (){
        simOutputStream.push(simulator01.processSupplier(timeSeconds,dayCounter));
    }
    static void btnSimDummyMethod (){

    }
    static void btnCrudGoBackMethod (){
        crudFrame.setVisible(false);
        mainFrame.setVisible(true);
        crudRunning=false;
    }
    static void btnCrudCreateMethod(){
        String result="Waiting access...";
        try{
            result=db1.addProduct(conn); //function passes result onto the string
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        crudOutputStream.push(result); //send result to stream for render later
    }
    static void btnCrudReadMethod(){
        String result="Waiting access...";
        try{
            result=db1.select(conn); //function passes result onto the string
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        crudOutputStream.push(result); //send result to stream for render later
    }
    static void btnCrudUpdateMethod(){
        String result="Waiting access...";
        try{
            result=db1.update(conn); //function passes result onto the string
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        crudOutputStream.push(result); //send result to stream for render later
    }
    static void btnCrudDeleteMethod (){
        String result="Waiting access...";
        try{
            result=db1.delete(conn); //function passes result onto the string
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        crudOutputStream.push(result); //send result to stream for render later
    }
    static void appRender() {

        txtMainStatusBar.setText(txtMainStatusBarOutput); //update the status bar of main frame

        if (crudRunning == true) {
            crudRenderTest++;
            if (crudRenderTest>10) crudRenderTest=0;
            crudOutputStream.push(Integer.toString(crudRenderTest));
            txtCrudOutput.setText(crudOutputStream.getStream());
        }
        if (simulationRunning == true) {
            timeSeconds++; //add time
            txtSimTime.setText("Time=" + Integer.toString(timeSeconds)); //print the time

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
            txtSimOutput.setText(simOutputStream.getStream());
            txtSimOutput2.setText(simulator01.printTotalResult());
        }
    }


    //PROGRAM ENTRY POINT----------------------------
    public static void main(String[] args) {

        GUIInit(); //initialize the GUI, buttons, actionListeners, labels, jpanels, etc.

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

    } //END PROGRAM-----------------------------------------------------------

    //METHOD FOR PROGRAM SETUP-----------------------------------------------
    static void GUIInit(){
        //MAINFRAME DEFINITION

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //without this, frame is hidden but
        mainFrame.setPreferredSize(new Dimension(640, 480));

        JLabel txtSharkSoft = new JLabel("SharkSoft"); //application title design

        //create a panel and add the buttons to it
        JPanel pnButton = new JPanel();
        pnButton.add(btnMainRunProgram);
        pnButton.add(btnMainRunSim);

        //create bottom panel
        JPanel pnMainBottom = new JPanel();
        pnMainBottom.add(txtMainStatusBar);

        //add objects to the frame
        mainFrame.getContentPane().add(txtSharkSoft, BorderLayout.NORTH);
        mainFrame.getContentPane().add(pnButton, BorderLayout.CENTER);
        mainFrame.getContentPane().add(pnMainBottom,BorderLayout.SOUTH);
        mainFrame.pack();
        //MAINFRAME DEFINITION END----------------------

        mainFrame.setVisible(true); //Just makes the frame visible

        //SIMULATION SCREEN DEFINITION START--------------
        simFrame.setPreferredSize(new Dimension(640, 480));
        simFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //without this, frame is hidden but

        //define the title panel
        JPanel pnSimTitle = new JPanel();
        JLabel txtSimTitle = new JLabel("Simulation");
        pnSimTitle.add(txtSimTitle);
        pnSimTitle.add(txtSimTime);

        //define the screen panel
        JPanel pnSimulationScreen = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
        txtSimOutput.setBorder(blackline);
        txtSimOutput2.setBorder(blackline);
        pnSimulationScreen.add(txtSimOutput); //rendering area 1
        pnSimulationScreen.add(txtSimOutput2); //rendering area 2

        //define and add to the buttons panel
        JPanel pnSimBtn = new JPanel();
        pnSimBtn.add(btnSimGoBack);
        pnSimBtn.add(btnSimCustomer);
        pnSimBtn.add(btnSimSupplier);
        pnSimBtn.add(btnSimDummy);

        simFrame.getContentPane().add(pnSimTitle, BorderLayout.NORTH);//title on top
        simFrame.getContentPane().add(pnSimulationScreen, BorderLayout.CENTER); //renderin at center
        simFrame.getContentPane().add(pnSimBtn, BorderLayout.SOUTH); //return button on the bottom

        simFrame.pack(); //finalize properties

        simFrame.setVisible(false);//keep invisible for now

        //SIMULATION SCREEN DEFINITION END---------------------------

        //CRUD SCREEN DEFINITION START--------------------------------

        crudFrame.setPreferredSize(new Dimension(640, 480));
        crudFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //without this, frame is hidden but

        JPanel pnCrudTitle = new JPanel();
        JLabel txtCrudTitle = new JLabel("CRUD");
        pnCrudTitle.add(txtCrudTitle);

        JPanel pnCrudCenter = new JPanel();

        pnCrudCenter.add(txtCrudOutput);

        JPanel pnCrudBottom = new JPanel();
        pnCrudBottom.add(btnCrudGoBack);
        pnCrudBottom.add(btnCrudCreate);
        pnCrudBottom.add(btnCrudRead);
        pnCrudBottom.add(btnCrudUpdate);
        pnCrudBottom.add(btnCrudDelete);

        crudFrame.getContentPane().add(pnCrudTitle, BorderLayout.NORTH);//title on top
        crudFrame.getContentPane().add(pnCrudCenter, BorderLayout.CENTER); //rendering at center
        crudFrame.getContentPane().add(pnCrudBottom, BorderLayout.SOUTH); //return button on the bottom

        crudFrame.pack();

        crudFrame.setVisible(false);

        //CRUD SCREEN DEFINITION END---------------------

        //ACTION LISTENERS, these codes binds the methods to the buttons
        //RUN SIMULATION BUTTON EVENT
        btnMainRunProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnMainRunProgramMethod();
            }});

        btnMainRunSim.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btnMainRunSimMethod();
            }
        });

        //SIMULATION RETURN BUTTON EVENT
        btnSimGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               btnSimGoBackMethod();
            }
        });
        btnSimCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSimCustomerMethod();
            }
        });

        btnSimSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSimSupplierMethod();
            }
        });
        btnSimDummy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSimDummyMethod();
            }
        });

        btnCrudGoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               btnCrudGoBackMethod();
            }
        });

        btnCrudCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCrudCreateMethod();
            }
        });
        btnCrudRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCrudReadMethod();
            }
        });
        btnCrudUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCrudUpdateMethod();
            }
        });
        btnCrudDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCrudDeleteMethod();
            }
        });

        //SETUP RENDER TIMER FOR SIMULATION OUTPUT
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //this timer handles our frames
                appRender();
            }
        });
    }
}