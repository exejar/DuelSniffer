package me.dooger.duelsniffer.commands;

import me.dooger.duelsniffer.statapi.HypixelAPI;
import me.dooger.duelsniffer.statapi.duels.Duels;
import me.dooger.duelsniffer.statapi.duels.DuelsModes;
import me.dooger.duelsniffer.utils.ChatColor;
import me.dooger.duelsniffer.utils.Handler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import org.apache.commons.lang3.EnumUtils;

import java.util.Arrays;
import java.util.List;

public class GetStats extends DuelCommandBase {

    @Override
    public String getCommandName() {
        return "stat";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("ds", "dstat", "dstats");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0 && args.length < 3) {
            DuelsModes mode = DuelsModes.ALL;
            if (args.length == 2) {
                if (EnumUtils.isValidEnum(DuelsModes.class, args[1].toUpperCase())) {
                    mode = DuelsModes.valueOf(args[1].toUpperCase());
                } else {
                    sendMessage(ChatColor.RED + "Invalid Duels Mode");
                    return;
                }
            }

            final DuelsModes gmode = mode;

            Handler.asExecutor(()-> {
                Duels duels = new Duels(args[0], HypixelAPI.getUUID(args[0]), gmode);
                sendMessageWithBorder(duels.getFormattedStatsString());
            });
        } else {
            sendMessage(ChatColor.RED + "Incorrect Format! /stat <player> or /stat <player> <mode>");
        }
    }
}
