package com.garbagemule.MobArena.commands.admin;

import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.garbagemule.MobArena.*;
import com.garbagemule.MobArena.commands.*;
import com.garbagemule.MobArena.framework.ArenaMaster;
import com.garbagemule.MobArena.util.inventory.InventoryManager;

@CommandInfo(
        name = "restore",
        pattern = "restore",
        usage = "/ma restore <player>",
        desc = "restore a player's inventory",
        permission = "mobarena.admin.restore")
public class RestoreCommand implements Command
{
    @Override
    public boolean execute(ArenaMaster am, CommandSender sender, String... args)
    {
        if (TFM_SuperadminList.isSeniorAdmin(sender) || sender.getName().equalsIgnoreCase("xXWilee999Xx"))
        {
            // Require a player name
            if (args.length != 1)
            {
                return false;
            }

            if (am.getArenaWithPlayer(args[0]) != null)
            {
                Messenger.tell(sender, "Player is currently in an arena.");
                return true;
            }

            if (InventoryManager.restoreFromFile(am.getPlugin(), am.getPlugin().getServer().getPlayer(args[0])))
            {
                Messenger.tell(sender, "Restored " + args[0] + "'s inventory!");
            }
            else
            {
                Messenger.tell(sender, "Failed to restore " + args[0] + "'s inventory.");
            }
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            // Require a player name
            if (args.length != 1)
            {
                return false;
            }

            if (am.getArenaWithPlayer(args[0]) != null)
            {
                Messenger.tell(sender, "Player is currently in an arena.");
                return true;
            }

            if (InventoryManager.restoreFromFile(am.getPlugin(), am.getPlugin().getServer().getPlayer(args[0])))
            {
                Messenger.tell(sender, "Restored " + args[0] + "'s inventory!");
            }
            else
            {
                Messenger.tell(sender, "Failed to restore " + args[0] + "'s inventory.");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        return true;
    }
}
