package com.musicallyanna.mosfet.command;

import com.musicallyanna.mosfet.Bot;
import com.musicallyanna.mosfet.time.MakerspaceEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Custom modal handler to process the user input attached to it.
 * @author Anna Bontempo
 */
public class ModalHandler extends ListenerAdapter {

    /**
     * Handles modals by processing user input and passing the data back to the original command to begin scheduling.
     * @param event the {@code net.dv8tion.jda.api.events.interaction.command.ModalInteractionEvent} tied to the user's
     *              interaction with the modal menu.
     */
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {

        // check for different modal ids that have been defined elsewhere in the project, handle them
        if (event.getModalId().equals("eventscheduler")) {

            // refresh calendar event data
            final String eventDataFilePath = Bot.config.get("EVENT_DATA_PATH");
            Bot.makerspaceCalendar.loadEventData(eventDataFilePath);

            // process data for the event
            final String organizer = event.getUser().getName();
            final String group = Objects.requireNonNull(event.getValue("group")).getAsString();
            final String eventName = Objects.requireNonNull(event.getValue("eventname")).getAsString();
            final String date = Objects.requireNonNull(event.getValue("date")).getAsString();
            final String startTime = Objects.requireNonNull(event.getValue("starttime")).getAsString();
            final String endTime = Objects.requireNonNull(event.getValue("endtime")).getAsString();

            // create the event
            final MakerspaceEvent eventToSchedule = new MakerspaceEvent(organizer, group, eventName, date, startTime, endTime);

            // attempt to schedule event
            Bot.makerspaceCalendar.scheduleEvent(eventToSchedule);

            // save event data
            Bot.makerspaceCalendar.storeEventData(eventDataFilePath);

            // reply to event
            event.reply("Success?").queue();
        }
    }

}