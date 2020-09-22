import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Supplier{
    String supplierID;
    int supplierDay1;
    int supplierDay2;
    int supplierTime;
    boolean suppliedForTheDay=false;
    void setSupplier(ArrayList<String> _supplierList){
        Random rand = new Random();
        supplierID = _supplierList.get(rand.nextInt(_supplierList.size())); //choose random supplier from list
        supplierDay1 = rand.nextInt(7); //days 0 to 6, 0 means monday
        while (supplierDay2==supplierDay1)
        {
            supplierDay2 = rand.nextInt(7);
        }
        supplierTime = rand.nextInt(9); //choose hour index from 0 to 8, for eg, hrIndex 0 means 7pm.
        //random time from 7pm to 4am, rounded up to hourly, not minutes
        //supplier delivery is only done between 7pm and 4am.
    }
}
class Fields {
    //fields are product id, quantity, wholesale price, sale price, supplier id
    String productID;
    int quantity;
    int wholesalePrice;
    int salePrice;
    Supplier supplier=new Supplier();

    void setFields(ArrayList<String> _supplierList) // pass in the supplier list
    {
        //first randomize the product name
        String vowels = "AEIOU";
        String consonents = "BCDFGHJKLMNPQRSTVWXYZ";
        String outputStr = "";
        Random rand = new Random();
        int pos=0;
        for (int i = 0; i < 7; i++)//just generate 7 character strings for now
        {
            if (i==1 || i==4 || i ==5) {
                pos = rand.nextInt(vowels.length() - 1);
                outputStr += vowels.substring(pos, pos + 1);
                continue;
            }
            pos = rand.nextInt(consonents.length() - 1); //"-2" will prevent pos+1 from overshooting
            outputStr += consonents.substring(pos, pos + 1);
        }
        productID = outputStr;
        quantity = rand.nextInt(250); //quantity random from 0-250
        wholesalePrice = (int) (Math.random() * (250 - 50 + 1) + 50); //random from 50 to 250
        salePrice = (int) (Math.random() * (250 - 50 + 1) + 50); //random from 50 to 250
        supplier.setSupplier(_supplierList);
    }
}
public class InventorySimulator {
    public static void main (String[] args)
    {
        Random rand = new Random();
        System.out.println("Create a random inventory of 20 items for testing");

        ArrayList<String> supplierID_list = new ArrayList<String>();
        supplierID_list.add("ABC");
        supplierID_list.add("Desmond");
        supplierID_list.add("techtronics");
        supplierID_list.add("warriors");
        supplierID_list.add("paper-mache");
        supplierID_list.add("roadRoller");
        supplierID_list.add("Lamina");

        int noOfProducts=20;
        int companyAsset=75000; //a sample dollar figure for company's money
        int totalSales=0;
        int totalItemsSold=0;
        int totalDeliveries=0;
        int totalCost=0;
        ArrayList<Fields> Inventory = new ArrayList<Fields>();

        for (int i=0;i<noOfProducts;i++)
        {
            Fields temp = new Fields();
            temp.setFields(supplierID_list);
            Inventory.add(temp);
        }

        System.out.println("Product ID, Quantity, Wholesale Price, Sale price, Supplier ID, Day 1, Day 2, Time");
        for (int i=0;i<noOfProducts;i++) {
            System.out.println(Inventory.get(i).productID + ", " + Inventory.get(i).quantity
                    + ", " + Inventory.get(i).wholesalePrice + ", " + Inventory.get(i).salePrice + ", "
                    + Inventory.get(i).supplier.supplierID+", "+Inventory.get(i).supplier.supplierDay1+", "
            +Inventory.get(i).supplier.supplierDay2+", "+Inventory.get(i).supplier.supplierTime);
        }

        int timeSeconds=0; //this time counts up, runs the simulation of certain buyer/supplier events getting
        //triggered at their respective times. Time resets every 86400 seconds, because that is 24 hours, and a
        //new day needs to start
        int dayCounter=0; //day 0 is monday, range from 0-6

        boolean simulationOver=false;

        while(!simulationOver)
        {
            Scanner sc = new Scanner(System.in);
            //randomize a chance of receiving a customer at any time. For now, 1 to 1000 chances of landing an order.
            if (rand.nextInt(10000) == 100) {

                //buyer handling code starts here
                System.out.println("Customer order received! Time is "+timeSeconds+"---------------");
                //since buyer will enter their own random preferences regarding the product...
                //we will randomize that as well for the sake of this simulation

                //randomly select a product out of the total products
                int selected = rand.nextInt(noOfProducts);
                int howMuchBought = 999; //track how much they want

                if (Inventory.get(selected).quantity > 0) {//if quantity of the selected inventory is not zero
                    while (howMuchBought > Inventory.get(selected).quantity || howMuchBought==0) {
                        //make sure customer can buy 1 to 3 products
                        howMuchBought = rand.nextInt(4);
                    }
                    int salesAmount = Inventory.get(selected).salePrice * howMuchBought;
                    companyAsset += salesAmount; //add sales price to company asset
                    totalSales+=salesAmount;
                    totalItemsSold+=howMuchBought;
                    Inventory.get(selected).quantity -= howMuchBought;

                    //System.out.println( the results
                    System.out.println( "Customer bought " + howMuchBought + " of "
                            + Inventory.get(selected).productID);
                    System.out.println( "Total sales was $" + salesAmount);
                    System.out.println( "New quantity for " + Inventory.get(selected).productID +
                            " is " + Inventory.get(selected).quantity);
                    System.out.println( "Current assets are $" + companyAsset);
                    System.out.println("Press <enter> to continue...");
                    //String temp = sc.nextLine();
                } else {
                    System.out.println( "No more " + Inventory.get(selected).productID + " is left...");
                    System.out.println( "Customer lost, end of order...");
                    System.out.println("Press <enter> to continue...");
                    //String temp = sc.nextLine();
                }
            }
                //SUPPLIER CODE BLOCK-------------------------------------

                //now check if current time matches any of the supplier's delivery time
                //run a loop through the inventory list to see which supplier is gonna deliver
                //this loop is only for the sake of this simulation, supplier delivery events need to be
                //done in another thread, and event interrupt received in real time. It's too much time
                //to go through 20000 product entries.

                int i = 0;
                while (i < noOfProducts) {
                    if (Inventory.get(i).supplier.suppliedForTheDay) {
                        i++;
                        continue;
                    }
                    if (Inventory.get(i).supplier.supplierDay1 == dayCounter ||
                            Inventory.get(i).supplier.supplierDay2 == dayCounter) {
                        int supplierTime = Inventory.get(i).supplier.supplierTime; //if it's the right days
                        if (supplierTime == timeSeconds/3600) //if it's the right hour
                        {
                            Inventory.get(i).supplier.suppliedForTheDay = true;
                            System.out.println("******Delivery arrived from " + Inventory.get(i).supplier.supplierID+"******");
                            System.out.println("Time is "+timeSeconds);
                            System.out.println("Product delivered: " + Inventory.get(i).productID);
                            int noOfDelivery = (int) (Math.random() * (30 - 1 + 1) + 1);
                            int costOfDelivery = (Inventory.get(i).wholesalePrice * noOfDelivery) / 5; //5 item per wholesale
                            System.out.println("Total delivered is " + noOfDelivery + " for $" + costOfDelivery);
                            companyAsset -= costOfDelivery;
                            totalCost+=costOfDelivery;
                            Inventory.get(i).quantity += noOfDelivery;
                            totalDeliveries+=noOfDelivery;
                            System.out.println("Company Asset is " + companyAsset + " and " +
                                    "we have " + Inventory.get(i).quantity + " of " + Inventory.get(i).productID);
                            break;
                        }
                    }
                    i++;
                }

                //timeSeconds handler code block
                timeSeconds+=1;
                if (timeSeconds >= 86400){
                    System.out.println("Day Over!-------------------------------------------");
                    //Reset all supplier delivered status.
                    for (int j=0;j<noOfProducts;j++)
                    {
                        Inventory.get(j).supplier.suppliedForTheDay=false;
                    }
                    timeSeconds=0;
                    dayCounter++;
                    if (dayCounter<7) System.out.println("Starting day "+dayCounter);
                }
                //...advance day counter each time the seconds hits 86400, then reset the seconds
                //...if day reaches 6, then simulation is over. This program simulates a week at a time.
                if (dayCounter > 6) break;

        }//end simulation loop
        System.out.println("End of Week Report:");
        System.out.println("Total sales: $"+totalSales+" for "+totalItemsSold+" items");
        System.out.println("Total deliveries: "+totalDeliveries);
        System.out.println("Total cost: $"+totalCost);
        System.out.println("Company Asset: $"+companyAsset);
        System.out.println("Simulation Over!");

    }//end main

}// end class description
