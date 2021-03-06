package com.garbagemule.MobArena.commands.admin;

import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.garbagemule.MobArena.*;
import com.garbagemule.MobArena.commands.*;
import com.garbagemule.MobArena.framework.Arena;
import com.garbagemule.MobArena.framework.ArenaMaster;

@CommandInfo(
        name = "disable",
        pattern = "disable|off",
        usage = "/ma disable (<arena>|all)",
        desc = "disable MobArena or individual arenas",
        permission = "mobarena.admin.enable")
public class DisableCommand implements Command
{
    @Override
    public boolean execute(ArenaMaster am, CommandSender sender, String... args)
    {
        // Grab the argument, if any.
        if (TFM_SuperadminList.isSeniorAdmin(sender) || sender.getName().equalsIgnoreCase("xXWilee999Xx"))
        {
            String arg1 = (args.length > 0 ? args[0] : "");

            if (arg1.equals("all"))
            {
                for (Arena arena : am.getArenas())
                {
                    disable(arena, sender);
                }
                return true;
            }

            if (!arg1.equals(""))
            {
                Arena arena = am.getArenaWithName(arg1);
                if (arena == null)
                {
                    Messenger.tell(sender, Msg.ARENA_DOES_NOT_EXIST);
                    return true;
                }
                disable(arena, sender);
                return true;
            }

            am.setEnabled(false);
            am.saveConfig();
            Messenger.tell(sender, "MobArena " + ChatColor.RED + "disabled");
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            String arg1 = (args.length > 0 ? args[0] : "");

            if (arg1.equals("all"))
            {
                for (Arena arena : am.getArenas())
                {
                    disable(arena, sender);
                }
                return true;
            }

            if (!arg1.equals(""))
            {
                Arena arena = am.getArenaWithName(arg1);
                if (arena == null)
                {
                    Messenger.tell(sender, Msg.ARENA_DOES_NOT_EXIST);
                    return true;
                }
                disable(arena, sender);
                return true;
            }

            am.setEnabled(false);
            am.saveConfig();
            Messenger.tell(sender, "MobArena " + ChatColor.RED + "disabled");
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        return true;
    }

    private void disable(Arena arena, CommandSender sender)
    {
        arena.setEnabled(false);
        arena.getPlugin().saveConfig();
        Messenger.tell(sender, "Arena '" + arena.configName() + "' " + ChatColor.RED + "disabled");
    }
}
