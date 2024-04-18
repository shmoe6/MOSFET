package com.musicallyanna.mosfet.command.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

/**
 * Command that reports the hours of the makerspace for the current day.
 * @author Anna Bontempo
 */
public class CheckOpenCommand extends CommandBase {

    /**
     * A {@code java.util.Map} that contains the current public hours of the makerspace.
     */
    private final Map<DayOfWeek, String> makerspaceHours = Map.of(
            DayOfWeek.MONDAY, "9am-3pm",
            DayOfWeek.WEDNESDAY, "9am-3pm",
            DayOfWeek.FRIDAY, "9am-3pm"
    );

    /**
     * Replies to the user with the hours of the makerspace for the day.
     * @param event the event tied to the command.
     */
    public void execute(SlashCommandInteractionEvent event) {

        // holds the hours for the day
        String currentHours = "";

        // get current day of week, report hours based on current day
        switch (LocalDate.now().getDayOfWeek()) {
            case MONDAY -> currentHours = makerspaceHours.get(DayOfWeek.MONDAY);
            case WEDNESDAY -> currentHours = makerspaceHours.get(DayOfWeek.WEDNESDAY);
            case FRIDAY -> currentHours = makerspaceHours.get(DayOfWeek.FRIDAY);
            default -> currentHours = "closed";
        }

        // reply to user with current day's makerspace hours
        event.reply("Makerspace hours for today: " + currentHours).queue();
    }

    /**
     * Returns the name of the command.
     * @return the {@code String} command name of {@code this}.
     */
    @Override
    public String getName() {
        return "checkopen";
    }

    /**
     * Returns the description of the command.
     * @return the {@code String} description of the command represented by {@code this}.
     */
    @Override
    public String getDescription() {
        return "reports the makerspace hours for today";
    }
}
