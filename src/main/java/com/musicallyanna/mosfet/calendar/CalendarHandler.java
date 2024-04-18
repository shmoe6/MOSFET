package com.musicallyanna.mosfet.calendar;

import java.util.ArrayList;
import java.util.Date;

/**
 * Handles the calendars for all four rooms in the makerspace.
 * @author Anna Bontempo
 */
public class CalendarHandler {

    /**
     * The current time, down to millisecond precision.
     */
    private Date currentTime;

    /**
     * The calendar for the Ideation Space.
     */
    private CalendarBase ideationCalendar;

    /**
     * The calendar for the Electronics Lab.
     */
    private CalendarBase electronicsCalendar;

    /**
     * The calendar for the woodshop.
     */
    private CalendarBase woodshopCalendar;

    /**
     * The calendar for the metalshop.
     */
    private CalendarBase metalshopCalendar;

    /**
     * Control point for whether scheduling is allowed or not.
     */
    boolean schedulingEnabled;

    /**
     * No argument constructor.
     */
    public CalendarHandler() {
        // set up calendar for each of the four rooms
        ideationCalendar = new CalendarBase(MakerspaceRoom.IDEATION_SPACE);
        electronicsCalendar = new CalendarBase(MakerspaceRoom.ELECTRONICS_LAB);
        woodshopCalendar = new CalendarBase(MakerspaceRoom.WOOD_SHOP);
        metalshopCalendar = new CalendarBase(MakerspaceRoom.METAL_SHOP);

        // initialize control variable
        schedulingEnabled = true;
    }

    /**
     * Facilitates the scheduling of a {@code MakerspaceEvent} and reports its success.
     * @param event the event to schedule.
     * @return a {@code boolean} representing if the event was successfully scheduled or not;
     */
    public boolean schedule(MakerspaceEvent event) {

        // forward event to the correct calendar for scheduling and attempt to schedule it
        final boolean success = switch (event.getRoom()) {
            case IDEATION_SPACE -> ideationCalendar.schedule(event);
            case ELECTRONICS_LAB -> electronicsCalendar.schedule(event);
            case WOOD_SHOP -> woodshopCalendar.schedule(event);
            case METAL_SHOP -> metalshopCalendar.schedule(event);
        };

        // log results in console
        if (success) {
            System.out.println("Scheduling Successful!");

        } else {
            System.out.println("Scheduling Unsuccessful.");
        }

        // return results to user
        return success;
    }

    /**
     * Gathers all {@code MakerspaceEvent}s from a {@code MakerspaceRoom}'s {@code CalendarBase}.
     * @param room the room to query events about.
     * @return an {@code ArrayList<MakerspaceEvent>} that contains all scheduled events for the specified room.
     */
    public ArrayList<MakerspaceEvent> getEvents(MakerspaceRoom room) {

        // forward request to the proper calendar
        final CalendarBase curWorkingCalendar = switch (room) {
            case IDEATION_SPACE -> this.ideationCalendar;
            case ELECTRONICS_LAB -> this.electronicsCalendar;
            case WOOD_SHOP -> this.woodshopCalendar;
            case METAL_SHOP -> this.metalshopCalendar;
        };

        // return all the events in an {@code Arraylist}
        return new ArrayList<MakerspaceEvent>(curWorkingCalendar.events);
    }
}
