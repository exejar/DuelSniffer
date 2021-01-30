package me.dooger.duelsniffer.events;

import me.dooger.duelsniffer.config.ModConfig;
import me.dooger.duelsniffer.utils.ChatColor;
import me.dooger.duelsniffer.utils.ChatUtils;
import me.dooger.duelsniffer.utils.References;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SetAPIEvent {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if (message.startsWith("Your new API key is")) {
            String apiKey = message.replace("Your new API key is ", "");
            ChatUtils.sendMessage(ChatColor.GREEN + References.MODNAMEPREF + " API Key set");
            ModConfig.getInstance().setApiKey(apiKey);
            ModConfig.getInstance().save();
        }
    }

}
