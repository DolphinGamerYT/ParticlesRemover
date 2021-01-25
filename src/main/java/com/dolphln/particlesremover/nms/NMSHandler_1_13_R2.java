package com.dolphln.particlesremover.nms;

import io.netty.channel.Channel;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSHandler_1_13_R2 implements NMSHandler{

    @Override
    public Channel getPlayerChannel(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
    }
    
}
