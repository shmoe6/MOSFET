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

public class CommandManager extends ListenerAdapter {

    private final HashMap<String, CommandBase> commandList;

    public CommandManager() {

        this.commandList = new HashMap<String, CommandBase>();
        this.commandList.put("checkopen", new CheckOpenCommand());
        this.commandList.put("test", new TestCommand());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        final String command = event.getName();

        if (this.commandList.containsKey(command)) {
            this.commandList.get(command).execute(event);
        }
    }

    // guild commands
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        List<CommandData> commandData = new ArrayList<CommandData>();

        for (HashMap.Entry<String, CommandBase> entry : this.commandList.entrySet()) {
            commandData.add(Commands.slash(entry.getKey(), entry.getValue().getDescription()));
        }

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
