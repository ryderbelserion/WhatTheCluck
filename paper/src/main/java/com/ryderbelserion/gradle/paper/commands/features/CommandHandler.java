package com.ryderbelserion.gradle.paper.commands.features;

import com.ryderbelserion.gradle.paper.WhatTheCluck;
import com.ryderbelserion.gradle.paper.commands.BaseCommand;
import com.ryderbelserion.gradle.paper.enums.Messages;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.extention.meta.MetaKey;
import dev.triumphteam.cmd.core.message.MessageKey;
import org.bukkit.command.CommandSender;

public class CommandHandler {

    private final WhatTheCluck plugin;

    private final BukkitCommandManager<CommandSender> manager;

    public CommandHandler(final WhatTheCluck plugin) {
        this.plugin = plugin;
        this.manager = BukkitCommandManager.create(this.plugin);
    }

    public void build() {
        this.manager.registerCommand(new BaseCommand(this.plugin));

        this.manager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, (sender, context) -> context.getMeta().get(MetaKey.SYNTAX).ifPresent(key -> Messages.correct_usage.sendMessage(sender, "{usage}", key)));

        this.manager.registerMessage(MessageKey.TOO_MANY_ARGUMENTS, (sender, context) -> context.getMeta().get(MetaKey.SYNTAX).ifPresent(key -> Messages.correct_usage.sendMessage(sender, "{usage}", key)));

        this.manager.registerMessage(MessageKey.INVALID_ARGUMENT, (sender, context) -> Messages.correct_usage.sendMessage(sender, "{usage}", context.getSyntax()));

        this.manager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, context) -> Messages.no_permission.sendMessage(sender, "{permission}", context.getPermission().toString()));

        this.manager.registerMessage(BukkitMessageKey.UNKNOWN_COMMAND, (sender, context) -> Messages.unknown_command.sendMessage(sender, "{command}", context.getInvalidInput()));

        this.manager.registerMessage(BukkitMessageKey.CONSOLE_ONLY, (sender, context) -> Messages.must_be_console_sender.sendMessage(sender));

        this.manager.registerMessage(BukkitMessageKey.PLAYER_ONLY, (sender, context) -> Messages.player_only.sendMessage(sender));
    }
}