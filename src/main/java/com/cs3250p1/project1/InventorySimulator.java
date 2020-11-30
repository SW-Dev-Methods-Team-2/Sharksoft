package com.cs3250p1.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

class Supplier {
    String supplierID;
    int supplierDay1;
    int supplierDay2;
    int supplierTime;
    boolean suppliedForTheDay = false;

    void setSupplier(ArrayList<String> _supplierList) {
        Random rand = new Random();
        supplierID = _supplierList.get(rand.nextInt(_supplierList.size())); // choose random supplier from list
        supplierDay1 = rand.nextInt(7); // days 0 to 6, 0 means monday
        while (supplierDay2 == supplierDay1) {
            supplierDay2 = rand.nextInt(7);
        }
        supplierTime = rand.nextInt(9); // choose hour index from 0 to 8, for eg, hrIndex 0 means 7pm.
        // random time from 7pm to 4am, rounded up to hourly, not minutes
        // supplier delivery is only done between 7pm and 4am.
    }
}

class Fields {
    // fields are product id, quantity, wholesale price, sale price, supplier id
    String productID;
    int quantity;
    int wholesalePrice;
    int salePrice;
    String supplier;

    void setFields(ArrayList<String> _supplierList) // pass in the supplier list
    {
        // first randomize the product name
        String vowels = "AEIOU";
        String consonents = "BCDFGHJKLMNPQRSTVWXYZ";
        String outputStr = "";
        Random rand = new Random();
        int pos = 0;
        for (int i = 0; i < 7; i++)// just generate 7 character strings for now
        {
            if (i == 1 || i == 4 || i == 5) {
                pos = rand.nextInt(vowels.length() - 1);
                outputStr += vowels.substring(pos, pos + 1);
                continue;
            }
            pos = rand.nextInt(consonents.length() - 1); // "-2" will prevent pos+1 from overshooting
            outputStr += consonents.substring(pos, pos + 1);
        }
        productID = outputStr;
        quantity = rand.nextInt(250); // quantity random from 0-250
        wholesalePrice = (int) (Math.random() * (250 - 50 + 1) + 50); // random from 50 to 250
        salePrice = (int) (Math.random() * (250 - 50 + 1) + 50); // random from 50 to 250
        supplier = _supplierList.get(rand.nextInt(_supplierList.size()));
    }
}

public class InventorySimulator {

    Random rand = new Random();
    String output = "";
    int timeSeconds = 0; // this time counts up, runs the simulation of certain buyer/supplier events
                         // getting
    // triggered at their respective times. Time resets every 86400 seconds, because
    // that is 24 hours, and a
    // new day needs to start
    int dayCounter = 0; // day 0 is monday, range from 0-6

    // database connection variables
    SimulatorSQLCommunicator simDB = new SimulatorSQLCommunicator("simsharktable"); // create the crud object
    Connection simConn; // connection object used to connect with DB
    String dbURL = "jdbc:mysql://cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
    String dbUsername = "admin";
    String dbPassword = "cs3250db1";

    StringStreamer stream1 = new StringStreamer(); // this streamer is used by simulator

    ArrayList<String> supplierID_list = new ArrayList<String>();

    int noOfProducts = 20;
    int companyAsset = 75000; // a sample dollar figure for company's money
    int totalSales = 0;
    int totalItemsSold = 0;
    int totalDeliveries = 0;
    int totalCost = 0;
    ArrayList<Fields> Inventory = new ArrayList<Fields>();

