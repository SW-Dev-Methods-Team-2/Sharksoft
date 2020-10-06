import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.*;                           //This program will create a JFrame and build North, East, and South
import java.awt.*;                              //JPanels then place JRadio Buttons in a group in the South JPanel that
public class theGUI{                               //changes the background color of each JRadioButton based on each

    JFrame frame;                               //This is my JFrame named frame

    public static JPanel panelInPanel;
    public static JPanel southPanel;                 //Naming the JPanel in the South, "southPanel"
    public static JPanel primaryPanel;                 //Naming the JPanel in the North, "primaryPanel"

    public ButtonGroup jGroup;                  //Naming the ButtonGroup, "jGroup"
    JRadioButton empJButton;                      //Naming Each JRadioButton; Each button is named with first letter as
    JRadioButton simJButton;                      //same first letter of which color the button will change.

    JLabel mainScreenLabel;
    JLabel simLabel;

    public static void main(String[] args) {    //Main method begins the program

        new theGUI();                              //Main method starts the program by creating new "hW2"
    }

    public theGUI(){                       //The Constructor is used to initialize objects


        frame = new JFrame("JPanel in JPanel Example GUI"); //JFrame, "frame" is now titled
        frame.setAlwaysOnTop(true);     //This locks the GUI over the top of all other windows

        mainScreenLabel = new JLabel("This is employee portal"); //Demonstration JLabel linked w/ Employee button

        //This is the demonstration of working component of JPanel inside other JPanel
        simLabel = new JLabel("This is the screen for the simulator"); // this is the test label;JPanel in JPanel

        panelInPanel = new JPanel(); //this is a test panel we will attempt to put inside another JPanel in the north

        southPanel = new JPanel();           // JPanel "southPanel" is a new JPanel
        primaryPanel = new JPanel();           // JPanel "primaryPanel" is a new JPanel

        jGroup = new ButtonGroup();     // Initializes "jGroup" as a new ButtonGroup

        buildSouth();                   // This builds the South JPanel

        primaryPanel.add(mainScreenLabel);;    //adds TextField "mainScreenLabel" to the north(Uneditable)

        frame.add(southPanel, BorderLayout.SOUTH); //Default manager that puts the "south" JPanel in the South of frame

        frame.add(primaryPanel, BorderLayout.NORTH); //Default manager that puts the "north" JPanel in the North of frame
        primaryPanel.add(panelInPanel);

        //"panelInPanel adds the label to demonstrate working component when using a panel inside a panel
        panelInPanel.add(simLabel);


        //this sets the visibility of the panel within the panel to false so it can coexist with other panels without
        //being seen. This is set to false so later we can use it like a switch by setting visibility to true according
        //to its corresponding button
        panelInPanel.setVisible(false);

        frame.pack();                   // This condenses the GUI to have no unnecessary space
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE); //This exits the program when the JFrame is closed
        frame.setVisible(true);         //This makes the GUI visible to the user

    }


    public void buildSouth() {          //This method creates JRadioButtons in a group and labels each with a color then
        // adds each JRadioButton to the South

        empJButton = new JRadioButton("Employee Portal");
        simJButton = new JRadioButton("Simulator");

        southPanel.add(empJButton);
        southPanel.add(simJButton);

        jGroup.add(empJButton);
        jGroup.add(simJButton);

        empJButton.addActionListener(e -> { //adds action listener to empJButton and changes visibility of buttons and
            //other JPanels

            empJButton.setBackground(Color.RED);  //This case the JRadioButton is clicked and turns its background to Red

            simJButton.setBackground(Color.WHITE);//while turning other JRadioButton backgrounds to white

            panelInPanel.setVisible(false);

            mainScreenLabel.setVisible(true);

        });
        simJButton.addActionListener(e -> {//adds action listener to bJButton and changes color of each button background

            simJButton.setBackground(Color.BLUE);

            empJButton.setBackground(Color.WHITE); //This case the JRadioButton is clicked and turns its background to Blue

            panelInPanel.setVisible(true); // this toggles the panel within the panel from off to on

            mainScreenLabel.setVisible(false);//toggles the original panel in the north from on to off

        });
    }



}