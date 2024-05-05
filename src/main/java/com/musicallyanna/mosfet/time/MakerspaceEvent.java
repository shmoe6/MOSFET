package com.musicallyanna.mosfet.time;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Class holding the data for an event in the Makerspace.
 * @author Anna Bontempo
 */
public class MakerspaceEvent implements Serializable {

    private String eventTitle;
    private String organizer;
    private String organization;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates an event with input provided through the {@code ScheduleEventCommand}'s modal menu.
     * @param organizerIn the name of the event organizer.
     * @param groupIn the name of the group/organization that is holding the event.
     * @param nameIn the name of the event.
     * @param dateIn the date that the event will take place.
     * @param startTimeIn the time that the event will start.
     * @param endTimeIn the time that the event will end.
     */
    public MakerspaceEvent(String organizerIn, String groupIn, String nameIn, String dateIn, String startTimeIn, String endTimeIn) {

        // initialize instance variables
        this.organizer = organizerIn;
        this.organization = groupIn;
        this.eventTitle = nameIn;

        // combine all the date strings into one, splitting them into chunks by whitespace, :, or /.
        String[] fullDateStr = (dateIn + " " + startTimeIn + " " + endTimeIn).split("[\\s:/]");

        // information to be stored in the event
        final String month = fullDateStr[0];
        final String day = fullDateStr[1];
        final String year = fullDateStr[2];
        final String startHour = fullDateStr[3];
        final String startMinute = fullDateStr[4];
        final String startTimeOfDay = fullDateStr[5];
        final String endHour = fullDateStr[6];
        final String endMinute = fullDateStr[7];
        final String endTimeOfDay = fullDateStr[8];

        // placeholder date information in case of thrown exception
        LocalDateTime eventStart = LocalDateTime.MAX;
        LocalDateTime eventEnd = LocalDateTime.MAX;

        // convert date "chunks" into formatted {@code LocalDateTime}
        try {
            eventStart = assembleDate(Integer.parseInt(year), month, Integer.parseInt(day), Integer.parseInt(startHour), Integer.parseInt(startMinute), startTimeOfDay);            eventStart = assembleDate(Integer.parseInt(year), month, Integer.parseInt(day), Integer.parseInt(startHour), Integer.parseInt(startMinute), startTimeOfDay);
            eventEnd = assembleDate(Integer.parseInt(year), month, Integer.parseInt(day), Integer.parseInt(endHour), Integer.parseInt(endMinute), endTimeOfDay);
        } catch (NumberFormatException nfe) {
            throw new RuntimeException(nfe);
        }

        // store the start and end times for the event
        this.start = eventStart;
        this.end = eventEnd;
    }

    /**
     * Combines date data into an easier to work with {@code LocalDateTime} object.
     * @param year the year number of the date.
     * @param monthStr the month number of the date.
     * @param day the number of the date.
     * @param hour the hour of the date.
     * @param minute the minute of the date.
     * @param timeOfDay the time of day of the date (AM or PM).
     * @return {@code LocalDateTime} containing all of the date and time related elements provided through the parameters.
     */
    public LocalDateTime assembleDate(int year, String monthStr, int day, int hour, int minute, String timeOfDay) {

        // convert month number to month enum
        Month month = switch(monthStr) {
            case "01" -> Month.JANUARY;
            case "02" -> Month.FEBRUARY;
            case "03" -> Month.MARCH;
            case "04" -> Month.APRIL;
            case "05" -> Month.MAY;
            case "06" -> Month.JUNE;
            case "07" -> Month.JULY;
            case "08" -> Month.AUGUST;
            case "09" -> Month.SEPTEMBER;
            case "10" -> Month.OCTOBER;
            case "11" -> Month.NOVEMBER;
            case "12" -> Month.DECEMBER;
            default -> throw new IllegalStateException("Unexpected value: " + monthStr); // handle fake months
        };

        // convert to 24 hour time system
        if (timeOfDay.equals("PM")) {
            hour += 12;

            // enforce the value of hour being [0,23]. 12 PM will convert to 24:00 otherwise.
            if (hour == 24) {
                hour--;
            }
        }

        // seconds variable to pass into the date creation function
        final int seconds = 0;

        // return object representing the date
        return LocalDateTime.of(year, month, day, hour, minute, seconds);
    }

    /**
     * Constructor to create an event from just a start and end time. This should be used for testing only.
     * @param t1 the {@code LocalDateTime} that the event starts at
     * @param t2 the {@code LocalDateTime} that the event ends at
     */
    public MakerspaceEvent(LocalDateTime t1, LocalDateTime t2) {
        this.start = t1;
        this.end = t2;
    }

    /**
     * Reports the earliest of the two provided times.
     * @param t1 the first {@code LocalDateTime to compare}
     * @param t2 the second {@code LocalDateTime to compare}
     * @return the time that comes first chronologically.
     */
    public LocalDateTime minTime(LocalDateTime t1, LocalDateTime t2) {

        // initially set first time to the first one provided
        LocalDateTime firstTime = t1;

        // set first time to the second parameter only if it comes before the first parameter
        if (t2.isBefore(t1)) {
            firstTime = t2;
        }

        // return earliest time
        return firstTime;
    }

    /**
     * Reports the latest of the two provided times.
     * @param t1 the first {@code LocalDateTime to compare}
     * @param t2 the second {@code LocalDateTime to compare}
     * @return the time that comes last chronologically.
     */
    public LocalDateTime maxTime(LocalDateTime t1, LocalDateTime t2) {

        // initially set last time to the first one provided
        LocalDateTime lastTime = t1;

        // set last time to the second parameter only if it comes before the first parameter
        if (t2.isAfter(t1)) {
            lastTime = t2;
        }

        // return latest time
        return lastTime;
    }

    /**
     * Reports whether two events in the Makerspace overlap.
     * @param e the event to check for overlap with
     * @return whether {@code this} and the provided event's times overlap
     */
    public boolean hasOverlap(MakerspaceEvent e) {

        // subtract the min of the ends of the ranges from the max of the beginning
        // if result <= 0, there is overlap. the following code does this with the LocalDateTime methods
        return !maxTime(this.end, e.end).isAfter(minTime(this.start, e.start));
    }

    /**
     * Reports the start time of the event corresponding to {@code this}.
     * @return {@code LocalDateTime} containing the start time of the event.
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Reports the end time of the event corresponding to {@code this}.
     * @return {@code LocalDateTime} containing the end time of the event.
     */
    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Reports the title of the event corresponding to {@code this}.
     * @return {@code String} containing the name of the event.
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * Reports the title of the organization planning the event corresponding to {@code this}.
     * @return {@code String} containing the organization planning the event.
     */
    public String getOrganization() {
        return organization;
    }
}
