package com.musicallyanna.mosfet.command.commands.scheduling;

import com.musicallyanna.mosfet.Bot;
import com.musicallyanna.mosfet.command.commands.CommandBase;
import com.musicallyanna.mosfet.time.MakerspaceEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;

/**
 * Command to view current schedule of a {@code MakerspaceRoom}.
 * @author Anna Bontempo
 */
public class ViewScheduleCommand extends CommandBase {

    /**
     * Displays current {@code MakerspaceEvent}s scheduled for the provided room in embed form.
     * @param event the event tied to the command.
     */
    @Override
    public void execute(SlashCommandInteractionEvent event) {

        // refresh events in the calendar
        final String eventDataFilePath = Bot.config.get("EVENT_DATA_PATH");
        Bot.makerspaceCalendar.loadEventData(eventDataFilePath);

        // get list of events from correct calendar
        ArrayList<MakerspaceEvent> events = Bot.makerspaceCalendar.getEvents();

        // setup embed
        EmbedBuilder eventDisplay = new EmbedBuilder().setTitle("Current Schedule for " + "Ideation Space");

        // add each {@code MakerspaceEvent} to the embed
        for (MakerspaceEvent e : events) {
            eventDisplay.addField(e.getEventTitle(), "(" + e.getOrganization() + ")", false)
                    .addField("More Information", "Start: " + e.getStart().toString() + "\n" + e.getEnd().toString(), false)
                    .addBlankField(false);
        }

        // build embed and send to user
        event.reply("").setEmbeds(eventDisplay.build()).queue();
    }

    /**
     * Returns the name of the command.
     * @return the {@code String} command name of {@code this}.
     */
    @Override
    public String getName() {
        return "viewschedule";
    }

    /**
     * Returns the description of the command.
     * @return the {@code String} description of the command represented by {@code this}.
     */
    @Override
    public String getDescription() {
        return "displays the schedule for the specified room";
    }
}