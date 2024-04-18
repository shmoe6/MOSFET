package com.musicallyanna.mosfet.command.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * Test command.
 * @author Anna Bontempo
 */
public class TestCommand extends CommandBase {

    /**
     * Replies to the user with an indicator that the bot is working.
     * @param event the event tied to the command.
     */
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("whoa, im alive").queue();
    }

    /**
     * Returns the name of the command.
     * @return the {@code String} command name of {@code this}.
     */
    @Override
    public String getName() {
        return "test";
    }

    /**
     * Returns the description of the command.
     * @return the {@code String} description of the command represented by {@code this}.
     */
    @Override
    public String getDescription() {
        return "test command";
    }
}
