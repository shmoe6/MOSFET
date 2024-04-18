package com.musicallyanna.mosfet.calendar;

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
     * No argument constructor.
     */
    public CalendarHandler() {

    }
}
