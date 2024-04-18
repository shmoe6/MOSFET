package com.musicallyanna.mosfet.command;

import com.musicallyanna.mosfet.command.commands.CheckOpenCommand;
import com.musicallyanna.mosfet.command.commands.CommandBase;
import com.musicallyanna.mosfet.command.commands.TestCommand;
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

//    /**
//     * The current package of {@code CommandManager.java}.
//     */
//    private final String currentPkg = "com.musicallyanna.mosfet.command";

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
        this.commandList.put("test", new TestCommand());

    // reflection attempt to auto load command classes. coming soon...
//        @NotNull
//        final Package[] currentSubPkgs = this.getClass().getPackage().getPackages();
//
//        Package commandsPkg = null;
//        boolean foundPkg = false;
//
//        for (int i = 0; !foundPkg && i < currentSubPkgs.length; i++) {
//
//            if (currentSubPkgs[i].getName().equals(this.currentPkg + ".commands")) {
//
//                commandsPkg = currentSubPkgs[i];
//                foundPkg = true;
//            }
//        }
//
//        assert commandsPkg != null: "ERROR: com.musicallyanna.mosfet.command.commands not found.";
//        Class<?>[] classes = commandsPkg.getClass().getClasses();
//
//        for (Class<?> c : classes) {
//            System.out.println(c);
//        }
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

        // declare and initalize list of command data for registration
        List<CommandData> commandData = new ArrayList<CommandData>();

        // loop through commands in the command list, adding each one's name and description to the command data
        for (HashMap.Entry<String, CommandBase> entry : this.commandList.entrySet()) {
            commandData.add(Commands.slash(entry.getKey(), entry.getValue().getDescription()));
        }

        // send data to api to update/register the commands
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
