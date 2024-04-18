package com.musicallyanna.mosfet.calendar;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Base class to serve as a framework for each calendar.
 * @author Anna Bontempo
 */
public class CalendarBase {
    /**
     * The {@code MakerspaceRoom} that the calendar is keeping track of events for.
     */
    private MakerspaceRoom room;

    /**
     * A {@code java.util.Queue} to hold the makerspace events in the order that they will happen.
     */
    Queue<MakerspaceEvent> events;

    /**
     * Constructor to create a calendar for a {@code MakerspaceRoom}.
     * @param room the {@code MakerspaceRoom} to initialize {@code this.room} to.
     */
    public CalendarBase(MakerspaceRoom room) {

        // set the room
        this.room = room;

        // create the {@code java.util.PriorityQueue} to store the events in chronological order
        this.events = new PriorityQueue<MakerspaceEvent>(new Comparator<MakerspaceEvent>() {

            /**
             * Compares the start times of each {@code MakerspaceEvent}.
             * @param o1 the first object to be compared.
             * @param o2 the second object to be compared.
             * @return == 0 if start times equal, < 0 if {@code o1.startTime} is first, > 0 if is last
             */
            @Override
            public int compare(MakerspaceEvent o1, MakerspaceEvent o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
    }

    /**
     * Attempts to schedule a {@code MakerspaceEvent} in {@code this}.
     * @param event the {@code MakerspaceEvent} to be scheduled.
     * @return a {@code boolean} that represents whether the event could be scheduled or not.
     */
    public boolean schedule(MakerspaceEvent event) {

        // variable that holds the return result
        boolean success = false;

        // if there are no scheduled events to pose a conflict, schedule this one
        if (this.events.isEmpty()) {
            events.add(event);
            success = true;

        } else {
            // control point for adding the proposed event to the schedule
            boolean allClearToSchedule = true;

            // temporary queue to store each event as it is removed from the events queue
            Queue<MakerspaceEvent> temp = new PriorityQueue<MakerspaceEvent>(this.events.size() + 1 );

            // loop through the events queue
            for (int i = 0; i < this.events.size(); i++) {

                // remove the first event, add it to the temporary queue
                MakerspaceEvent front = this.events.remove();
                temp.add(front);

                // if there is overlap between the two events, deny scheduling
                if (front.hasOverlap(event)) {
                    allClearToSchedule = false;
                }
            }

            // if no overlaps, add the event to the temp queue so it can be scheduled
            if (allClearToSchedule) {
                temp.add(event);
                success = true;
            }

            // add all events from the temp queue back into the event queue, ordering them in the process
            this.events.addAll(temp);
        }

        // report success of scheduling
        return success;
    }
}
