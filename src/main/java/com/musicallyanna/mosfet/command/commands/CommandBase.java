package com.musicallyanna.mosfet.command.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class CommandBase {

    public abstract void execute(SlashCommandInteractionEvent event);

    public abstract String getName();

    public abstract String getDescription();
}
