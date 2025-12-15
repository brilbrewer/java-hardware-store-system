package edu.iu.c212.utils;

import edu.iu.c212.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {

    /**
     * file paths
     */
    private static File inputFile = new File("resources/input.txt");
    private static File outputFile = new File("resources/output.txt");
    private static File inventoryFile = new File("resources/inventory.txt");
    private static File staffAvailabilityFile = new File("resources/staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File("resources/shift_schedules_IN.txt");
    private static File storeScheduleFile = new File("resources/store_schedule_OUT.txt");

    /**
     * reads the inventory file and builds and item based off of the info in every line
     * @return List of items
     * @throws IOException file exception
     */
    public static List<Item> readInventoryFromFile() throws IOException {
        List<Item> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inventoryFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                int aisleNum = Integer.parseInt(parts[3]);
                Item item = new Item(name, price, quantity, aisleNum);
                items.add(item);
            }
            reader.close();
        }
        return items;
    }

    /**
     * this writes a list of items to the inventory file
     * @param items List of items
     * @throws IOException file exception
     */
    public static void writeInventoryToFile(List<Item> items) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFile))) {
            for (Item item: items) {
                String line = item.getName() + "," + item.getPrice() + "," + item.getQuantity() + "," + item.getAisleNum();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
    }

    /**
     * this writes a list of staff members to the staff availability file
     * @param employees list of staff members
     * @throws IOException file exception
     */
    public static void writeStaffToFile(List<Staff> employees) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(staffAvailabilityFile))) {
            for (Staff staff : employees) {
                String line = staff.getFullName() + " " + staff.getAge() + " " + staff.getRole() + " " + staff.getAvailability();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
    }

    /**
     * this reads the input file to get a list of all the commands
     * @return list of strings of commands
     * @throws IOException file exception
     */
    public static List<String> readCommandsFromFile() throws IOException {
        List<String> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (!(line.isBlank())) {
                    commands.add(line);
                }
            }
            reader.close();
        }
        return commands;
    }

    /**
     * this writes a string to the output file
     * @param line String
     * @throws IOException file exception
     */
    public static void writeLineToOutputFile(String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write(line);
            writer.newLine();
            writer.close();
        }
    }

    /**
     * this reads the staff availability file and builds staff members while putting them into a list of staff members
     * @return list of staff members
     * @throws IOException file exception
     */
    public static List<Staff> readStaffFromFile() throws IOException {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(staffAvailabilityFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String fullName = parts[0] + " " + parts[1];
                int age = Integer.parseInt(parts[2]);
                String role = parts[3];
                String availability = parts[4];
                Staff staff = new Staff(fullName, age, role, availability);
                staffList.add(staff);
            }
            reader.close();
        }
        return staffList;
    }

    /**
     * this reads the shift schedule file and puts the days, opening time and closing time into their own array
     * @return list of string arrays
     * @throws IOException file exception
     */
    public static List<String[]> readShiftSchedulesFromFile() throws IOException {
        List<String[]> shiftSchedules = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(shiftSchedulesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                shiftSchedules.add(parts);
            }
            reader.close();
        }
        return shiftSchedules;
    }

    /**
     * this writes to schedule out file with our completed schedule using the StaffScheduler class
     * @param storeSchedule Map, key = String, value = list of strings
     * @throws IOException file exception
     */
    public static void writeStoreScheduleToFile(Map<String, List<String>> storeSchedule) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storeScheduleFile))) {
            writer.write("Created on " + java.time.LocalDate.now() + " at " + java.time.LocalTime.now());
            writer.newLine();
            for (Map.Entry<String, List<String>> entry : storeSchedule.entrySet()) {
                writer.newLine();
                writer.write(entry.getKey() + " ");
                for (String staff : entry.getValue()) {
                    writer.write("(" + staff + ") ");
                }
            }
            writer.close();
        }
    }
}
