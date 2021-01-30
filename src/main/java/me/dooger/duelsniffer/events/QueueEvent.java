package me.dooger.duelsniffer.events;

import me.dooger.duelsniffer.Main;
import me.dooger.duelsniffer.statapi.HPlayer;
import me.dooger.duelsniffer.statapi.duels.Duels;
import me.dooger.duelsniffer.statapi.duels.DuelsModes;
import me.dooger.duelsniffer.utils.ChatUtils;
import me.dooger.duelsniffer.utils.Handler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class QueueEvent {

    @SubscribeEvent
    public void onPlayerQueue(ClientChatReceivedEvent event) {
        String msg = event.message.getFormattedText();
        String umsg = event.message.getUnformattedText();
        if (msg.contains("§r§e has joined (")) {
            event.setCanceled(true);
            ChatUtils.sendMessage(msg.replace("§k", ""));
        } else if (msg.equalsIgnoreCase("§r§eThe game starts in §r§c5§r§e seconds!§r")) {
            if (!Main.getInstance().statHud.getHPlayers().isEmpty()) Main.getInstance().statHud.getHPlayers().clear();

            for (EntityPlayer player : Minecraft.getMinecraft().theWorld.playerEntities) {
                if (player.getTeam().getRegisteredName().equalsIgnoreCase("§7§k")) {
                    Handler.asExecutor(()-> {
                        Duels duels = new Duels(player.getName(), player.getUniqueID().toString(), DuelsModes.ALL);
                        new HPlayer(duels);
                    });
                }
            }
        }
    }

}
