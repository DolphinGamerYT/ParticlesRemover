package com.dolphln.particlesremover.core;

import org.bukkit.ChatColor;

public enum Permission {

    HELP("help"),
    BYPASS("bypass"),
    DEBUG("debug"),;

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getRawPermission() {
        return this.permission;
    }

    public String getPermission() {
        return "particlesremover." + this.permission;
    }

    public String getMissingError() {
        return ChatColor.translateAlternateColorCodes('&', "&cYou are missing the following permission: &c" + ChatColor.ITALIC + getPermission());
    }
}
