package com.musicallyanna.mosfet.command.commands.control;

import com.musicallyanna.mosfet.Bot;
import com.musicallyanna.mosfet.command.commands.CommandBase;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * Command to shut down the bot from a Discord channel. Bot owner only.
 * @author Anna Bontempo
 */
public class ShutdownCommand extends CommandBase {

    /**
     * Shuts down the bot.
     * @param event the event tied to the command.
     */
    @Override
    public void execute(SlashCommandInteractionEvent event) {

        // get bot owner username
        final String botOwnerUsername = Bot.config.get("BOT_OWNER_USERNAME");

        // check if command sent by bot owner
        if (event.getUser().getName().equals(botOwnerUsername)) {
            event.reply("Shutting down...").queue(); // notify user of shutdown
            event.getJDA().shutdown(); // shut down the bot
            System.exit(0); // exit the program

        } else {
            event.reply("You do not have permission to use this command!").queue(); // notify user of insufficient permissions
        }
    }

    /**
     * Returns the name of the command.
     * @return the {@code String} command name of {@code this}.
     */
    @Override
    public String getName() {
        return "shutdown";
    }

    /**
     * Returns the description of the command.
     * @return the {@code String} description of the command represented by {@code this}.
     */
    @Override
    public String getDescription() {
        return "shuts down the bot (bot owner only command)";
    }
}
