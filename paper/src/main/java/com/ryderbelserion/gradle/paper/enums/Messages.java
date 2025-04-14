package com.ryderbelserion.gradle.paper.enums;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.fusion.api.utils.StringUtils;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.utils.AdvUtils;
import com.ryderbelserion.gradle.paper.WhatTheCluck;
import com.ryderbelserion.gradle.paper.configuration.ConfigManager;
import com.ryderbelserion.gradle.paper.configuration.types.ConfigKeys;
import com.ryderbelserion.gradle.paper.configuration.types.MiscKeys;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Messages {

    must_be_console_sender(MiscKeys.must_be_console_sender),
    feature_disabled(MiscKeys.feature_disabled),
    unknown_command(MiscKeys.unknown_command),
    no_permission(MiscKeys.no_permission),
    player_only(MiscKeys.player_only),
    reload_plugin(MiscKeys.reload_message),
    correct_usage(MiscKeys.correct_usage);

    private Property<String> property;

    private Property<List<String>> properties;
    private boolean isList = false;

    Messages(@NotNull final Property<String> property) {
        this.property = property;
    }

    Messages(@NotNull final Property<List<String>> properties, final boolean isList) {
        this.properties = properties;
        this.isList = isList;
    }

    private final WhatTheCluck plugin = WhatTheCluck.getPlugin(WhatTheCluck.class);

    private final ConfigManager configManager = this.plugin.getConfigManager();

    private final SettingsManager config = this.configManager.getConfig();

    private final SettingsManager locale = this.configManager.getLocale();

    public String getString() {
        return this.locale.getProperty(this.property);
    }

    public List<String> getList() {
        return this.locale.getProperty(this.properties);
    }

    public Component getMessage(@NotNull final Audience sender) {
        return getMessage(sender, new HashMap<>());
    }

    public Component getMessage(@NotNull final Audience sender, @NotNull final String placeholder, @NotNull final String replacement) {
        Map<String, String> placeholders = new HashMap<>() {{
            put(placeholder, replacement);
        }};

        return getMessage(sender, placeholders);
    }

    public Component getMessage(@NotNull final Audience sender, @NotNull final Map<String, String> placeholders) {
        placeholders.putIfAbsent("prefix", this.config.getProperty(ConfigKeys.message_prefix));

        return parse(sender, placeholders);
    }

    public void sendMessage(final Audience sender, final String placeholder, final String replacement) {
        final State state = this.config.getProperty(ConfigKeys.message_state);

        switch (state) {
            case send_message -> sendRichMessage(sender, placeholder, replacement);
            case send_actionbar -> sendActionBar(sender, placeholder, replacement);
        }
    }

    public void sendMessage(final Audience sender, final Map<String, String> placeholders) {
        final State state = this.config.getProperty(ConfigKeys.message_state);

        switch (state) {
            case send_message -> sendRichMessage(sender, placeholders);
            case send_actionbar -> sendActionBar(sender, placeholders);
        }
    }

    public void sendMessage(final Audience sender) {
        final State state = this.config.getProperty(ConfigKeys.message_state);

        switch (state) {
            case send_message -> sendRichMessage(sender);
            case send_actionbar -> sendActionBar(sender);
        }
    }

    public void sendActionBar(final Audience sender, final String placeholder, final String replacement) {
        final Component component = getMessage(sender, placeholder, replacement);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendActionBar(final Audience sender, final Map<String, String> placeholders) {
        final Component component = getMessage(sender, placeholders);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendActionBar(final Audience sender) {
        final Component component = getMessage(sender);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendRichMessage(final Audience sender, final String placeholder, final String replacement) {
        final Component component = getMessage(sender, placeholder, replacement);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public void sendRichMessage(final Audience sender, final Map<String, String> placeholders) {
        final Component component = getMessage(sender, placeholders);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public void sendRichMessage(final Audience sender) {
        final Component component = getMessage(sender);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public final boolean isList() {
        return this.isList;
    }

    public void migrate() {
        if (this.isList) {
            this.locale.setProperty(this.properties, AdvUtils.convert(this.locale.getProperty(this.properties), true));

            return;
        }

        this.locale.setProperty(this.property, AdvUtils.convert(this.locale.getProperty(this.property), true));
    }

    private Component parse(final Audience audience, final Map<String, String> placeholders) {
        String message;

        if (this.isList) {
            message = StringUtils.toString(getList());
        } else {
            message = getString();
        }

        return FusionCore.FusionProvider.get().placeholders(audience, message, placeholders);
    }
}