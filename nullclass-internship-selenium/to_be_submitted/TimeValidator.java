package com.nullclass.internship.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to validate time-based execution constraints for internship tasks
 * 
 * Task 2: Should run only between 6 PM to 7 PM
 * Task 3: Should run only between 3 PM to 6 PM
 * 
 * @author Vinaykumar Nakka
 */
public class TimeValidator {
    
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /**
     * Validates if current time is within Task 2 execution window (6 PM - 7 PM)
     * 
     * @return true if current time is between 18:00:00 and 19:00:00, false otherwise
     */
    public static boolean isTask2ExecutionTime() {
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(18, 0, 0); // 6 PM
        LocalTime endTime = LocalTime.of(19, 0, 0);   // 7 PM
        
        boolean isValidTime = !currentTime.isBefore(startTime) && currentTime.isBefore(endTime);
        
        System.out.println("=== TASK 2 TIME VALIDATION ===");
        System.out.println("Current Time: " + currentTime.format(TIME_FORMATTER));
        System.out.println("Required Time Window: 18:00:00 - 19:00:00 (6 PM - 7 PM)");
        System.out.println("Is Valid Time: " + isValidTime);
        System.out.println("===============================");
        
        return isValidTime;
    }
    
    /**
     * Validates if current time is within Task 3 execution window (3 PM - 6 PM)
     * 
     * @return true if current time is between 15:00:00 and 18:00:00, false otherwise
     */
    public static boolean isTask3ExecutionTime() {
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(15, 0, 0); // 3 PM
        LocalTime endTime = LocalTime.of(18, 0, 0);   // 6 PM
        
        boolean isValidTime = !currentTime.isBefore(startTime) && currentTime.isBefore(endTime);
        
        System.out.println("=== TASK 3 TIME VALIDATION ===");
        System.out.println("Current Time: " + currentTime.format(TIME_FORMATTER));
        System.out.println("Required Time Window: 15:00:00 - 18:00:00 (3 PM - 6 PM)");
        System.out.println("Is Valid Time: " + isValidTime);
        System.out.println("===============================");
        
        return isValidTime;
    }
    
    /**
     * Gets the current time in HH:mm:ss format
     * 
     * @return formatted current time string
     */
    public static String getCurrentTimeFormatted() {
        return LocalTime.now().format(TIME_FORMATTER);
    }
    
    /**
     * Calculates time remaining until Task 2 execution window starts
     * 
     * @return time remaining message or empty string if within window
     */
    public static String getTask2TimeRemaining() {
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(18, 0, 0);
        
        if (currentTime.isBefore(startTime)) {
            long minutesRemaining = java.time.Duration.between(currentTime, startTime).toMinutes();
            return "Task 2 will be available in " + minutesRemaining + " minutes (at 6 PM)";
        } else if (currentTime.isAfter(LocalTime.of(19, 0, 0))) {
            return "Task 2 execution window has passed. Next available tomorrow at 6 PM";
        }
        
        return "";
    }
    
    /**
     * Calculates time remaining until Task 3 execution window starts
     * 
     * @return time remaining message or empty string if within window
     */
    public static String getTask3TimeRemaining() {
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(15, 0, 0);
        
        if (currentTime.isBefore(startTime)) {
            long minutesRemaining = java.time.Duration.between(currentTime, startTime).toMinutes();
            return "Task 3 will be available in " + minutesRemaining + " minutes (at 3 PM)";
        } else if (currentTime.isAfter(LocalTime.of(18, 0, 0))) {
            return "Task 3 execution window has passed. Next available tomorrow at 3 PM";
        }
        
        return "";
    }
}
