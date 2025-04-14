package com.ryderbelserion.gradle.paper.configuration.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.gradle.paper.enums.State;
import org.jetbrains.annotations.NotNull;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

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

    public static final Property<String> message_prefix = newProperty("root.prefix", "<red>[WhatTheCluck] ");

    @Comment({
            "This option will tell the plugin to send all messages as action bars or messages in chat.",
            "",
            "send_message -> sends messages in chat.",
            "send_actionbar -> sends messages in actionbar.",
            ""
    })
    public static final Property<State> message_state = newBeanProperty(State.class, "root.message-state", State.send_message);

    @Comment("This defines how much damage a chicken does.")
    public static final Property<Double> chicken_damage = newProperty("entity.chicken.damage", 0.0D);

}