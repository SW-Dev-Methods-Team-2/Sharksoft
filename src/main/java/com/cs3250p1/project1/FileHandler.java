package com.cs3250p1.project1;

import java.util.ArrayList;
import java.util.List;

//Using this class to currently store any customer order received from opening
//a file, after sql connection is set up, we'll have the customer order
//directly go to the database
class CustomerOrderField{
    double date; //dates are long so needs double
    String email;
    String shipping_address;
    String product_id;
    int quantity;
    String printField(){
        return Double.toString(date)+","+email+","+shipping_address+","+
                product_id+","+Integer.toString(quantity);
    }
}
class SupplierOrderField{
    double date;
    String supplier_id;
    String product_id;
    int quantity;
    String printField(){
        return Double.toString(date)+","+supplier_id+","+product_id+","+Integer.toString(quantity);
    }
}

public class FileHandler {
    ArrayList<CustomerOrderField> parseCustomerOrder(List<String> lines){
        ArrayList<CustomerOrderField> table = new ArrayList<>();

        for (int i=0;i<lines.size();i++){
            //each item in this list is a single line from the string

            String thisLine= lines.get(i);//get a single line of string
            CustomerOrderField temp = new CustomerOrderField();

            int commaCounter=0; String str="", output="";
            for (int j=0; j<thisLine.length();j++) {
                str = thisLine.substring(j, j + 1);
                if (str.equals(" ")) {
                    continue;
                } //ignore spaces

                if (str.equals(",")) {
                    commaCounter++;
                    if (commaCounter == 1) {
                        temp.date = Double.parseDouble(output);
                        output = ""; //reset this variable
                        continue;
                    } else if (commaCounter == 2) {
                        temp.email = output;
                        output = "";
                        continue;
                    } else if (commaCounter == 3) {
                        temp.shipping_address = output;
                        output = "";
                        continue;
                    } else if (commaCounter == 4) {
                        temp.product_id = output;
                        output = "";
                        continue;
                    } else {
                    }
                }
                output = output + str;
            }//when loop ends only quantity is left over.
            temp.quantity = Integer.parseInt(output);
            output = ""; //just for safety
            table.add(temp); //add this new created field to storage
        }
        return table; //return the newly created data structure
    }

    ArrayList<SupplierOrderField> parseSupplierOrder(List<String> lines){
        ArrayList<SupplierOrderField> table = new ArrayList<>();

        for (int i=0;i<lines.size();i++){
            //each item in this list is a single line from the string

            String thisLine= lines.get(i);//get a single line of string
            SupplierOrderField temp = new SupplierOrderField();

            int commaCounter=0; String str="", output="";
            for (int j=0; j<thisLine.length();j++) {
                str = thisLine.substring(j, j + 1);
                if (str.equals(" ")) {
                    continue;
                } //ignore spaces

                if (str.equals(",")) {
                    commaCounter++;
                    if (commaCounter == 1) {
                        temp.date = Double.parseDouble(output);
                        output = ""; //reset this variable
                        continue;
                    } else if (commaCounter == 2) {
                        temp.supplier_id = output;
                        output = "";
                        continue;
                    } else if (commaCounter == 3) {
                        temp.product_id = output;
                        output = "";
                        continue;
                    } else {
                    }
                }
                output = output + str;
            }//when loop ends only quantity is left over.
            temp.quantity = Integer.parseInt(output);
            output = ""; //just for safety
            table.add(temp); //add this new created field to storage
        }
        return table; //return the newly created data structure
    }
}
