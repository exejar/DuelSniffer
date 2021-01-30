package me.dooger.duelsniffer.commands;

import me.dooger.duelsniffer.utils.ChatColor;
import me.dooger.duelsniffer.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public abstract class DuelCommandBase extends CommandBase {

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatColor.translateAlternateColorCodes(message)));
    }

    public void sendMessageWithBorder(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatColor.translateAlternateColorCodes(References.PREFIX + message + References.SUFFIX)));
    }

}
