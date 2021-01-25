package com.dolphln.particlesremover.commands;

import com.dolphln.particlesremover.ParticlesRemover;
import com.dolphln.particlesremover.core.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParticlesRemoverCommand implements CommandExecutor {

    private ParticlesRemover plugin;
    private String invalidSubcommand;

    public ParticlesRemoverCommand(ParticlesRemover plugin) {
        this.plugin = plugin;
        this.invalidSubcommand = "&8[&bParticlesRemover&8] &cInvalid arguments/command. Use /pr help to see all commands";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            String[] messages = {
                    "&8[&bParticlesRemover&8] &7Made by &3Dolphln&7. You are running on version &61.0&7.",
                    "&7To see all commands run &f/pr help"
            };
            sendMessages(messages, sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "debug":
                if (!sender.hasPermission(Permission.DEBUG.getPermission()) && !sender.isOp()) {
                    sender.sendMessage(Permission.DEBUG.getMissingError());
                    return true;
                }

                plugin.setDebug(!plugin.isDebug());
                String message = plugin.isDebug() ? "&8[&bParticlesRemover&8] &3Debug has been &aenabled&3." : "&8[&bParticlesRemover&8] &3Debug has been &cdisabled&3.";
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                break;
            case "player":
            case "bypass":
                if (!sender.hasPermission(Permission.BYPASS.getPermission()) && !sender.isOp()) {
                    sender.sendMessage(Permission.BYPASS.getMissingError());
                    return true;
                }

                if (args.length != 3) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', invalidSubcommand));
                    return true;
                }

                boolean enable = false;
                if (args[1].toLowerCase().equals("on") || args[1].toLowerCase().equals("enable")) {
                    enable = true;
                } else if (args[1].toLowerCase().equals("off") || args[1].toLowerCase().equals("disable")) {
                    enable = false;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', invalidSubcommand));
                    return true;
                }

                Player user = Bukkit.getPlayer(args[2]);
                if (user == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bParticlesRemover&8] &cPlayer " + user.getName() + " is not online or doesn't exist."));
                    return true;
                }

                if (enable) {
                    this.plugin.addBypassPlayers(user);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bParticlesRemover&8] &3Player has been added to the bypass list."));
                } else {
                    this.plugin.removeBypassPlayer(user);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bParticlesRemover&8] &3Player has been removed from the bypass list."));
                }
                break;
            case "help":
                if (!sender.hasPermission(Permission.HELP.getPermission()) && !sender.isOp()) {
                    sender.sendMessage(Permission.HELP.getMissingError());
                    return true;
                }

                String[] messages = {
                        "&8=============================",
                        "&3/pr bypass <on/off> <player> - &bEnable/Disable the bypass of the particles remover.",
                        "&3/pr debug - &bEnable/disable console debug.",
                        "&3/pr help - &bShows this command.",
                        "&8============================="
                };
                sendMessages(messages, sender);
                break;
            default:
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', invalidSubcommand));
        }
        return false;
    }

    private void sendMessages(String[] messages, CommandSender sender) {
        for (String message : messages) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

}
