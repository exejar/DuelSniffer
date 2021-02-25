package me.exejar.duelsniffer.events;

import me.exejar.duelsniffer.Main;
import me.exejar.duelsniffer.config.ModConfig;
import me.exejar.duelsniffer.statapi.HPlayer;
import me.exejar.duelsniffer.statapi.duels.Duels;
import me.exejar.duelsniffer.statapi.duels.DuelsModes;
import me.exejar.duelsniffer.utils.ChatUtils;
import me.exejar.duelsniffer.utils.Handler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

public class QueueEvent {

    @SubscribeEvent
    public void onPlayerQueue(ClientChatReceivedEvent event) {
        String msg = event.message.getFormattedText();
        String umsg = event.message.getUnformattedText();
        if (msg.contains("§r§e has joined (")) {
            event.setCanceled(true);
            ChatUtils.sendMessage(msg.replace("§k", ""));

            String[] args = umsg.split(" ");
            String players = args[3];
            int joinPos = Integer.parseInt(StringUtils.substringBetween(players, "(", "/"));
            int maxPlayers = Integer.parseInt(StringUtils.substringBetween(players, "/", ")"));

            System.out.println(joinPos + " " + maxPlayers);

            if (joinPos == maxPlayers) {
                Handler.asExecutor(()-> {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ignored){}

                    for (EntityPlayer player : Minecraft.getMinecraft().theWorld.playerEntities) {
                        if (player.getTeam().getRegisteredName().equalsIgnoreCase("§7§k")) {
                            if (!Main.getInstance().statHud.getHPlayers().containsKey(player.getName())) {
                                Handler.asExecutor(()-> {
                                    DuelsModes mode;
                                    if (!EnumUtils.isValidEnum(DuelsModes.class, ModConfig.getInstance().getHudMode())) {
                                        mode = DuelsModes.ALL;
                                    } else {
                                        mode = DuelsModes.valueOf(ModConfig.getInstance().getHudMode());
                                    }

                                    Duels duels = new Duels(player.getName(), player.getUniqueID().toString(), mode);
                                    Main.getInstance().statHud.addHPlayer(player.getName(), new HPlayer(duels));
                                });
                            }
                        }
                    }
                });
            }
        } else if (msg.equalsIgnoreCase("§r§f §r§f §r§1 §r§0 §r§2 §r§f §r§f §r§2 §r§0 §r§4 §r§3 §r§9 §r§2 §r§0 §r§0 §r§3 §r§9 §r§2 §r§0 §r§0 §r§3 §r§9 §r§2 §r§0 §r§0 §r") || msg.contains("§r§a§l▬▬▬▬▬▬▬")) {
            if (!Main.getInstance().statHud.getHPlayers().isEmpty()) Main.getInstance().statHud.getHPlayers().clear();
        } else if (msg.contains("§r§e has quit!")) {
            String playerName = umsg.replace(" has quit!", "");
            if (Main.getInstance().statHud.getHPlayers().containsKey(playerName)) Main.getInstance().statHud.removeHPlayer(playerName);
        }
    }

}
