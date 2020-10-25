package com.cs3250p1.project1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class GUIForm{ //one form stores one JPanel at a time
    String title="default";
    ArrayList<JLabel> keyList = new ArrayList<JLabel>();
    ArrayList<JTextField> valueList = new ArrayList<JTextField>();
    JPanel pnForm = new JPanel();
    int itemCounter=0; //gotta know how many items we have

    //this method accepts a string full of key values, creates a list of form elements,
    //then sets up a panel which can then be used to render into any JOptionPane later
    public GUIForm(String _title, String _keyString //a string with key strings seperated by semicolon
                    // eg. "product;quantity;wholesalePrice;...;" also has to end with a semicolon
            , int _fieldSize){
                title=_title;
        String output="",str="";
        for(int i=0;i<_keyString.length();i++){
            str=_keyString.substring(i,i+1); //check for ";"
            if (str.equals(";")){
                itemCounter++;
                keyList.add(new JLabel(output));
                output="";
                valueList.add(new JTextField(_fieldSize));
                continue; //to prevent inclusion of semicolon in our output string
            }
            output=output+str;
        }
        //now create an appropriate jpanel to house those form elements

        pnForm.setLayout(new GridLayout(itemCounter,2));
        for (int i=0;i<keyList.size();i++){
            pnForm.add(keyList.get(i));
            pnForm.add(valueList.get(i));
        }// add the keys and values one by one in a grid style
        //JPanel is ready for deployment!!
    }

    String getKeyValue(String _key){
        for(int i=0;i<keyList.size();i++){
            if (keyList.get(i).getText().equals(_key)){
                return valueList.get(i).getText();
            }
        }
        return "key not found";
    }
    void showFormDialog(){
        for (int i=0;i<valueList.size();i++){
            valueList.get(i).setText("");
        } //clear the form from previous entry
        JOptionPane.showMessageDialog(null,pnForm,title,JOptionPane.INFORMATION_MESSAGE);
    }
}

class Screen{
    JPanel east = new JPanel();
    JPanel west = new JPanel();
    JPanel north = new JPanel();
    JPanel south = new JPanel();
    JPanel center = new JPanel();
    JPanel bottom = new JPanel();

    JFrame frame = new JFrame();

    public Screen() { //does this constructor have to be public??
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //without this, frame is hidden but
        frame.setPreferredSize(new Dimension(1024, 768));
        //frame.setUndecorated(true); //remove windows border,we don't want user hitting the close button
        frame.setLocation(128,0);
        frame.setBackground(Color.decode("#ffffff"));
    }

    void packScreen(){//all objects have been added to their respective JPanels,
        //now bind those JPanels into appropriate positions on this JFrame, and pack the JFrame
        //this function should be called only after all objects have been added to their jpanels
        frame.getContentPane().add(east, BorderLayout.EAST);
        frame.getContentPane().add(west, BorderLayout.WEST);
        frame.getContentPane().add(north, BorderLayout.NORTH);
        frame.getContentPane().add(south, BorderLayout.SOUTH);
        frame.getContentPane().add(center, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(false);
    }

}

public class GUI {

    JFrame activeFrame = null; //store the reference to the frame that is currently active
    ArrayList<Screen> screenList = new ArrayList<Screen>();
    int screenIndexCounter = 0;

    ArrayList<JButton> buttonList = new ArrayList<JButton>();
    int buttonIndexCounter = 0;

    ArrayList<JLabel> labelList = new ArrayList<JLabel>();
    int labelIndexCounter = 0;

    ArrayList<GUIForm> formList = new ArrayList<GUIForm>();
    int formIndexCounter = 0;

    //creates a screen, adds it to the storage, and returns an index to identify the screen in other
    //functions to perform actions on it
    int addScreen() {
        Screen temp = new Screen();
        screenList.add(temp);
        screenIndexCounter++;
        return screenIndexCounter - 1;
    }


