package com.musicallyanna.mosfet.command;

import com.musicallyanna.mosfet.command.commands.CheckOpenCommand;
import com.musicallyanna.mosfet.command.commands.CommandBase;
import com.musicallyanna.mosfet.command.commands.TestCommand;
import com.musicallyanna.mosfet.command.commands.scheduling.ScheduleEventCommand;
import com.musicallyanna.mosfet.command.commands.scheduling.ViewScheduleCommand;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Custom command manager to handle Application (Slash) Commands.
 * @author Anna Bontempo
 */
public class CommandManager extends ListenerAdapter {

    /**
     * {@code java.util.HashMap} that stores the name of each command as the key with an
     * instance of that command as the value.
     */
    private final HashMap<String, CommandBase> commandList;

    /**
     * No argument constructor.
     */
    public CommandManager() {

        this.commandList = new HashMap<String, CommandBase>(); // initialize command list

        // manually add each command to the command list
        this.commandList.put("checkopen", new CheckOpenCommand());
        this.commandList.put("schedule", new ScheduleEventCommand());
        this.commandList.put("test", new TestCommand());
        this.commandList.put("viewschedule", new ViewScheduleCommand());
    }

    /**
     * Handles incoming slash commands by calling the execute() method for the respective command.
     * @param event the {@code net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent},
     *              which is the command the user executed.
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        // the name of the slash command being called
        final String command = event.getName();

        // find the command in the command list and execute it if it's there
        if (this.commandList.containsKey(command)) {
            this.commandList.get(command).execute(event);
        }
    }

    /* Guild Commands */

    /**
     * Registers guild commands. Discord's maximum is one hundred.
     * @param event the {@code net.dv8tion.jda.api.events.guild.GuildReadyEvent} that triggers the registration of guild commands.
     */
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        // declare and initialize list of command data for registration
        List<CommandData> commandData = new ArrayList<CommandData>();

        // loop through commands in the command list, adding each one's name and description to the command data
        for (HashMap.Entry<String, CommandBase> entry : this.commandList.entrySet()) {

            if (entry.getValue().getOptions() == null) {
                commandData.add(Commands.slash(entry.getKey(), entry.getValue().getDescription()));

            } else {
                commandData.add(Commands.slash(entry.getKey(), entry.getValue().getDescription()).addOptions(entry.getValue().getOptions()));
            }
        }

        // send data to api to update/register the commands
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
