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
    /**
     * The main method starts the program and is being used to construct and initialize a
     * the JFrame, JRadioButtons, ButtonGroup, adds a JPanel and object to the center and south
     */
    static int i=1;
    static StringStreamer stream1 = new StringStreamer(); //this streamer is used by simulator
    static StringStreamer stream2 = new StringStreamer(); //this streamer is used by the CRUDframe
    static String simResults = "";
    static boolean simulationRunning = false;
    static int timeSeconds=0;
    static int dayCounter=0;

    static boolean crudRunning=false;




    public static void main(String[] args) {//main function, program start...

        //MAINFRAME DEFINITION
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //without this, frame is hidden but
        mainFrame.setPreferredSize(new Dimension(640,480));

        JLabel txtSharkSoft = new JLabel("SharkSoft"); //application title design

        JPanel pnButton = new JPanel();
        JButton btnRunProgram = new JButton("Run Program");
        JButton btnRunSim = new JButton("Run Simulation");

        pnButton.add(btnRunProgram);
        pnButton.add(btnRunSim);

        mainFrame.getContentPane().add(txtSharkSoft, BorderLayout.NORTH);
        mainFrame.getContentPane().add(pnButton, BorderLayout.CENTER);
        mainFrame.pack();
        //MAINFRAME DEFINITION END----------------------

        mainFrame.setVisible(true); //Just makes the frame visible

        //SIMULATION SCREEN DEFINITION START--------------
        JFrame simFrame = new JFrame();
        simFrame.setPreferredSize(new Dimension(640,480));
        simFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //without this, frame is hidden but

        JPanel pnSimTitle = new JPanel();
        JLabel txtSimTitle = new JLabel("Simulation");
        JLabel txtSimTime = new JLabel("Time");
        pnSimTitle.add(txtSimTitle);
        pnSimTitle.add(txtSimTime);

        JPanel pnSimulationScreen = new JPanel();
        JLabel txtSimulationLog = new JLabel(stream1.getStream());//this JLabel is output for the simulator
        JLabel txtSimResults = new JLabel(simResults);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        txtSimulationLog.setBorder(blackline);
        txtSimResults.setBorder(blackline);
        pnSimulationScreen.add(txtSimulationLog);
        pnSimulationScreen.add(txtSimResults);

        JPanel pnSimBtn = new JPanel();
        JButton btnGoBack = new JButton("Go back");
        JButton btnCustomer = new JButton("Invoke Customer Order");
        JButton btnSupplier = new JButton("Invoke Supplier Order");
        JButton btnDummy = new JButton("Dummy");
        pnSimBtn.add(btnGoBack);
        pnSimBtn.add(btnCustomer);
        pnSimBtn.add(btnSupplier);
        pnSimBtn.add(btnDummy);

        simFrame.getContentPane().add(pnSimTitle,BorderLayout.NORTH);//title on top
        simFrame.getContentPane().add(pnSimulationScreen,BorderLayout.CENTER); //renderin at center
        simFrame.getContentPane().add(pnSimBtn,BorderLayout.SOUTH); //return button on the bottom

        simFrame.pack(); //finalize properties

        simFrame.setVisible(false);//keep invisible for now

        //SIMULATION SCREEN DEFINITION END---------------------------

        //CRUD SCREEN DEFINITION START--------------------------------
        JFrame crudFrame = new JFrame();
        crudFrame.setPreferredSize(new Dimension(640,480));
        crudFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //without this, frame is hidden but

        JPanel pnCrudTitle = new JPanel();
        JLabel txtCrudTitle = new JLabel("CRUD");
        pnCrudTitle.add(txtCrudTitle);

        JPanel pnCrudCenter= new JPanel();
        JLabel txtCentralText = new JLabel("Central item goes here");
        pnCrudCenter.add(txtCentralText);

        JPanel pnCrudBottom = new JPanel();
        JButton btnCrudGoBack = new JButton("Go back");
        JButton btnCrudCreate = new JButton("Create Product");
        JButton btnCrudRead = new JButton("Read Product");
        JButton btnCrudUpdate = new JButton("Update Product");
        JButton btnCrudDelete = new JButton("Delete Product");
        pnCrudBottom.add(btnCrudGoBack);
        pnCrudBottom.add(btnCrudCreate);
        pnCrudBottom.add(btnCrudRead);
        pnCrudBottom.add(btnCrudUpdate);
        pnCrudBottom.add(btnCrudDelete);

        crudFrame.getContentPane().add(pnCrudTitle,BorderLayout.NORTH);//title on top
        crudFrame.getContentPane().add(pnCrudCenter,BorderLayout.CENTER); //rendering at center
        crudFrame.getContentPane().add(pnCrudBottom,BorderLayout.SOUTH); //return button on the bottom

        crudFrame.pack();

        crudFrame.setVisible(false);

        //CRUD SCREEN DEFINITION END----------------------


        //COMPONENT OBJECT INITIALIZATIONS FOR APPLICATION
        //--------------------------

        //CREATE AND INITIALIZE THE INVENTORY SIMULATOR OBJECT
        InventorySimulator simulator01 = new InventorySimulator();
        simulator01.initializeSimulatorData(); //generate the simulator data

        //CREATE AND INITIALIZE THE CRUD OBJECT
        //First establish connection to database
        String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
        String dbUsername = "admin";
        String dbPassword = "cs3250db1";

        CRUDDB db1 = new CRUDDB(); //instantiate database handler

        //RUN SIMULATION BUTTON EVENT
        btnRunSim.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainFrame.setVisible(false);
                simFrame.setVisible(true);
                simulationRunning=true; //activate simulation
            }
        });

        //SIMULATION RETURN BUTTON EVENT
        btnGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simFrame.setVisible(false);
                mainFrame.setVisible(true);
                simulationRunning = false; //deactivate simulation
            }
        });

        btnRunProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                crudFrame.setVisible(true);

                //initialize crud process
                crudRunning = true;

                //Establish connection to database
                try {

                    Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

                    if (conn != null) {
                        stream2.pushLn("******CONNECTING******");
                        stream2.pushLn("Connected to database");

                        stream2.push(db1.delete(conn));
                        stream2.push(db1.delete(conn));


                    }
                } catch (
                        SQLException ex) {
                    ex.printStackTrace();
                }
            }});

        btnCrudGoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crudFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });
        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stream1.push(simulator01.processBuyer(timeSeconds));
            }
        });

        btnSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stream1.push(simulator01.processSupplier(timeSeconds,dayCounter));
            }
        });
        btnDummy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //RENDER TIMER FOR SIMULATION OUTPUT
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //this timer handles our frames
                if (simulationRunning==true) {
                    timeSeconds++; //add time
                    txtSimTime.setText("Time="+Integer.toString(timeSeconds)); //print the time

                    //process the time
                    if (timeSeconds >= 86400){
                        stream1.push("Day Over!-------------------------------------------");
                        simulator01.resetAllSuppliers();
                        timeSeconds=0;
                        dayCounter++;
                        if (dayCounter<7) stream1.push("Starting day "+dayCounter);
                    }
                    //...advance day counter each time the seconds hits 86400, then reset the seconds
                    //...if day reaches 6, then simulation is over. This program simulates a week at a time.
                    if (dayCounter > 6) simulationRunning=false;

                    //finalize and render all result at the end of the frame
                    txtSimulationLog.setText(stream1.getStream());
                    txtSimResults.setText(simulator01.printTotalResult());
                }
                if(crudRunning==true){
                    txtCentralText.setText(stream2.getStream());
                }
            }
        });
        timer.start();
    }
}