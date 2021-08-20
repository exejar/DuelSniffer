package me.exejar.duelsniffer;

import me.exejar.duelsniffer.commands.GetStats;
import me.exejar.duelsniffer.commands.GuiCommand;
import me.exejar.duelsniffer.config.ModConfig;
import me.exejar.duelsniffer.events.QueueEvent;
import me.exejar.duelsniffer.events.SetAPIEvent;
import me.exejar.duelsniffer.hud.StatHud;
import me.exejar.duelsniffer.utils.References;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;

@Mod(modid = References.MODID, name = References.MODNAME, version = References.VERSION, clientSideOnly = true)
public class DuelSniffer {

    private static DuelSniffer instance;
    public StatHud statHud;

    public static DuelSniffer getInstance() {
        return instance;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModConfig.getInstance().init();
        instance = this;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        statHud = new StatHud();
        statHud.setInGameStats(ModConfig.getInstance().getInGame());
        registerListeners(statHud, new QueueEvent(), new SetAPIEvent());
        registerCommands(new GetStats(), new GuiCommand());
    }

    private void registerListeners(Object... objects) {
        for (Object o : objects) {
            MinecraftForge.EVENT_BUS.register(o);
        }
    }

    private void registerCommands(ICommand... command) {
        Arrays.stream(command).forEachOrdered(ClientCommandHandler.instance::registerCommand);
    }
}