    //creates a button, adds it to the storage, and returns an index to identify the button in other
    //functions to perform actions on it
    int addButton(int _screenIndex, //which screen do you want the button to be in?
                  String _buttonPlace, //which jpanel of the screen? center, right, left..?
                  String _buttonName) { //name of the button

        JButton temp = new JButton(_buttonName);

     /*   temp.setBackground(Color.decode("#47d3ec")); //add light blue color background
        temp.setForeground(Color.decode("#b347ec"));
        temp.setBorder(BorderFactory.createEmptyBorder()); //remove borders
        temp.setPreferredSize(new Dimension(120,40));
        temp.setFont(new Font("Arial", Font.PLAIN,16));*/

        if (_buttonPlace == "center") {
            screenList.get(_screenIndex).center.add(temp);
        } else if (_buttonPlace == "south") {
            screenList.get(_screenIndex).south.add(temp);
        } else if (_buttonPlace == "east") {
            screenList.get(_screenIndex).east.add(temp);
        } else if (_buttonPlace == "west") {
            screenList.get(_screenIndex).west.add(temp);
        } else if (_buttonPlace == "north") {
            screenList.get(_screenIndex).north.add(temp);
        } else if (_buttonPlace == "bottom") {
            screenList.get(_screenIndex).bottom.add(temp);
        } else {
            screenList.get(_screenIndex).center.add(temp);
        }

        buttonList.add(temp);
        buttonIndexCounter++;
        return buttonIndexCounter - 1;

    }

    //creates a label, adds it to the storage, and returns an index to identify the label in other
    //functions to perform actions on it
    int addLabel(int _screenIndex, //which screen do you want the label to be in?
                 String _labelPlace, //which jpanel of the screen? center, right, left..?
                 String _labelName) { //name of the label

        JLabel temp = new JLabel(_labelName);

        if (_labelPlace == "center") {
            screenList.get(_screenIndex).center.add(temp);
        } else if (_labelPlace == "south") {
            screenList.get(_screenIndex).south.add(temp);
        } else if (_labelPlace == "east") {
            screenList.get(_screenIndex).east.add(temp);
        } else if (_labelPlace == "west") {
            screenList.get(_screenIndex).west.add(temp);
        } else if (_labelPlace == "north") {
            screenList.get(_screenIndex).north.add(temp);
        } else if (_labelPlace == "bottom") {
            screenList.get(_screenIndex).bottom.add(temp);
        } else {
            screenList.get(_screenIndex).center.add(temp);
        }

        labelList.add(temp);
        labelIndexCounter++;
        return labelIndexCounter - 1;
    }

    int addForm(String _title, String _keyString, int _fieldSize) {
        GUIForm temp = new GUIForm(_title, _keyString, _fieldSize);
        formList.add(temp);
        formIndexCounter++;
        return formIndexCounter - 1;
    }

    GUIForm getForm(int _formIndex) {
        return formList.get(_formIndex);
    }


    void setText(int _labelIndex, //the jlabel that you want to change
                 String _text //the text you want to change it to
    ) {
        labelList.get(_labelIndex).setText(_text);
    }

    JButton getButton(int _buttonIndex) {
        return buttonList.get(_buttonIndex);
    }

    void setupGUI() { //this function should only be called after creating all the GUI elements needed
        for (int i = 0; i < screenList.size(); i++) {
            screenList.get(i).packScreen(); //pack each individual screen in the list
        }

    }

    void screenChangeInto(int _screenIndex) { //only call this function after setupGUI has been called
        for (int i = 0; i < screenList.size(); i++) { //make all the screen invisible
            screenList.get(i).frame.setVisible(false);
        }
        screenList.get(_screenIndex).frame.setVisible(true); //make the specified screen visible
        activeFrame = screenList.get(_screenIndex).frame; //put this active frame into reference
    }

    List<String> getStringFromFileDialog() {

        final JFileChooser fc = new JFileChooser();
        File file = null;
        List<String> lines = null;
        //this dialog needs a frame to live inside.
        //by storing which frame is active in a variable, we can open
        //this dialog properly in its designated frame
        int returnval = fc.showOpenDialog(activeFrame);

        if (returnval == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();

            System.out.println("This is the file path: " + file.getAbsolutePath());
            Path path = Paths.get(file.getAbsolutePath()); //get the path for the file, I don't know how file system
            //works on mac, so keep all files local
            try {
                lines = Files.readAllLines(path);
                for (int i = 0; i < lines.size(); i++) {
                    System.out.println(lines.get(i));

                }
                return lines;
            } catch (IOException e) {
                System.out.println("Could not read file: " + e);

            }
        }
        return lines;
    }
}