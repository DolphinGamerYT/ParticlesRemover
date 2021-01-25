package com.dolphln.particlesremover.core;

import com.dolphln.particlesremover.ParticlesRemover;
import io.netty.channel.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PacketListener {

    private final ParticlesRemover plugin;

    public PacketListener(ParticlesRemover plugin) {
        this.plugin = plugin;
    }

    public void start(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                super.channelRead(channelHandlerContext, packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                if (packet.getClass().getName().equals("PacketPlayOutWorldParticles")) {
                    if (!plugin.isBypassPlayer(player)) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bParticle Packet has been &cdenied &bfor Player " + player.getName()));
                        return;
                    }
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bParticle Packet has been &aenabled &bfor Player " + player.getName()));
                }
                super.write(channelHandlerContext, packet, channelPromise);
            }
        };

        ChannelPipeline pipeline = this.plugin.getNmsHandler().getPlayerChannel(player).pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
    }

    public void end(Player player) {
        Channel channel = this.plugin.getNmsHandler().getPlayerChannel(player);
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
        });
    }

}
