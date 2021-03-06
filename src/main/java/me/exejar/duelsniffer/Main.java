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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;

@Mod(modid = References.MODID, name = References.MODNAME, clientSideOnly = true, version = References.VERSION, acceptedMinecraftVersions = "1.8.9")
public class Main {

    public StatHud statHud;
    private static Main instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModConfig.getInstance().init();
        instance = this;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        statHud = new StatHud();
        statHud.setInGameStats(ModConfig.getInstance().getInGame());
        registerListeners(statHud, new QueueEvent(), new SetAPIEvent());
        registerCommands(new GetStats(), new GuiCommand());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    public static Main getInstance() { return instance; }

    private void registerListeners(Object... objects) {
        for (Object o : objects) {
            MinecraftForge.EVENT_BUS.register(o);
        }
    }

    private void registerCommands(ICommand... command) {
        Arrays.stream(command).forEachOrdered(ClientCommandHandler.instance::registerCommand);
    }

}
