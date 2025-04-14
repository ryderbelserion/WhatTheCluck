package com.ryderbelserion.gradle.paper.configuration.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import org.jetbrains.annotations.NotNull;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class MiscKeys implements SettingsHolder {

    @Override
    public void registerComments(@NotNull CommentsConfiguration conf) {
        String[] header = {
                "Github: https://github.com/ryderbelserion",
                "Support: https://discord.gg/w7yCw4M9za",
                "",
                "Features/Issues: https://github.com/ryderbelserion/WhatTheCluck/issues",
                ""
        };

        conf.setComment("root", header);

        conf.setComment("entity", "All settings related to entities.");

        conf.setComment("entity.chicken", "All settings related to the clucker!");
    }

    public static final Property<String> reload_message = newProperty("root.reload", "{prefix}<yellow>You have reloaded the plugin.");

    @Comment("A list of available placeholders: {command}")
    public static final Property<String> unknown_command = newProperty("root.unknown-command", "{prefix}<red>{command} is not a known command.");

    @Comment("A list of available placeholders: {usage}")
    public static final Property<String> correct_usage = newProperty("root.correct-usage", "{prefix}<red>The correct usage for this command is <yellow>{usage}");

    public static final Property<String> feature_disabled = newProperty("root.feature-disabled", "{prefix}<red>This feature is disabled.");

    public static final Property<String> must_be_console_sender = newProperty("root.must-be-console-sender", "{prefix}<red>You must be using console to use this command.");

    public static final Property<String> player_only = newProperty("root.player-only", "{prefix}<red>Only players can use this command.");

    @Comment("A list of available placeholders: {permission}")
    public static final Property<String> no_permission = newProperty("root.no-permission", "{prefix}<red>You do not have permission to use that command!");

}