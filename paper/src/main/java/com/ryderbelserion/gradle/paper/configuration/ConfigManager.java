package com.ryderbelserion.gradle.paper.configuration;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.gradle.paper.configuration.types.ConfigKeys;
import com.ryderbelserion.gradle.paper.configuration.types.MiscKeys;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigManager {

    private SettingsManager config;
    private SettingsManager locale;

    public void load(@NotNull final File dataFolder) {
        final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        this.config = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class)
                .create();

        this.locale = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "messages.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(MiscKeys.class)
                .create();
    }

    public void reload() {
        if (this.config != null) {
            this.config.reload();
        }

        if (this.locale != null) {
            this.locale.reload();
        }
    }

    public final SettingsManager getConfig() {
        return this.config;
    }

    public final SettingsManager getLocale() {
        return this.locale;
    }
}