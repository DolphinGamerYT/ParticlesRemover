package com.dolphln.particlesremover.nms;

import io.netty.channel.Channel;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSHandler_1_15_R1 implements NMSHandler{

    @Override
    public Channel getPlayerChannel(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
    }
    
}
