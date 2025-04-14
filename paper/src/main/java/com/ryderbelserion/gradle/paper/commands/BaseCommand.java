package com.ryderbelserion.gradle.paper.commands;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.gradle.paper.WhatTheCluck;
import com.ryderbelserion.gradle.paper.configuration.ConfigManager;
import com.ryderbelserion.gradle.paper.enums.Messages;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Permission;
import dev.triumphteam.cmd.core.enums.Mode;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Command(value = "whatthecluck", alias = {"wtc"})
public class BaseCommand {

    private final ConfigManager configManager;
    private final FusionPaper fusion;

    public BaseCommand(@NotNull final WhatTheCluck plugin) {
        this.configManager = plugin.getConfigManager();
        this.fusion = plugin.getFusion();
    }

    @Command
    @Permission(value = "whatthecluck.use", def = Mode.OP)
    public void perform(final CommandSender sender) {
        this.configManager.reload();

        this.fusion.reload();

        Messages.reload_plugin.sendMessage(sender);
    }
}