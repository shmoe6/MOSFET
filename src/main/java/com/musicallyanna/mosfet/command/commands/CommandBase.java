package com.musicallyanna.mosfet.command.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

/**
 * Base class for all bot commands.
 * @author Anna Bontempo
 */
public abstract class CommandBase {

    /**
     * Collection to hold various command {@code OptionData}.
     */
    private final ArrayList<OptionData> options = null;

    /**
     * Carries out the behavior of the command.
     * @param event the event tied to the command.
     */
    public abstract void execute(SlashCommandInteractionEvent event);

    /**
     * Returns the name of the command.
     * @return the {@code String} command name of {@code this}.
     */
    public abstract String getName();

    /**
     * Returns the description of the command.
     * @return the {@code String} description of the command represented by {@code this}.
     */
    public abstract String getDescription();

    /**
     * Returns the list of command options for {@code this}.
     * @return the {@code ArrayList} containing the {@code OptionData} for the command represented by {@code this}.
     */
    public ArrayList<OptionData> getOptions() {
        return this.options;
    }
}
