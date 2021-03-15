package me.exejar.duelsniffer.events;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.exejar.duelsniffer.Main;
import me.exejar.duelsniffer.config.ModConfig;
import me.exejar.duelsniffer.statapi.HPlayer;
import me.exejar.duelsniffer.statapi.duels.Duels;
import me.exejar.duelsniffer.statapi.duels.DuelsModes;
import me.exejar.duelsniffer.utils.Handler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.EnumUtils;

import java.util.UUID;

public class QueueEvent {

    Minecraft mc = Minecraft.getMinecraft();
    private boolean ingame;
    private World world;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START || Minecraft.getMinecraft().theWorld == null) return;

        if (world != mc.theWorld) {
            this.world = mc.theWorld;
            mc.thePlayer.sendChatMessage("/locraw");
        }

        if (!mc.isGamePaused() && mc.thePlayer != null && ingame) {
            this.tick();
        }
    }

    private void tick() {
        for (NetworkPlayerInfo playerInfo : mc.getNetHandler().getPlayerInfoMap()) {
            UUID playerUUID = playerInfo.getGameProfile().getId();
            String name = playerInfo.getGameProfile().getName();

            if (playerInfo.getPlayerTeam() != null) {
                String registeredName = playerInfo.getPlayerTeam().getRegisteredName();

                if (registeredName.contains("§7§k")) {
                    if (!Main.getInstance().statHud.getHPlayers().containsKey(name) && !Main.getInstance().statHud.getAssembly().contains(name)) {
                        Handler.asExecutor(()-> {
                            Main.getInstance().statHud.addAseembly(name);

                            DuelsModes mode;
                            if (!EnumUtils.isValidEnum(DuelsModes.class, ModConfig.getInstance().getHudMode())) {
                                mode = DuelsModes.ALL;
                            } else {
                                mode = DuelsModes.valueOf(ModConfig.getInstance().getHudMode());
                            }

                            Duels duels = new Duels(name, playerUUID.toString(), mode);
                            Main.getInstance().statHud.addHPlayer(name, new HPlayer(duels));

                            Main.getInstance().statHud.removeAssembly(name);
                        });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerQueue(ClientChatReceivedEvent event) {
        String msg = event.message.getFormattedText();
        String umsg = event.message.getUnformattedText();
        if (msg.contains("§r§e has quit!")) {
            String playerName = umsg.replace(" has quit!", "");
            if (Main.getInstance().statHud.getHPlayers().containsKey(playerName)) Main.getInstance().statHud.removeHPlayer(playerName);
        } else if (umsg.startsWith("{\"server\":")) {
            event.setCanceled(true);
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(umsg).getAsJsonObject();

//            idk what to do with these...but they can be very useful
            String server = obj.get("server").getAsString();
            String gametype = obj.get("gametype").getAsString();

            if (obj.get("mode") != null && obj.get("map") != null && gametype.equalsIgnoreCase("DUELS")) {
                this.ingame = true;
            } else {
                this.ingame = false;
                if (!Main.getInstance().statHud.getHPlayers().isEmpty()) Main.getInstance().statHud.clearHPlayerList();
            }
        } else if (msg.contains("§r§a§l▬▬▬▬▬▬▬") && !Main.getInstance().statHud.getInGameStats()) {
            if (!Main.getInstance().statHud.getHPlayers().isEmpty()) Main.getInstance().statHud.clearHPlayerList();
        }
    }

//    if (msg.equalsIgnoreCase("§r§f §r§f §r§1 §r§0 §r§2 §r§f §r§f §r§2 §r§0 §r§4 §r§3 §r§9 §r§2 §r§0 §r§0 §r§3 §r§9 §r§2 §r§0 §r§0 §r§3 §r§9 §r§2 §r§0 §r§0 §r") || msg.contains("§r§a§l▬▬▬▬▬▬▬")) {
//            if (!Main.getInstance().statHud.getHPlayers().isEmpty()) Main.getInstance().statHud.clearHPlayerList();
//        } else

//    if (msg.contains("§r§e has joined (")) {
//            event.setCanceled(true);
//            ChatUtils.sendMessage(msg.replace("§k", ""));
//
//            String[] args = umsg.split(" ");
//            String players = args[3];
//            int joinPos = Integer.parseInt(StringUtils.substringBetween(players, "(", "/"));
//            int maxPlayers = Integer.parseInt(StringUtils.substringBetween(players, "/", ")"));
//
//            System.out.println(joinPos + " " + maxPlayers);
//
//            if (joinPos == maxPlayers) {
//                Handler.asExecutor(()-> {
//                    try {
//                        Thread.sleep(300);
//                    } catch (InterruptedException ignored){}
//
//                    for (EntityPlayer player : Minecraft.getMinecraft().theWorld.playerEntities) {
//                        if (player.getTeam().getRegisteredName().equalsIgnoreCase("§7§k")) {
//                            if (!Main.getInstance().statHud.getHPlayers().containsKey(player.getName())) {
//                                Handler.asExecutor(()-> {
//                                    DuelsModes mode;
//                                    if (!EnumUtils.isValidEnum(DuelsModes.class, ModConfig.getInstance().getHudMode())) {
//                                        mode = DuelsModes.ALL;
//                                    } else {
//                                        mode = DuelsModes.valueOf(ModConfig.getInstance().getHudMode());
//                                    }
//
//                                    Duels duels = new Duels(player.getName(), player.getUniqueID().toString(), mode);
//                                    Main.getInstance().statHud.addHPlayer(player.getName(), new HPlayer(duels));
//                                });
//                            }
//                        }
//                    }
//                });
//            }
//        } else i

}
