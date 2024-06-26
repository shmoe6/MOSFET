package com.musicallyanna.mosfet.command.commands.scheduling;

import com.musicallyanna.mosfet.command.commands.CommandBase;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Command to schedule a {@code MakerspaceEvent} in the calendar.
 * @author Anna Bontempo
 */
public class ScheduleCommand extends CommandBase {

    /**
     * Collection to hold various command {@code OptionData}.
     */
    private final ArrayList<OptionData> options;

    /**
     * Stores the current room that any user is trying to book for.
     */
    public static final HashMap<Long, String> roomCache = new HashMap<Long, String>();

    /**
     * Lower character limit for modal fields.
     */
    final int SMALL_TEXTBOX_MIN_LENGTH = 5;

    /**
     * Upper character limit for modal fields.
     */
    final int SMALL_TEXTBOX_MAX_LENGTH = 40;

    /**
     * No argument contructor.
     */
    public ScheduleCommand() {

        // setup command options
        this.options = new ArrayList<OptionData>();
    }

    /**
     * Creates a modal menu to gather user input about the event to be scheduled. Menu input is forwarded to {@code ModalHandler}.
     * @param event the event tied to the command.
     */
    @Override
    public void execute(SlashCommandInteractionEvent event) {

        // input field for the group hosting the event
        TextInput group = TextInput.create("group", "Group Name", TextInputStyle.SHORT)
                .setPlaceholder("What group/org is this event for?")
                .setRequiredRange(SMALL_TEXTBOX_MIN_LENGTH, SMALL_TEXTBOX_MAX_LENGTH)
                .build();

        // input field for the event's name
        TextInput eventName = TextInput.create("eventname", "Event Name", TextInputStyle.SHORT)
                .setPlaceholder("What is the event called?")
                .setRequiredRange(SMALL_TEXTBOX_MIN_LENGTH, SMALL_TEXTBOX_MAX_LENGTH)
                .build();

        // input field for the event's date
        TextInput date = TextInput.create("date", "Date", TextInputStyle.SHORT)
                .setPlaceholder("MM/DD/YYYY")
                .setRequiredRange(SMALL_TEXTBOX_MIN_LENGTH, SMALL_TEXTBOX_MAX_LENGTH)
                .build();

        // input field for the event's starting time
        TextInput startTime = TextInput.create("starttime", "Starting Time", TextInputStyle.SHORT)
                .setPlaceholder("HH:MM AM")
                .setRequiredRange(SMALL_TEXTBOX_MIN_LENGTH, SMALL_TEXTBOX_MAX_LENGTH)
                .build();

        // input field for the event's ending time
        TextInput endTime = TextInput.create("endtime", "Ending Time", TextInputStyle.SHORT)
                .setPlaceholder("HH:MM PM")
                .setRequiredRange(SMALL_TEXTBOX_MIN_LENGTH, SMALL_TEXTBOX_MAX_LENGTH)
                .build();

        // add all components to modal, build it
        Modal ui = Modal.create("eventscheduler", "Event Scheduler")
                .addActionRow(group)
                .addActionRow(eventName)
                .addActionRow(date)
                .addActionRow(startTime)
                .addActionRow(endTime)
                .build();

        // pass the request off to {@code ModalHandler.java}. will be brought back here for final execution
        event.replyModal(ui).queue();
    }

    /**
     * Returns the name of the command.
     * @return the {@code String} command name of {@code this}.
     */
    @Override
    public String getName() {
        return "schedule";
    }

    /**
     * Returns the description of the command.
     * @return the {@code String} description of the command represented by {@code this}.
     */
    @Override
    public String getDescription() {
        return "if no conflicts exist with other events, schedules an event in the makerspace";
    }

    /**
     * Returns the list of command options for {@code this}.
     * @return the {@code ArrayList} containing the {@code OptionData} for the command represented by {@code this}.
     */
    @Override public ArrayList<OptionData> getOptions() {
        return this.options;
    }
}
