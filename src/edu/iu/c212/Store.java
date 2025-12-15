package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.programs.SawPrimePlanks;
import edu.iu.c212.programs.StaffScheduler;
import edu.iu.c212.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Store implements IStore {

    /**
     * initialized instance variables
     */
    private List<Item> items;
    private List<Staff> staffList;
    private List<String> commands;
    private StaffScheduler staffScheduler;

    /**
     * this is the constructor for the Store class where the instance variables are assigned to the read file outputs
     * @throws IOException file exception
     */
    public Store() throws IOException {
        this.items = FileUtils.readInventoryFromFile();
        this.staffList = FileUtils.readStaffFromFile();
        this.commands = FileUtils.readCommandsFromFile();
        this.staffScheduler = new StaffScheduler();
    }

    /**
     * this returns the items instance variable
     * @return List of Items
     * @throws IOException file exception
     */
    @Override
    public List<Item> getItemsFromFile() throws IOException {
        try {
            return this.items;
        }
        catch (Exception e) {
            System.err.println("An error occurred while reading inventory file: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    /**
     * this returns the stafflist instance variable
     * @return List of Staff members
     * @throws IOException file exception
     */
    @Override
    public List<Staff> getStaffFromFile() throws IOException {
        try {
            return this.staffList;
        }
        catch (Exception e) {
            System.err.println("An error occurred while reading the staff file: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    /**
     * this writes the items to the inventory.txt
     */
    @Override
    public void saveItemsFromFile() {
        try {
            FileUtils.writeInventoryToFile(items);
        }
        catch (Exception e) {
            System.err.println("An error occurred while saving items to inventory file: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * this writes the staffList to the staff availability file
     */
    @Override
    public void saveStaffFromFile() {
        try {
            FileUtils.writeStaffToFile(staffList);
        }
        catch (Exception e) {
            System.err.println("An error occurred while saving items to staff file: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * this is classes signal to initiate the commands in the input file
     */
    @Override
    public void takeAction() {
        try {

            for (String command : commands) {
                executeCommands(command, this.items, this.staffList, this.staffScheduler);
                saveStaffFromFile();
                saveItemsFromFile();
            }
        }
        catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * private method handling all of the command cases
     * @param commandString String
     * @param items List of items
     * @param staff list of staff members
     * @param staffScheduler staffScheduler class member
     * @throws IOException file exception
     */
    private void executeCommands(String commandString, List<Item> items, List<Staff> staff, StaffScheduler staffScheduler) throws IOException {

        List<String> commandList = new ArrayList<>();
        List<Integer> aposIndexes = new ArrayList<>();
        for (int p = 0; p < commandString.length(); p++) {
            if (Character.isLetter(commandString.charAt(p)) || Character.isSpaceChar(commandString.charAt(p)) || Character.isDigit(commandString.charAt(p)) || commandString.charAt(p) == '.') {
                continue;
            }
            else {
                aposIndexes.add(p);
            }
        }
        StringBuilder temp = new StringBuilder();
        if (aposIndexes.size() == 2) {
            commandList.add(commandString.substring(0, aposIndexes.get(0) - 1));
            commandList.add(commandString.substring(aposIndexes.get(0) + 1, aposIndexes.get(1)));
            int index = aposIndexes.get(1) + 1;

            while (index < commandString.length()) {
                if (Character.isSpaceChar(commandString.charAt(index))) {
                    if (!temp.isEmpty()) {
                        commandList.add(temp.toString());
                    }
                    temp = new StringBuilder();
                    index++;
                }
                else {
                    temp.append(commandString.charAt(index));
                    index++;
                }
            }
            if (!temp.isEmpty()) {
                commandList.add(temp.toString());
            }
        }
        else {
            commandList.add(commandString);
        }

        String actualCommand = commandList.get(0);

        switch (actualCommand) {

            case "ADD":
                String itemName1 = commandList.get(1);
                double itemCost = Double.parseDouble(commandList.get(2));
                int itemQuantity = Integer.parseInt(commandList.get(3));
                int itemAisle = Integer.parseInt(commandList.get(4));

                items.add(new Item("'" + itemName1 + "'", itemCost, itemQuantity, itemAisle));
                this.items = items;
                String outputText1 = itemName1 + " was added to inventory";
                FileUtils.writeLineToOutputFile(outputText1);
                break;

            case "COST":
                String itemName2 = commandList.get(1);
                double cost = 0;

                for (Item item : items) {
                    if (item.getName() == "'" + itemName2 + "'") {
                        cost = item.getPrice();
                    }
                }
                this.items = items;
                String outputText2 = itemName2 + ": $" + cost;
                FileUtils.writeLineToOutputFile(outputText2);
                break;

            case "EXIT":
                FileUtils.writeLineToOutputFile("Thank you for visiting High's Hardware and Gardening!");
                System.out.println("Press enter to continue...");
                System.console().readLine();
                System.exit(0);
                break;

            case "FIND":
                String itemName3 = commandList.get(1);
                Boolean checker = false;

                for (Item item : items) {
                    if (item.getName() == "'" + itemName3 + "'") {
                        FileUtils.writeLineToOutputFile(item.getQuantity() + " " + itemName3 + " are available in " + item.getAisleNum());
                        checker = true;
                    }
                }
                if (!checker) {
                    FileUtils.writeLineToOutputFile("ERROR: " + itemName3 + " cannot be found");
                }
                break;

            case "FIRE":
                String staffName1 = commandList.get(1);
                boolean check = false;

                for (Staff staffMembers1 : staff) {
                    if (staffMembers1.getFullName() == staffName1) {
                        staff.remove(staffMembers1);
                        check = true;
                    }
                }

                if (check) {
                    this.staffList = staff;
                    saveStaffFromFile();
                    FileUtils.writeLineToOutputFile(staffName1 + " was fired");
                }
                else {
                    FileUtils.writeLineToOutputFile("ERROR: " + staffName1 + " cannot be found");
                }
                break;

            case "HIRE":
                String staffName2 = commandList.get(1);
                int staffAge = Integer.parseInt(commandList.get(2));
                String staffRole = commandList.get(3);
                String staffAvailability = commandList.get(4);
                Staff newStaff = new Staff(staffName2, staffAge, staffRole, staffAvailability);

                if (staffRole == "M") {
                    staffRole = "Manager";
                }
                else if (staffRole == "C") {
                    staffRole = "Cashier";
                }
                else {
                    staffRole = "Gardening Expert";
                }

                staff.add(newStaff);
                this.staffList = staff;
                saveStaffFromFile();
                FileUtils.writeLineToOutputFile(staffName2 + " has been hired as a " + staffRole);
                break;

            case "PROMOTE":
                String staffName3 = commandList.get(1);
                String staffNewRole = commandList.get(2);

                for (Staff staffMember2 : staff) {
                    if (staffMember2.getFullName() == staffName3) {
                        staffMember2.setRole(staffNewRole);
                    }
                }
                this.staffList = staff;
                saveStaffFromFile();

                if (staffNewRole == "M") {
                    staffNewRole = "Manager";
                }
                else if (staffNewRole == "C") {
                    staffNewRole = "Cashier";
                }
                else {
                    staffNewRole = "Gardening Expert";
                }

                FileUtils.writeLineToOutputFile(staffName3 + " was promoted to " + staffNewRole);
                break;

            case "SAW":
                int plankLength = 0;
                for (Item item : items) {
                    if (item.getName().substring(0, 7) == "'Plank-") {
                        plankLength = Integer.parseInt(item.getName().substring(7));
                        int sawedPlank = SawPrimePlanks.sawPlank(plankLength);
                        int sawedPlankQuantity = SawPrimePlanks.getPlankLengths(plankLength).size();
                        double price = sawedPlank * sawedPlank;
                        item.setName("'Plank-" + sawedPlank + "'");
                        item.setPrice(price);
                        item.setQuantity(sawedPlankQuantity);
                    }
                }
                this.items = items;
                saveItemsFromFile();
                FileUtils.writeLineToOutputFile("Planks sawed");
                break;

            case "SCHEDULE":
                staffScheduler.scheduleStaff();
                FileUtils.writeLineToOutputFile("Schedule created");

            case "SELL":
                String itemName4 = commandList.get(1);
                int quantity = Integer.parseInt(commandList.get(2));
                boolean check2 = false;

                for (Item items1 : items) {
                    if (items1.getName() == "'" + itemName4 + "'") {
                        if (items1.getQuantity() >= quantity) {
                            check2 = true;
                            items1.setQuantity(items1.getQuantity() - quantity);
                        }
                    }
                }
                if (check2) {
                    this.items = items;
                    saveItemsFromFile();
                    FileUtils.writeLineToOutputFile(quantity + " " + itemName4 + " was sold");
                }
                else {
                    FileUtils.writeLineToOutputFile("ERROR: " + itemName4 + " could not be sold");
                }
                break;

            case "QUANTITY":
                String itemName5 = commandList.get(1);
                int quantity2 = 0;

                for (Item items2 : items) {
                   if (items2.getName() == "'" + itemName5 + "'") {
                       quantity2 = items2.getQuantity();
                   }
                }

                FileUtils.writeLineToOutputFile(String.valueOf(quantity2));
                break;

            default:
                FileUtils.writeLineToOutputFile("Command Unknown");
        }

    }
}
