package com.cs3250p1.project1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

public class GUI{

    JFrame frame = new JFrame();
    // Variables declaration - do not modify
    javax.swing.JButton btnCrudCreate;
    javax.swing.JButton btnCrudDelete;
    javax.swing.JButton btnCrudUpdate;
    javax.swing.JButton btnCustomerOrder;
    javax.swing.JButton btnSupplierOrder;
    javax.swing.JCheckBox chBoxSimulationStatus;
    javax.swing.JScrollPane consoleBox;
    javax.swing.JTextArea consoleArea;
    javax.swing.JSeparator sep1;
    javax.swing.JSeparator sep2;
    javax.swing.JSeparator sep3;
    javax.swing.JScrollPane tableScrollPane;
    javax.swing.JComboBox<String> tableSelector;
    javax.swing.JLabel txtCrud;
    javax.swing.JLabel txtLoadBulkOrders;
    javax.swing.JLabel txtTable;

    ArrayList<GUIForm> formList = new ArrayList<GUIForm>();
    int formIndexCounter = 0;

    public GUI() {
        tableScrollPane = new javax.swing.JScrollPane();
        consoleBox = new javax.swing.JScrollPane();
        consoleArea = new javax.swing.JTextArea();
        tableSelector = new javax.swing.JComboBox<>();
        txtTable = new javax.swing.JLabel();
        txtCrud = new javax.swing.JLabel();
        sep1 = new javax.swing.JSeparator();
        btnCrudCreate = new javax.swing.JButton();
        btnCrudUpdate = new javax.swing.JButton();
        btnCrudDelete = new javax.swing.JButton();
        sep2 = new javax.swing.JSeparator();
        txtLoadBulkOrders = new javax.swing.JLabel();
        btnCustomerOrder = new javax.swing.JButton();
        btnSupplierOrder = new javax.swing.JButton();
        sep3 = new javax.swing.JSeparator();
        chBoxSimulationStatus = new javax.swing.JCheckBox();
    }

    void GUIFormatter(){//code generated by netbeans swing UI designer
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sep2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sep3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sep1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(txtTable)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tableSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnCrudCreate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCrudUpdate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCrudDelete)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCustomerOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSupplierOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCrud)
                            .addComponent(txtLoadBulkOrders)
                            .addComponent(chBoxSimulationStatus))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(consoleBox))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTable)
                    .addComponent(tableSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chBoxSimulationStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCrud)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrudCreate)
                    .addComponent(btnCrudUpdate)
                    .addComponent(btnCrudDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLoadBulkOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCustomerOrder)
                    .addComponent(btnSupplierOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consoleBox)
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
    }

     void GUIinit(JTable _table) {

        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableScrollPane.setViewportView(_table);

        consoleArea.setColumns(20);
        consoleArea.setRows(5);
        consoleBox.setViewportView(consoleArea);

        tableSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "cs3250main.sharktable", "cs3250main.sales_orders", "cs3250main.supplier_orders","cs3250main.simsharktable","cs3250main.simsales_orders","cs3250main.user_data","cs3250main.simuser_data"}));

        txtTable.setText("Table");

        txtCrud.setText("CRUD");

        btnCrudCreate.setText("Create");

        btnCrudUpdate.setText("Update");

        btnCrudDelete.setText("Delete");

        txtLoadBulkOrders.setText("Load Bulk Orders");

        btnCustomerOrder.setText("Customer Order");

        btnSupplierOrder.setText("Supplier Order");

        chBoxSimulationStatus.setText("Simulation Off");

        GUIFormatter(); //this has to be called at exactly this place

        frame.pack();
        frame.setVisible(true);
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
