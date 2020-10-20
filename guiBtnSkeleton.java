import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.*;                           //This program will create a JFrame and build North, East, and South
import java.awt.*;                              //JPanels then place JRadio Buttons in a group in the South JPanel that
public class guiBtnSkeleton{                               //changes the background color of each JRadioButton based on each

    JFrame frame;                               //This is my JFrame named frame

    //JPanel "north" will be placed in the North using layout manager and will have a "northTexField"(set to uneditable)
    public static JPanel north;

    //JPanel "south" will be placed in South using layout manager and will contain button groups, "mainBtnGroup" and,
    //crudBtnGroup. JPanel "south" will also hold "southTextField"
    public static JPanel south;

    //"mainBtnGroup will consist of JRadioButtons "empPortalBtn" and "simBtn"
    public ButtonGroup mainBtnGroup;

    //"empCrudBtnGroup" will consist of a JRadioButton for each Crud method and a button to return to first options
    // called "empToMain"
    public ButtonGroup empCrudBtnGroup;

    //"simCrudBtnGroup" will consist of a JRadioButton for each method and a button to return to first first options
    // called "simToMain"
    public ButtonGroup simCrudBtnGroup;

    //"empPortalBtn" will set "mainButtonGroup" vis. to false and set "empCrudBtnGroup" vis. to true
    JRadioButton empPortalBtn;

    //"simBtn" will set "mainBtnGroup" vis. to false and set "simCrudBtnGroup" vis. to true
    JRadioButton simBtn;

    //will allow mainBtnGroup to be visible again while setting other visibilities to false. If cannot use in two
    //separate Jradio buttongroups, may need to make one specifically for both emp and sim buttongroups
    JRadioButton empToMain;
    JRadioButton simToMain;

    JRadioButton empCreateBtn;
    JRadioButton empReadBtn;
    JRadioButton empUpdateBtn;
    JRadioButton empDeleteBtn;

    JRadioButton simCreateBtn;
    JRadioButton simReadBtn;
    JRadioButton simUpdateBtn;
    JRadioButton simDeleteBtn;



    public static void main(String[] args) {    //Main method begins the program

        new guiBtnSkeleton();                              //Main method starts the program by creating new "hW2"
    }

