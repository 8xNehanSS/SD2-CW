package com.example.javfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.getIcons().add(icon);
        stage.setTitle("Foodies Fave Food Center");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static int[] BurgersSold = new int[3];
    private static int BurgerStock = 50;
    public static FoodQueue[] Queue = new FoodQueue[]{
            new FoodQueue(2),
            new FoodQueue(3),
            new FoodQueue(5)
    };
    public static FoodQueue WaitingLIST = new FoodQueue(10);

    public static void main(String[] args) {

        //defining and initializing the variables and arrays
        String option;

        //main while loop to loop the entire program
        while(true) {
            //checking if burger stocks are low
            if(BurgerStock<=10) {
                System.out.println("Warning, Burger stocks are low!\n");
            }

            //printing the menu of the food center
            printmenu();

            //getting the input from user and checking if the user wants to exit or proceeds to the below switch
            System.out.print("Enter the option: ");
            option = scanner.nextLine();
            if(option.equals("999")||option.equals("EXT")) {
                System.out.println("Program Exiting..");
                break;
            }

            //switch containing all the methods which are the menu options
            switch (option) {
                case "100","VFQ":
                    viewallqueues(Queue);
                    break;
                case "101","VEQ":
                    viewemptyqueues(Queue);
                    break;
                case "102","ACQ":
                    addcustomer(Queue, WaitingLIST, 1, null);
                    break;
                case "103","RCQ":
                    removecustomer(Queue, WaitingLIST);
                    break;
                case "104","PCQ":
                    servecustomer(Queue, WaitingLIST);
                    break;
                case "105","VCS":
                    viewcustomersort(Queue);
                    break;
                case "106","SPD":
                    storequeuedata(Queue);
                    break;
                case "107","LPD":
                    loadqueuedata(Queue, WaitingLIST);
                    break;
                case "108","STK":
                    viewburgerstock();
                    break;
                case "109","AFS":
                    addburgerstock();
                    break;
                case "110","IFS":
                    CheckCashierIncome();
                    break;
                case "112","GUI":
                    System.out.println("GUI Launched!\n");
                    launchGUI();
                    break;
                default:
                    System.out.println("Invalid Menu Option\n");
                    break;
            }

        }

    }

    public static void launchGUI() {
        Thread guiThread = new Thread(() -> {
            try {
                launch(); // Launch the JavaFX Application
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        guiThread.start();
    }

    public static void Check(FoodQueue WaitingLIST) {
        Customer[] WaitingArray = WaitingLIST.getQueue();
        for(Customer element:WaitingArray){
            if(element!=null){
                System.out.println(element.getName());
            }
        }
    }

    public static void CheckCashierIncome() {
        int IncomeONE = BurgersSold[0]*650;
        int IncomeTWO = BurgersSold[1]*650;
        int IncomeTHREE = BurgersSold[2]*650;
        System.out.println("\nIncome from the Cashiers:\n\nCashier One = Rs."+IncomeONE);
        System.out.println("Cashier One = Rs."+IncomeTWO);
        System.out.println("Cashier One = Rs."+IncomeTHREE);
        System.out.println();
    }
    //method to print the food center menu
    public static void printmenu() {
        System.out.println("Foodies Fave Food Center!\n\n"+
                "Menu Options:\n"+
                "100 or VFQ: View all Queues.\n"+
                "101 or VEQ: View all Empty Queues.\n" +
                "102 or ACQ: Add customer to a Queue.\n" +
                "103 or RCQ: Remove a customer from a Queue.\n" +
                "104 or PCQ: Remove a served customer.\n" +
                "105 or VCS: View Customers Sorted in alphabetical order.\n" +
                "106 or SPD: Store Program Data into file.\n" +
                "107 or LPD: Load Program Data from file.\n" +
                "108 or STK: View Remaining burgers Stock.\n" +
                "109 or AFS: Add burgers to Stock.\n" +
                "110 or IFS: Check Income of Cashiers.\n"+
                "112 or GUI: Launch the GUI.\n"+
                "999 or EXT: Exit the Program.\n");
    }

    //method to print the cashier queue using for a loop
    public static void viewallqueues(FoodQueue[] Queue) {
        Customer[] CashierONE = Queue[0].getQueue();
        Customer[] CashierTWO = Queue[1].getQueue();
        Customer[] CashierTHREE = Queue[2].getQueue();

        System.out.println();
        System.out.println("***************\n*** Cashier ***\n***************");
        System.out.println("  C1  C2  C3");
        for (int i = 0; i < 5; i++) {
            String value1 = " ";
            String value2 = " ";
            String value3 = "";
            if(i<CashierONE.length) {if(i<2){if(CashierONE[i]!=null) { value1 = "O"; } else { value1 = "X";}}}
            if(i<CashierTWO.length) {if(i<3){if(CashierTWO[i]!=null) { value2 = "O"; } else { value2 = "X";}}}
            if(i<CashierTHREE.length) {if(i<5){if(CashierTHREE[i]!=null) { value3 = "O"; } else { value3 = "X";}}}

            System.out.println("   "+value1+"   "+value2+"   "+value3);
        }
        System.out.println("X - Not Occupied\nO - Occupied\n");
    }

    //looping through the array containing the cashiers and finding if any of the cashiers are empty
    public static void viewemptyqueues(FoodQueue[] Queue) {
        Customer[] CashierONE = Queue[0].getQueue();
        Customer[] CashierTWO = Queue[1].getQueue();
        Customer[] CashierTHREE = Queue[2].getQueue();

        System.out.println();

        boolean isCashierOneEmpty = isEmptyQueue(CashierONE);
        boolean isCashierTwoEmpty = isEmptyQueue(CashierTWO);
        boolean isCashierThreeEmpty = isEmptyQueue(CashierTHREE);

        if (isCashierOneEmpty) {
            System.out.println("Cashier One is empty");
        }
        if (isCashierTwoEmpty) {
            System.out.println("Cashier Two is empty");
        }
        if (isCashierThreeEmpty) {
            System.out.println("Cashier Three is empty");
        }
        if (!isCashierOneEmpty && !isCashierTwoEmpty && !isCashierThreeEmpty) {
            System.out.println("None of the cashiers are empty");
        }

        System.out.println();
    }

    private static boolean isEmptyQueue(Customer[] queue) {
        for (Customer element : queue) {
            if (element != null) {
                return false;
            }
        }
        return true;
    }

    public static int CheckSlots(Customer[] Queue) {
        int count = 0;
        for(Customer element:Queue) {
            if(element!=null) {
                count++;
            }
        }
        return count;
    }

    public static int CheckMinCustomers(int FreeSlots1, int FreeSlots2, int FreeSlots3) {
        int cashier = 1;
        if (FreeSlots2 < FreeSlots1 || FreeSlots1 == 2) {
            cashier = 2;
        }

        if (FreeSlots3 < FreeSlots2 || FreeSlots2 == 3) {
            cashier = 3;
        }
        if (FreeSlots1 == 1) {
            cashier = 1;
        }
        return cashier;
    }

    //method to add customers to queue
    public static void addcustomer (FoodQueue[] Queue, FoodQueue WaitingLIST, int type, Customer newCustomer) {
        Customer[] CashierONE = Queue[0].getQueue();
        Customer[] CashierTWO = Queue[1].getQueue();
        Customer[] CashierTHREE = Queue[2].getQueue();
        Customer[] WaitingQueue = WaitingLIST.getQueue();

        int FreeSlots1 = CheckSlots(CashierONE);
        int FreeSlots2 = CheckSlots(CashierTWO);
        int FreeSlots3 = CheckSlots(CashierTHREE);
        int FreeSlotsWaitingLIST = CheckSlots(WaitingQueue);
        if(FreeSlotsWaitingLIST==10){
            System.out.println("Waiting list also full, Try again later!");
            return;
        }

        if(type==1) {
            System.out.print("\n");
            System.out.print("Enter first name: ");
            String FirstName = scanner.next();
            System.out.print("Enter second name: ");
            String LastName = scanner.next();
            System.out.print("Enter required burgers: ");
            int RequiredBurgers = scanner.nextInt();
            scanner.nextLine();
            newCustomer = new Customer(FirstName, LastName, RequiredBurgers);
            if (FreeSlots3 + FreeSlots2 + FreeSlots1 == 10) {
                System.out.println("All cashiers are full!");
                for(int i=0; i<WaitingQueue.length; i++) {
                    if(WaitingQueue[i]==null) {
                        WaitingQueue[i] = newCustomer;
                        System.out.println("Customer added to waiting list!\n");
                        break;
                    }
                }
                return;
            }
        }

        int cashier = CheckMinCustomers(FreeSlots1, FreeSlots2, FreeSlots3);

        if(cashier == 1){
            Queue[0].setCustomer(newCustomer);
            if(type==1) {
                System.out.println("Customer added to the Queue in Cashier One");
            } else if(type==2){
                System.out.println("Customer added to Queue from Waiting list to Cashier One");
            }
        }
        if(cashier == 2){
            Queue[1].setCustomer(newCustomer);
            if(type==1) {
                System.out.println("Customer added to the Queue in Cashier Two");
            } else if(type==2){
                System.out.println("Customer added to Queue from Waiting list to Cashier Two");
            }
        }
        if(cashier == 3){
            Queue[2].setCustomer(newCustomer);
            if(type==1) {
                System.out.println("Customer added to the Queue in Cashier Three");
            } else if(type==2){
                System.out.println("Customer added to Queue from Waiting list to Cashier Three");
            }
        }
        if(type==1){System.out.println();}
    }

    //method to remove the customer from the queue
    public static void removecustomer (FoodQueue[] Queue, FoodQueue WaitingLIST) {

        System.out.print("\n");
        while(true) {
            System.out.print("");
            //intializing the variables and getting the inputs
            int Cashier;
            int CashierSlot;
            try {
                System.out.print("Enter Cashier Number (1,2,3): ");
                Cashier = scanner.nextInt();
                scanner.nextLine();
                //if input is 999 user can exit the process
                if(Cashier==999) {
                    System.out.println("Exiting Remove Process");
                    break;
                }
                if(Cashier==1) { System.out.print("Enter here (Max 2): ");}
                if(Cashier==2) { System.out.print("Enter here (Max 3): ");}
                if(Cashier==3) { System.out.print("Enter here (Max 5): ");}
                CashierSlot = scanner.nextInt();
                scanner.nextLine();
            }
            catch(Exception e) {
                System.out.println("Enter Cashier & Clot using only numbers!");
                continue;
            }
            //if input is 999 user can exit the process
            if(CashierSlot==999) {
                System.out.println("Exiting Remove Process");
                break;
            }
            //passing the values to a submethod to remove the customers accordingly
            if(Cashier==1) {
                Queue[0].RemoveCustomer(Cashier,CashierSlot);
                Customer WaitingCustomer = GetWaitingCustomer(WaitingLIST);
                if(WaitingCustomer!=null){
                    addcustomer(Queue, WaitingLIST, 2, WaitingCustomer);
                }
                break;
            } else if(Cashier==2) {
                Queue[1].RemoveCustomer(Cashier,CashierSlot);
                Customer WaitingCustomer = GetWaitingCustomer(WaitingLIST);
                if(WaitingCustomer!=null){
                    addcustomer(Queue, WaitingLIST, 2, WaitingCustomer);
                }
                break;
            } else if(Cashier==3) {
                Queue[2].RemoveCustomer(Cashier,CashierSlot);
                Customer WaitingCustomer = GetWaitingCustomer(WaitingLIST);
                if(WaitingCustomer!=null){
                    addcustomer(Queue, WaitingLIST, 2, WaitingCustomer);
                }
                break;
            } else {
                System.out.println("Invalid Inputs");
            }
        }
        System.out.println();
    }

    public static Customer GetWaitingCustomer(FoodQueue WaitingLIST) {
        Customer[] WaitingArray = WaitingLIST.getQueue();
        for(int j=0; j<WaitingArray.length;j++) {
            if(WaitingArray[j]!=null){
                Customer Details = WaitingArray[j];
                WaitingArray[j] = null;
                for (int i =0; i < (WaitingArray.length - 1); i++) {
                    if (WaitingArray[i]==null) {
                        Customer temp = WaitingArray[i + 1];
                        WaitingArray[i + 1] = WaitingArray[i];
                        WaitingArray[i] = temp;
                    }
                }
                return Details;
            }
        }
        return null;
    }

    //method to serve burgers to customers
    public static void servecustomer(FoodQueue[] Queue, FoodQueue WaitingLIST) {
        System.out.print("\n");
        int servedBurgers = 0;
        while(true) {
            System.out.print("");
            //getting the cashier input
            int cashiername;
            try {
                System.out.print("Enter Cashier Number: ");
                cashiername = scanner.nextInt();
                scanner.nextLine();
                if(cashiername==999) {
                    System.out.println("Exiting Serve Process\n");
                    break;
                }
            }
            catch(Exception e) {
                System.out.println("Enter Cashier Using Numbers!");
                continue;
            }
            //passing the values to the sub method in the corresponding cashier
            if(cashiername==1) {
                servedBurgers = Queue[0].ServeCustomer(BurgerStock);
                BurgersSold[0] += servedBurgers;
                BurgerStock -= servedBurgers;
                Customer WaitingCustomer = GetWaitingCustomer(WaitingLIST);
                if(WaitingCustomer!=null){
                    addcustomer(Queue, WaitingLIST, 2, WaitingCustomer);
                }
                break;
            } else if(cashiername==2) {
                servedBurgers = Queue[1].ServeCustomer(BurgerStock);
                BurgersSold[1] += servedBurgers;
                BurgerStock -= servedBurgers;
                Customer WaitingCustomer = GetWaitingCustomer(WaitingLIST);
                if(WaitingCustomer!=null){
                    addcustomer(Queue, WaitingLIST, 2, WaitingCustomer);
                }
                break;
            } else if(cashiername==3) {
                servedBurgers = Queue[2].ServeCustomer(BurgerStock);
                BurgersSold[2] += servedBurgers;
                BurgerStock -= servedBurgers;
                Customer WaitingCustomer = GetWaitingCustomer(WaitingLIST);
                if(WaitingCustomer!=null){
                    addcustomer(Queue, WaitingLIST, 2, WaitingCustomer);
                }
                break;
            } else {
                System.out.println("Invalid Cashier Number! Enter 999 to exit.");
            }
        }
    }

    //method to print the remaining burgers
    public static void viewburgerstock () {
        System.out.println();
        System.out.println(BurgerStock+" : Burgers remaining");
        System.out.println();
    }

    //method to sort the customer names
    public static void viewcustomersort (FoodQueue[] Queue) {
        //adding the customer names to a new array

        Customer[] CashierONE = Queue[0].getQueue();
        Customer[] CashierTWO = Queue[1].getQueue();
        Customer[] CashierTHREE = Queue[2].getQueue();

        Customer[][] AllCashiers = {CashierONE, CashierTWO, CashierTHREE};
        String[] customers = new String[10];
        int count = 0;
        for(Customer[] Cashier:AllCashiers) {
            for(Customer element:Cashier) {
                if(element!=null){ customers[count] = element.getName(); }
                else { customers[count] = "-"; }
                count++;
            }
        }

        //sorting processs by passing to submethod
        for (int i = 0; i < customers.length - 1; i++) {
            for (int j = 0; j < customers.length - i - 1; j++) {
                if (Compare_STRINGS(customers[j].toUpperCase(), customers[j + 1].toUpperCase()) > 0) {
                    String temp = customers[j];
                    customers[j] = customers[j + 1];
                    customers[j + 1] = temp;
                }
            }
        }
        //checking if there are any customers if there is no customers then printing special statement
        int temp = 0;
        for(String element1:customers) {
            if(element1.equals("-")){
                temp = 1;
            } else {
                temp = 0;
            }
        }
        //printing the sorted customer list
        if(temp==0){
            System.out.println("List of customers in Queue:");
            for(String element:customers){
                if(!element.equals("-")) {
                    System.out.print(element+", ");
                }
            }
        } else {
            System.out.println("No customers to show");
        }

        System.out.println("\n");
    }

    //custom submethod to sort the passed strings
    public static int Compare_STRINGS(String s1, String s2) {
        // Custom comparison logic without using built-in methods
        int minLength = Math.min(s1.length(), s2.length());
        for (int i = 0; i < minLength; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 != c2) {
                return c1 - c2;
            }
        }

        // If the common prefix is equal, compare based on length
        return s1.length() - s2.length();
    }

    //method to add burgers to stock
    public static void addburgerstock() {
        System.out.println();
        //getting the burger input value
        while(true) {
            int burgers;
            try {
                System.out.print("Enter amount of burgers to add: ");
                burgers = scanner.nextInt();
                scanner.nextLine();
            }
            catch(Exception e) {
                System.out.println("Enter burgers amount using numbers!");
                continue;
            }
            BurgerStock += burgers;
            System.out.println(burgers+" Burgers added to Stock");
            System.out.println();
            break;
        }
    }

    //method to store data to text file
    public static void storequeuedata(FoodQueue[] Queue) {

        //prompt to check if user wants to store the data
        System.out.println("Storing will delete existing data. Are you sure? (Y): ");
        String valid = scanner.nextLine();
        if (!valid.equalsIgnoreCase("Y")) {
            System.out.println("Exiting store process\n");
            return;
        }

        Customer[] CashierONE = Queue[0].getQueue();
        Customer[] CashierTWO = Queue[1].getQueue();
        Customer[] CashierTHREE = Queue[2].getQueue();

        Customer[][] cashier = {CashierONE, CashierTWO, CashierTHREE};
        //defining writer object and writing the values to the textfile
        try {
            FileWriter myWriter = new FileWriter("cashierdata.txt");
            //writing the data
            for(Customer[] element:cashier) {
                for(Customer element1:element){
                    String name = "null null";
                    int Burgers = 0;
                    if(element1 !=null){
                        name = element1.getName();
                        Burgers = element1.getRequiredBurgers();
                    }
                    myWriter.write(name+":"+Burgers+",");
                }
            }
            myWriter.write("\n");
            String BURGERS = Integer.toString(BurgerStock);
            myWriter.write(BURGERS);
            //closing the file writer
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.\n");
        }
        System.out.println();
    }

    //method to load data from a text file
    public static void loadqueuedata(FoodQueue[] Queue, FoodQueue WaitingLIST) {
        //prompt to check if user wants to store the data
        System.out.println("Loading will delete existing data. Are you sure? (Y): ");
        String valid = scanner.nextLine();
        if(!valid.equalsIgnoreCase("Y")){
            System.out.println("Exiting load process\n");
            return;
        }

        Customer[] CashierONE = Queue[0].getQueue();
        Customer[] CashierTWO = Queue[1].getQueue();
        Customer[] CashierTHREE = Queue[2].getQueue();

        for (int i = 0; i < CashierONE.length; i++) {
            CashierONE[i] = null;
        }
        for (int i = 0; i < CashierTWO.length; i++) {
            CashierTWO[i] = null;
        }
        for (int i = 0; i < CashierTHREE.length; i++) {
            CashierTHREE[i] = null;
        }

        //defining reading object and reading the values in the textfile line by line
        try {
            File file = new File("cashierdata.txt");
            Scanner file_reader = new Scanner(file);
            String line1 = file_reader.nextLine();
            int line2 = file_reader.nextInt();
            BurgerStock = line2;
            String[] DataArray = line1.split(",",0);
            int i=0;
            for(String element:DataArray){
                String[] NameANDBurgers = element.split(":",0);
                String[] Name = NameANDBurgers[0].split(" ", 0);
                if(Name[0].equals("null")){
                    continue;
                }
                Customer LoadCustomer = new Customer(Name[0],Name[1],Integer.parseInt(NameANDBurgers[1]));
                addcustomer(Queue, WaitingLIST, 0, LoadCustomer);
            }

            //closing the file reader
            file_reader.close();
            System.out.println("Successfully loaded from file!\n");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while reading a file.\n");
        }
    }
}