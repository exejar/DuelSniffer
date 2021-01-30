package me.dooger.duelsniffer.commands;

import me.dooger.duelsniffer.Main;
import me.dooger.duelsniffer.hud.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;
import java.util.List;

public class GuiCommand extends DuelCommandBase {

    @Override
    public String getCommandName() {
        return "dsniff";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("gui", "hud", "duelsniffer", "duelsniff");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Main.getInstance().statHud.updatePos();
        Minecraft.getMinecraft().displayGuiScreen(new Gui());
        MinecraftForge.EVENT_BUS.unregister(this);
    }

}
