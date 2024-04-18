package com.musicallyanna.mosfet.command.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class CheckOpenCommand extends CommandBase {

    private final Map<DayOfWeek, String> makerspaceHours = Map.of(
            DayOfWeek.MONDAY, "9am-3pm",
            DayOfWeek.WEDNESDAY, "9am-3pm",
            DayOfWeek.FRIDAY, "9am-3pm"
    );

    public void execute(SlashCommandInteractionEvent event) {

        String currentHours = "";

        // get current day of week, report hours based on current day
        switch (LocalDate.now().getDayOfWeek()) {
            case MONDAY -> currentHours = makerspaceHours.get(DayOfWeek.MONDAY);
            case WEDNESDAY -> currentHours = makerspaceHours.get(DayOfWeek.WEDNESDAY);
            case FRIDAY -> currentHours = makerspaceHours.get(DayOfWeek.FRIDAY);
            default -> currentHours = "closed";
        }

        event.reply("Makerspace hours for today: " + currentHours).queue();
    }

    @Override
    public String getName() {
        return "checkopen";
    }

    @Override
    public String getDescription() {
        return "reports the makerspace hours for today";
    }
}
