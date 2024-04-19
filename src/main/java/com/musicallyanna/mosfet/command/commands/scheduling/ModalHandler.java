package com.musicallyanna.mosfet.command.commands.scheduling;

import com.musicallyanna.mosfet.calendar.MakerspaceEvent;
import com.musicallyanna.mosfet.calendar.MakerspaceRoom;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
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

        // check for different modal types that have been defined elsewhere in the project, handle them
        if (event.getModalId().equals("eventscheduler")) {

            // process data for the event
            final String organizer = event.getUser().getName();
            final String group = Objects.requireNonNull(event.getValue("group")).getAsString();
            final String eventName = Objects.requireNonNull(event.getValue("eventname")).getAsString();
            final String date = Objects.requireNonNull(event.getValue("date")).getAsString();
            final String startTime = Objects.requireNonNull(event.getValue("starttime")).getAsString();
            final String endTime = Objects.requireNonNull(event.getValue("endtime")).getAsString();

            // get room cache from {@code ScheduleEventCommand} to see what room the user is scheduling for
            final HashMap<Long, String> roomCache = ScheduleEventCommand.roomCache;

            // check if user id is in the cache
            if (roomCache.containsKey(event.getUser().getIdLong())) {

                // if user id is in cache, extract their room preference
                final String roomString = roomCache.get(event.getUser().getIdLong());

                // convert roomString to enum value
                final MakerspaceRoom room = MakerspaceRoom.stringToEnum(roomString);

                // create the event to be sent back to the command's class
                final MakerspaceEvent scheduledEvent = new MakerspaceEvent(room, organizer, group, eventName, date, startTime, endTime);

                // begin scheduling request through {@code ScheduleEventCommand}
                ScheduleEventCommand.requestSchedule(scheduledEvent);

                // notify user of success
                event.reply("Event Scheduled!").queue();
            } else {
                // notify user of failure
                event.reply("An error has occurred. Please check the information entered and try again.").queue();
            }

            // clear cache upon success
            roomCache.remove(event.getUser().getIdLong());

            // block use of event to prevent further collection of data
            event = null;
        }
    }
}
