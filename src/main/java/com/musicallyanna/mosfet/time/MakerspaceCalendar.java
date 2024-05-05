package com.musicallyanna.mosfet.time;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Calendar for the Makerspace that holds all scheduled events.
 * @author Anna Bontempo
 */
public class MakerspaceCalendar {

    /**
     * Collection of scheduled events, ordered earliest to latest chronologically.
     */
    private PriorityQueue<MakerspaceEvent> eventQueue;

    /**
     * No argument constructor.
     */
    public MakerspaceCalendar() {
        eventQueue = new PriorityQueue<MakerspaceEvent>(1);
    }

    /**
     * Creates a calendar given a valid file path.
     * @param filePath the path to the file containing the event data
     */
    public MakerspaceCalendar(String filePath) {
        loadEventData(filePath);
    }

    /**
     * Saves each scheduled event in {@code this.eventQueue}.
     * @param filePath the path to the file where the event queue will be stored in
     */
    public void storeEventData(String filePath) {

        // serialize the event queue to the file at the specified file path
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(this.eventQueue);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads scheduled events from a file into {@code this.eventQueue}.
     * @param filePath the path to the file where the event queue will be loaded from
     */
    @SuppressWarnings("unchecked")
    public void loadEventData(String filePath) {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            this.eventQueue = new PriorityQueue<>(new EventOrdering()); // initialize this.eventQueue
            this.eventQueue.addAll((PriorityQueue<MakerspaceEvent>) in.readObject()); // attempt to load from file

        } catch (IOException | ClassNotFoundException e) {
            this.eventQueue = new PriorityQueue<MakerspaceEvent>(new EventOrdering()); // make empty queue on fail
        }
    }

    /**
     * Schedules an event into the event queue.
     * @param e the {@code MakerspaceEvent} to add into {@code this.eventQueue}
     */
    public void scheduleEvent(MakerspaceEvent e) {

        // shows whether the events overlap or not. initially false until proven true
        boolean overlap = false;

        // only iterate over the queue if it has events in it
        if (!this.eventQueue.isEmpty()) {
            for (MakerspaceEvent scheduledEvent : this.eventQueue) {

                // check if the current event in the queue overlaps with the parameter event
                if (scheduledEvent.hasOverlap(e)) {
                    overlap = true;
                }
            }
        }

        // "schedule" event (add it to the queue) if there is no overlap
        if (!overlap) {
            this.eventQueue.add(e);
        }
    }

    /**
     * Reports all the events scheduled for the Makerspace.
     * @return a {@code java.util.ArrayList<MakerspaceEvent>} containing all of the scheduled events in {@code this.eventQueue}
     */
    public ArrayList<MakerspaceEvent> getEvents() {

        // return the entries in the event queue in ArrayList form
        return new ArrayList<MakerspaceEvent>(this.eventQueue);
    }

    /**
     * Serializable comparator for {@code MakerspaceEvent}.
     * @author Anna Bontempo
     */
    private static class EventOrdering implements Comparator<MakerspaceEvent>, Serializable {

        /**
         * Compares the start times of each {@code MakerspaceEvent}.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return == 0 if start times equal, < 0 if {@code o1.startTime} is first, > 0 if is last
         */
        @Override
        public int compare(MakerspaceEvent o1, MakerspaceEvent o2) {
            return o1.getStart().compareTo(o2.getStart());
        }
    }
}