    void initializeSimulatorData() {// also establishes connection with database

        try {// initialize the connection here

            simConn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // generate suppliers list
        supplierID_list.add("ABC");
        supplierID_list.add("Desmond");
        supplierID_list.add("techtronics");
        supplierID_list.add("warriors");
        supplierID_list.add("paper-mache");
        supplierID_list.add("roadRoller");
        supplierID_list.add("Lamina");

        // generate local inventory database
        for (int i = 0; i < noOfProducts; i++) {
            Fields temp = new Fields();
            temp.setFields(supplierID_list);
            Inventory.add(temp);
        }

        createInventoryOnline(); //sync local database to sql
    }

    void createInventoryOnline(){//only call about initializeSimulatorData method
    //add inventory to the online database
        for (int i=0;i<noOfProducts;i++) {
            try {
                simDB.addProduct(simConn, Inventory.get(i).productID,
                Integer.toString(Inventory.get(i).quantity),
                Integer.toString(Inventory.get(i).wholesalePrice),
                Integer.toString(Inventory.get(i).salePrice),
                Inventory.get(i).supplier);
                stream1.push("Added "+Inventory.get(i).productID+"\n");
            } catch (SQLException ex) {
            ex.printStackTrace();
            }
        }
    }

    void clearInventoryOnline() { // call this when closing the program
        for (int i = 0; i < noOfProducts; i++) {
            try {
                simDB.delete(simConn, Inventory.get(i).productID);
                stream1.push("Deleted " + Inventory.get(i).productID + "\n");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    void printTable() {
        stream1.push("Product ID, Quantity, Wholesale Price, Sale price, Supplier ID, Day 1, Day 2, Time\n");
        for (int i = 0; i < noOfProducts; i++) {
            stream1.push((Inventory.get(i).productID + ", " + Inventory.get(i).quantity + ", "
                    + Inventory.get(i).wholesalePrice + ", " + Inventory.get(i).salePrice + ", "
                    + Inventory.get(i).supplier + "\n"));
        }
    }

    void processBuyer(int _timeSeconds) {

        // buyer handling code starts here
        stream1.push("\nCustomer order received! Time is " + _timeSeconds + "---------------\n");
        // since buyer will enter their own random preferences regarding the product...
        // we will randomize that as well for the sake of this simulation

        // randomly select a product out of the total products
        int selected = rand.nextInt(noOfProducts);
        int howMuchBought = 999; // track how much they want

        if (Inventory.get(selected).quantity > 0) {// if quantity of the selected inventory is not zero
            while (howMuchBought > Inventory.get(selected).quantity || howMuchBought == 0) {
                // make sure customer can buy 1 to 3 products
                howMuchBought = rand.nextInt(4);
            }
            int salesAmount = Inventory.get(selected).salePrice * howMuchBought;
            companyAsset += salesAmount; // add sales price to company asset
            totalSales += salesAmount;
            totalItemsSold += howMuchBought;
            Inventory.get(selected).quantity -= howMuchBought;

            stream1.push("Customer bought " + howMuchBought + " of " + Inventory.get(selected).productID + "\n");
            stream1.push("Total sales was $" + salesAmount + "\n");
            stream1.push("New quantity for " + Inventory.get(selected).productID + " is "
                    + Inventory.get(selected).quantity + "\n");
            stream1.push("Current assets are $" + companyAsset + "\n");
        } else {
            stream1.push("No more " + Inventory.get(selected).productID + " is left..." + "\n");
            stream1.push("Customer lost, end of order..." + "\n");
        }

        // now update this to online database
        int i = selected;
        try {
            simDB.update(simConn, Inventory.get(i).productID, Integer.toString(Inventory.get(i).quantity),
                    Integer.toString(Inventory.get(i).wholesalePrice), Integer.toString(Inventory.get(i).salePrice),
                    Inventory.get(i).supplier);
            stream1.push("<br>Online updated: " + Inventory.get(i).productID + "<br>");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void processSupplier(int _timeSeconds, int _dayCounter) {
        int i = 0;
        /*
         * while (i < noOfProducts) { if (!Inventory.get(i).supplier.suppliedForTheDay)
         * { //can't figure this out, leave for later if
         * (Inventory.get(i).supplier.supplierDay1 == _dayCounter ||
         * Inventory.get(i).supplier.supplierDay2 == _dayCounter) { int supplierTime =
         * Inventory.get(i).supplier.supplierTime; //if it's the right days if
         * (supplierTime == _timeSeconds / 3600) //if it's the right hour {
         * Inventory.get(i).supplier.suppliedForTheDay = true;
         */

        // choose a random product from inventory to be delivered.
        Random rand = new Random();
        i = rand.nextInt(noOfProducts); // random from 50 to 250
        stream1.push("<br>******Delivery arrived from " + Inventory.get(i).supplier + "******<br>");
        stream1.push("Time is " + timeSeconds + "<br>");
        stream1.push("Product delivered: " + Inventory.get(i).productID + "<br>");
        int noOfDelivery = (int) (Math.random() * (30 - 1 + 1) + 1);
        int costOfDelivery = (Inventory.get(i).wholesalePrice * noOfDelivery) / 5; // 5 item per wholesale
        stream1.push("Total delivered is " + noOfDelivery + " for $" + costOfDelivery + "<br>");
        companyAsset -= costOfDelivery;
        totalCost += costOfDelivery;
        Inventory.get(i).quantity += noOfDelivery;
        totalDeliveries += noOfDelivery;
        stream1.push("Company Asset is " + companyAsset + " and " + "we have " + Inventory.get(i).quantity + " of "
                + Inventory.get(i).productID + "<br>");
        // break;
        try {
            simDB.update(simConn, Inventory.get(i).productID, Integer.toString(Inventory.get(i).quantity),
                    Integer.toString(Inventory.get(i).wholesalePrice), Integer.toString(Inventory.get(i).salePrice),
                    Inventory.get(i).supplier);
            stream1.push("<br>Online updated: " + Inventory.get(i).productID + "<br>");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * } } i++; }
     */
    // }
    void resetAllSuppliers() {
        // Reset all supplier delivered status.
        for (int j = 0; j < noOfProducts; j++) {
            //Inventory.get(j).supplier.suppliedForTheDay = false;
        }
    }

    String printTotalResult() {
        String result = "";
        result += "<html>Total sales: $" + totalSales + " for " + totalItemsSold + " items" + "\n";
        result += "Total deliveries: " + totalDeliveries + "\n";
        result += "Total cost: $" + totalCost + "\n";
        result += "Company Asset: $" + companyAsset + "\n";
        return result;
    }

    String getStream() {
        return stream1.getStream();
    }

    int getTimeSeconds() {
        return timeSeconds;
    }

    int getDayCounter() {
        return dayCounter;
    }

    void runSim() {
        timeSeconds++; // add time

        // process the time
        if (timeSeconds >= 86400) {
            stream1.push("Day Over!-------------------------------------------");
            resetAllSuppliers();
            timeSeconds = 0;
            dayCounter++;
            if (dayCounter < 7)
                stream1.push("Starting day " + dayCounter);
        }
    }

}// end class description