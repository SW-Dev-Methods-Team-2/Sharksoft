import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        frame.setPreferredSize(new Dimension(640, 480));
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

    ArrayList<Screen> screenList = new ArrayList<Screen>();
    int screenIndexCounter=0;

    ArrayList<JButton> buttonList = new ArrayList<JButton>();
    int buttonIndexCounter=0;

    ArrayList<JLabel> labelList = new ArrayList<JLabel>();
    int labelIndexCounter=0;

    //creates a screen, adds it to the storage, and returns an index to identify the screen in other
    //functions to perform actions on it
    int addScreen(){
        Screen temp = new Screen();
        screenList.add(temp);
        screenIndexCounter++;
        return screenIndexCounter-1;
    }


    //creates a button, adds it to the storage, and returns an index to identify the button in other
    //functions to perform actions on it
    int addButton(int _screenIndex, //which screen do you want the button to be in?
                  String _buttonPlace, //which jpanel of the screen? center, right, left..?
                  String _buttonName){ //name of the button

        JButton temp = new JButton(_buttonName);

     /*   temp.setBackground(Color.decode("#47d3ec")); //add light blue color background
        temp.setForeground(Color.decode("#b347ec"));
        temp.setBorder(BorderFactory.createEmptyBorder()); //remove borders
        temp.setPreferredSize(new Dimension(120,40));
        temp.setFont(new Font("Arial", Font.PLAIN,16));*/

        if (_buttonPlace=="center"){
            screenList.get(_screenIndex).center.add(temp);
        }
        else if(_buttonPlace=="south"){
            screenList.get(_screenIndex).south.add(temp);
        }
        else if(_buttonPlace=="east"){
            screenList.get(_screenIndex).east.add(temp);
        }
        else if(_buttonPlace=="west"){
            screenList.get(_screenIndex).west.add(temp);
        }
        else if(_buttonPlace=="north"){
            screenList.get(_screenIndex).north.add(temp);
        }
        else if(_buttonPlace=="bottom") {
            screenList.get(_screenIndex).bottom.add(temp);
        }
        else{
            screenList.get(_screenIndex).center.add(temp);
        }

        buttonList.add(temp);
        buttonIndexCounter++;
        return buttonIndexCounter-1;

    }
    //creates a label, adds it to the storage, and returns an index to identify the label in other
    //functions to perform actions on it
    int addLabel(int _screenIndex, //which screen do you want the label to be in?
                  String _labelPlace, //which jpanel of the screen? center, right, left..?
                  String _labelName){ //name of the label

        JLabel temp = new JLabel(_labelName);

        if (_labelPlace=="center"){
            screenList.get(_screenIndex).center.add(temp);
        }
        else if(_labelPlace=="south"){
            screenList.get(_screenIndex).south.add(temp);
        }
        else if(_labelPlace=="east"){
            screenList.get(_screenIndex).east.add(temp);
        }
        else if(_labelPlace=="west"){
            screenList.get(_screenIndex).west.add(temp);
        }
        else if(_labelPlace=="north"){
            screenList.get(_screenIndex).north.add(temp);
        }
        else if(_labelPlace=="bottom") {
            screenList.get(_screenIndex).bottom.add(temp);
        }
        else{
            screenList.get(_screenIndex).center.add(temp);
        }

        labelList.add(temp);
        labelIndexCounter++;
        return labelIndexCounter-1;
    }

    void setText(int _labelIndex, //the jlabel that you want to change
                 String _text //the text you want to change it to
                 ){
        labelList.get(_labelIndex).setText(_text);
    }

    JButton getButton(int _buttonIndex){
        return buttonList.get(_buttonIndex);
    }

    void setupGUI(){ //this function should only be called after creating all the GUI elements needed
        for (int i=0;i<screenList.size();i++){
            screenList.get(i).packScreen(); //pack each individual screen in the list
        }
    }

    void screenChangeInto(int _screenIndex){ //only call this function after setupGUI has been called
        for (int i=0;i<screenList.size();i++){ //make all the screen invisible
            screenList.get(i).frame.setVisible(false);
        }
        screenList.get(_screenIndex).frame.setVisible(true); //make the specified screen visible
    }

}
