package com.musicallyanna.mosfet.command.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TestCommand extends CommandBase {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("whoa, im alive").queue();
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "test command";
    }
}