    public guiBtnSkeleton(){                       //The Constructor is used to initialize objects


        frame = new JFrame("POS ACCESS CENTER"); //JFrame, "frame" is now titled
        frame.setAlwaysOnTop(true);     //This locks the GUI over the top of all other windows


        north = new JPanel(); // creates a new panel "north" to be put inside the north using layout manager

        south = new JPanel();           // JPanel "southPanel" is a new JPanel

        mainBtnGroup = new ButtonGroup();     // Initializes "mainBtnGroup" as a new ButtonGroup

        empCrudBtnGroup = new ButtonGroup();  // Initializes "empCrudBtnGroup" as a new ButtonGroup

        simCrudBtnGroup = new ButtonGroup(); // Initializes "simCrudBtnGroup" as a new ButtonGroup

        buildNorth();                   // build the north JPanel

        buildSouth();                   // This builds the south JPanel


        ///////north.add(mainScreenLabel);;    //adds TextField "mainScreenLabel" to the north(Uneditable)

        frame.add(south, BorderLayout.SOUTH); //Default manager that puts the "south" JPanel in the South of frame

        frame.add(north, BorderLayout.NORTH); //Default manager that puts the "north" JPanel in the North of frame

        //north.add();

        south.add(empPortalBtn);
        south.add(simBtn);

        south.add(empCreateBtn);
        south.add(empReadBtn);
        south.add(empUpdateBtn);
        south.add(empDeleteBtn);

        south.add(simCreateBtn);
        south.add(simReadBtn);
        south.add(simUpdateBtn);
        south.add(simDeleteBtn);

        //attempting to use "backToMain" in two separate groups
        south.add(empToMain);
        south.add(simToMain);

        empToMain.setVisible(false);
        simToMain.setVisible(false);

        frame.pack();                   // This condenses the GUI to have no unnecessary space
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE); //This exits the program when the JFrame is closed
        frame.setVisible(true);         //This makes the GUI visible to the user

    }

    public void buildNorth() {

        // add northTextfield here

    }

    public void buildSouth() {          //This method creates JRadioButtons in a group and labels each with a color then
        // adds each JRadioButton to the South

        empPortalBtn = new JRadioButton("Employee Portal");
        simBtn = new JRadioButton("Simulator");

        //attempted to use in both "empCrudBtnGroup" and "simCrudBtnGroup"
        empToMain = new JRadioButton("Go back");
        simToMain = new JRadioButton("Go back");

        empCreateBtn = new JRadioButton("Create to Real DB");
        empReadBtn = new JRadioButton("Read real DB");
        empUpdateBtn = new JRadioButton("Update real DB");
        empDeleteBtn = new JRadioButton("Delete from Real database");

        simCreateBtn = new JRadioButton("Create to sim DB");
        simReadBtn = new JRadioButton("Read sim DB");
        simUpdateBtn = new JRadioButton("Update sim DB");
        simDeleteBtn = new JRadioButton("Delete from sim DB");

        mainBtnGroup.add(empPortalBtn);
        mainBtnGroup.add(simBtn);

        empCrudBtnGroup.add(empCreateBtn);
        empCrudBtnGroup.add(empReadBtn);
        empCrudBtnGroup.add(empUpdateBtn);
        empCrudBtnGroup.add(empDeleteBtn);
        // attempting to use "backToMain" in both "empCrudBtnGroup" and "simCrudBtnGroup"
        empCrudBtnGroup.add(empToMain);

        simCrudBtnGroup.add(simCreateBtn);
        simCrudBtnGroup.add(simReadBtn);
        simCrudBtnGroup.add(simUpdateBtn);
        simCrudBtnGroup.add(simDeleteBtn);
        // attempting to use "backToMain" in both "empCrudBtnGroup" and "simCrudBtnGroup"
        simCrudBtnGroup.add(simToMain);

        empCreateBtn.setVisible(false);
        empReadBtn.setVisible(false);
        empUpdateBtn.setVisible(false);
        empDeleteBtn.setVisible(false);

        simCreateBtn.setVisible(false);
        simReadBtn.setVisible(false);
        simUpdateBtn.setVisible(false);
        simDeleteBtn.setVisible(false);

        empPortalBtn.addActionListener(e -> { //adds action listener to empJButton and changes visibility of buttons and

            empPortalBtn.setVisible(false);
            simBtn.setVisible(false);

            empCreateBtn.setVisible(true);
            empReadBtn.setVisible(true);
            empUpdateBtn.setVisible(true);
            empDeleteBtn.setVisible(true);
            empToMain.setVisible(true);

            //other JPanels

            //empJButton.setBackground(Color.RED);  //This case the JRadioButton is clicked and turns its background to Red

            //simJButton.setBackground(Color.WHITE);//while turning other JRadioButton backgrounds to white

            //panelInPanel.setVisible(false);

            //mainScreenLabel.setVisible(true);

        });
        simBtn.addActionListener(e -> {//adds action listener to bJButton and changes color of each button background

            empPortalBtn.setVisible(false);
            simBtn.setVisible(false);

            simCreateBtn.setVisible(true);
            simReadBtn.setVisible(true);
            simUpdateBtn.setVisible(true);
            simDeleteBtn.setVisible(true);
            simToMain.setVisible(true);

            //simJButton.setBackground(Color.BLUE);

            //empJButton.setBackground(Color.WHITE); //This case the JRadioButton is clicked and turns its background to Blue

            //panelInPanel.setVisible(true); // this toggles the panel within the panel from off to on

            //mainScreenLabel.setVisible(false);//toggles the original panel in the north from on to off

        });
        empToMain.addActionListener(e ->{

            empPortalBtn.setVisible(true);
            simBtn.setVisible(true);

            empCreateBtn.setVisible(false);
            empReadBtn.setVisible(false);
            empUpdateBtn.setVisible(false);
            empDeleteBtn.setVisible(false);

            empToMain.setVisible(false);

        });

        simToMain.addActionListener(e ->{

            empPortalBtn.setVisible(true);
            simBtn.setVisible(true);

            simCreateBtn.setVisible(false);
            simReadBtn.setVisible(false);
            simUpdateBtn.setVisible(false);
            simDeleteBtn.setVisible(false);

            simToMain.setVisible(false);

        });
    }



}