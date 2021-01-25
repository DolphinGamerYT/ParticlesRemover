package com.dolphln.particlesremover;

import com.dolphln.particlesremover.commands.ParticlesRemoverCommand;
import com.dolphln.particlesremover.core.PacketListener;
import com.dolphln.particlesremover.listeners.JoinQuitListener;
import com.dolphln.particlesremover.nms.NMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public final class ParticlesRemover extends JavaPlugin {

    private static ParticlesRemover instance;

    private NMSHandler nmsHandler;
    private PacketListener packetListener;

    private ArrayList<UUID> bypassPlayers;
    private Boolean debug;

    @Override
    public void onEnable() {
        instance = this;

        try {
            this.nmsHandler = (NMSHandler) Class.forName("com.dolphln.particlesremover.nms.NMSHandler_" + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].substring(1)).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.packetListener = new PacketListener(this);

        this.bypassPlayers = new ArrayList<>();
        this.debug = false;

        getCommand("particlesremover").setExecutor(new ParticlesRemoverCommand(this));

        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(this), this);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.packetListener.end(player);
        }
    }

    public static ParticlesRemover getInstance() {
        return instance;
    }

    public NMSHandler getNmsHandler() {
        return nmsHandler;
    }

    public PacketListener getPacketListener() {
        return packetListener;
    }

    public Boolean isDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public void addBypassPlayers(Player player) {
        if (this.bypassPlayers.contains(player.getUniqueId())) {
            this.bypassPlayers.add(player.getUniqueId());
        }
    }

    public void removeBypassPlayer(Player player) {
        this.bypassPlayers.remove(player.getUniqueId());
    }

    public boolean isBypassPlayer(Player player) {
        return this.bypassPlayers.contains(player.getUniqueId());
    }
}
