package com.ryderbelserion.gradle.paper;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.gradle.paper.commands.features.CommandHandler;
import com.ryderbelserion.gradle.paper.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WhatTheCluck extends JavaPlugin {

    private ConfigManager configManager;

    private FusionPaper fusion;

    @Override
    public void onEnable() {
        this.fusion = new FusionPaper(getComponentLogger(), getDataPath());
        this.fusion.enable(this);

        this.configManager = new ConfigManager();
        this.configManager.load(getDataFolder());

        new CommandHandler(this).build();
    }

    @Override
    public void onDisable() {
        if (this.fusion != null) {
            this.fusion.save();
        }
    }

    public final ConfigManager getConfigManager() {
        return this.configManager;
    }

    public final FusionPaper getFusion() {
        return this.fusion;
    }
}