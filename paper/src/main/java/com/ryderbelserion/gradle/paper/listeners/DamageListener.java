package com.ryderbelserion.gradle.paper.listeners;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.gradle.paper.WhatTheCluck;
import com.ryderbelserion.gradle.paper.configuration.types.ConfigKeys;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public class DamageListener implements Listener {

    private final SettingsManager config;

    public DamageListener(@NotNull final WhatTheCluck plugin) {
        this.config = plugin.getConfigManager().getConfig();
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        final Entity damager = event.getDamager();

        if (!(damager instanceof LivingEntity livingEntity)) return;

        final double damage = this.config.getProperty(ConfigKeys.chicken_damage);

        if (damage <= 0.0D) return;

        final Entity entity = event.getEntity();

        if (entity.isDead()) return;

        final World world = entity.getWorld();

        world.spawnEntity(entity.getLocation(), EntityType.CHICKEN, CreatureSpawnEvent.SpawnReason.CUSTOM, key -> {
            final Mob mob = (Mob) key;

            mob.setTarget(livingEntity);
            mob.setAggressive(true);

            mob.registerAttribute(Attribute.ATTACK_DAMAGE);

            final AttributeInstance attribute = mob.getAttribute(Attribute.ATTACK_DAMAGE);

            if (attribute != null) {
                attribute.setBaseValue(damage);
            }
        });
    }
}