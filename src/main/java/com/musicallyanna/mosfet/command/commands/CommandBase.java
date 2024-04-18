package com.musicallyanna.mosfet.command.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * Base class for all bot commands.
 * @author Anna Bontempo
 */
public abstract class CommandBase {

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
}
