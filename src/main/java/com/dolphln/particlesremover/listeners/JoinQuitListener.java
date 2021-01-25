package com.dolphln.particlesremover.listeners;

import com.dolphln.particlesremover.ParticlesRemover;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    private ParticlesRemover plugin;

    public JoinQuitListener(ParticlesRemover plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.plugin.getPacketListener().start(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        plugin.getPacketListener().end(e.getPlayer());
        plugin.removeBypassPlayer(e.getPlayer());
    }
}
