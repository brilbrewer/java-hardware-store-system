package edu.iu.c212.programs;

import edu.iu.c212.models.Staff;
import edu.iu.c212.utils.FileUtils;

import java.io.*;
import java.util.*;


public class StaffScheduler {

    /**
     * this is the main action in the class that uses the private method below to generate a schedule
     */
    public void scheduleStaff() {
        try {
            List<Staff> staffAvailability = FileUtils.readStaffFromFile();
            List<String[]> shiftTimes = FileUtils.readShiftSchedulesFromFile();
            Map<String, List<String>> storeSchedule = generateStoreSchedule(staffAvailability, shiftTimes);
            FileUtils.writeStoreScheduleToFile(storeSchedule);
        } catch (Exception e) {
            System.err.println("An error occurred while trying to access the files: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * private method for generating a schedule based on the stores hours and employees availability
     * @param staffAvailability list of staff members
     * @param shiftTimes list of string arrays
     * @return Map, key = string, value = list of strings
     */
    private Map<String, List<String>> generateStoreSchedule(List<Staff> staffAvailability, List<String[]> shiftTimes) {
        List<String> daysOfWeek = List.of("M", "T", "W", "TR", "F", "SAT", "SUN");
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (String str : daysOfWeek) {
            result.put(str, new ArrayList<>());
        }
        double totalHours = 0;

        Map<String, Integer> totalHoursMap = new HashMap<>();
        for (String[] shift : shiftTimes) {
            String dayOfWeek = shift[0];
            double openTime = Double.parseDouble(shift[1]);
            double closeTime = Double.parseDouble(shift[2]);
            totalHours = Math.abs(closeTime - openTime) / 100;
            totalHoursMap.put(dayOfWeek, totalHoursMap.getOrDefault(dayOfWeek, 0) + (int) totalHours);
        }
        double combinedDayHours = 0;
        for (int hours : totalHoursMap.values()) {
            combinedDayHours += hours;
        }

        double averageHours = combinedDayHours / staffAvailability.size();
        double trackHours = 0;

        for (Staff staff : staffAvailability) {
            String[] availableDays = staff.getAvailability().split("\\.");
            Random random = new Random();
            double randomHours = random.nextDouble(averageHours - 20, averageHours + 20);
            for (String day : availableDays) {
                int hours = totalHoursMap.getOrDefault(day, 0);
                    if (hours > 0) {
                        result.get(day).add(staff.getFullName());
                        totalHoursMap.put(day, hours - 1);
                    }

            }
        }
        return result;

    }
}

