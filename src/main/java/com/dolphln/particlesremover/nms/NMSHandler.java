package com.dolphln.particlesremover.nms;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;

public interface NMSHandler {

    public Channel getPlayerChannel(Player player);

}
