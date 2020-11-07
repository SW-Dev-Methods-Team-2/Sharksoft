package com.cs3250p1.project1;

/**
 * @author Shane Bowman
 * @author Diptanshu Giri
 */

import javax.swing.*;
import java.awt.*;
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

public class GUI {

    JFrame frame = new JFrame();

    JPanel east = new JPanel();
    JPanel west = new JPanel();
    JPanel north = new JPanel();
    JPanel south = new JPanel();
    JPanel center = new JPanel();
    JPanel bottom = new JPanel();

    ArrayList<GUIForm> formList = new ArrayList<GUIForm>();
    int formIndexCounter = 0;

    TextArea mainTextArea = new TextArea("Welcome to the portal",20,120);
    JCheckBox chBoxSimulationMode = new JCheckBox("Simulation Mode");

    JButton btnCrudOpenCustomerOrder = new JButton("Open Customer Order");
    JButton btnCrudOpenSupplierOrder = new JButton("Open Supplier Order");
    JButton btnCrudCreate = new JButton("Create Product");
    JButton btnCrudRead = new JButton("Read Product");
    JButton btnCrudUpdate = new JButton("Update Product");
    JButton btnCrudDelete = new JButton("Delete Product");

    int addForm(String _title, String _keyString, int _fieldSize) {
        GUIForm temp = new GUIForm(_title, _keyString, _fieldSize);
        formList.add(temp);
        formIndexCounter++;
        return formIndexCounter - 1;
    }

    GUIForm getForm(int _formIndex) {
        return formList.get(_formIndex);
    }
    void setText(String _input){
        mainTextArea.setText(_input);
    }

    void setupGUI() { //this function should only be called after creating all the GUI elements needed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //without this, frame is hidden but
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setLocation(128,0);
        frame.setBackground(Color.decode("#ffffff"));
        
        north.add(mainTextArea);
        south.add(btnCrudCreate);
        south.add(btnCrudRead);
        south.add(btnCrudUpdate);
        south.add(btnCrudDelete);
        east.add(btnCrudOpenCustomerOrder);
        east.add(btnCrudOpenSupplierOrder);
        east.add(chBoxSimulationMode);


        frame.getContentPane().add(east, BorderLayout.EAST);
        frame.getContentPane().add(west, BorderLayout.WEST);
        frame.getContentPane().add(north, BorderLayout.NORTH);
        frame.getContentPane().add(south, BorderLayout.SOUTH);
        frame.getContentPane().add(center, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        }

    List<String> getStringFromFileDialog() {

        final JFileChooser fc = new JFileChooser();
        File file = null;
        List<String> lines = null;
        //this dialog needs a frame to live inside.
        //by storing which frame is active in a variable, we can open
        //this dialog properly in its designated frame
        int returnval = fc.showOpenDialog(frame);

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