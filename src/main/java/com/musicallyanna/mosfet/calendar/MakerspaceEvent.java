package com.musicallyanna.mosfet.calendar;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Class representing an event to take place in the makerspace.
 * @author Anna Bontempo
 */
public class MakerspaceEvent {

    /**
     * The {@code MakerspaceRoom} the the event is being held in.
     */
    private MakerspaceRoom room;

    /**
     * The organizer for the event.
     */
    private String organizer;

    /**
     * The group who the event is for.
     */
    private String group;

    /**
     * The name of the event.
     */
    private String name;

    /**
     * The start time of the event.
     */
    private LocalDateTime startTime;

    /**
     * The start time of the event.
     */
    private LocalDateTime endTime;

    /**
     * Creates an event with input provided through the {@code ScheduleEventCommand}'s modal menu.
     * @param roomIn the {@code MakerspaceRoom} that the event will be held in.
     * @param organizerIn the name of the event organizer.
     * @param groupIn the name of the group/organization that is holding the event.
     * @param nameIn the name of the event.
     * @param dateIn the date that the event will take place.
     * @param startTimeIn the time that the event will start.
     * @param endTimeIn the time that the event will end.
     */
    public MakerspaceEvent(MakerspaceRoom roomIn, String organizerIn, String groupIn, String nameIn, String dateIn, String startTimeIn, String endTimeIn) {

        // initialize instance variables
        this.room = roomIn;
        this.organizer = organizerIn;
        this.group = groupIn;
        this.name = nameIn;

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
            // PLEASE CHANGE THIS FOR THE LOVE OF GOD!!!!!!!!!!!!!!!!
            throw new RuntimeException(nfe);
        }

        // store the start and end times for the event
        this.startTime = eventStart;
        this.endTime = eventEnd;
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
     * Returns the current room for the event.
     * @return the {@code MakerspaceRoom} that the event is located in.
     */
    public MakerspaceRoom getRoom() {
        return this.room;
    }

    /**
     * Formats {@code this.room} into a printable {@code String} format.
     * @return a {@code String} in capitalized, spaced out format representing the value of {@code this.room}
     */
    public String getRoomString() {
        return MakerspaceRoom.toFormattedString(this.room);
    }

    /**
     * Reports whether another event's scheduling will cause conflicts in the calendar.
     * @param eventIn the {@code MakerspaceEvent} to compare against this one.
     * @return {@code boolean} indicating whether {@code this} and {@code eventIn} will overlap in the calendar.
     */
    public boolean hasOverlap(MakerspaceEvent eventIn) {

        // get start and end time of other event
        LocalDateTime startTimeIn = eventIn.getStartTime();
        LocalDateTime endTimeIn = eventIn.getEndTime();

        // check to see if the provided event would cause scheduling conflicts with this one
        return (this.startTime.isBefore(endTimeIn) || this.endTime.isAfter((startTimeIn)));
    }

    /**
     * Reports the event's organizer.
     * @return {@code String} containing the name of the organizer of {@code this}.
     */
    public String getOrganizer() {
        return this.organizer;
    }

    /**
     * Reports the event's name.
     * @return {@code String} containing the name of {@code this}.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Reports the group hosting the event.
     * @return {@code String} containing the group that is hosting {@code this}.
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * Reports the event's starting time.
     * @return {@code LocalDateTime} representing the start time of the event corresponding to {@code this}.
     */
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Reports the event's ending time.
     * @return {@code LocalDateTime} representing the end time of the event corresponding to {@code this}.
     */
    public LocalDateTime getEndTime() {
        return this.endTime;
    }
}
