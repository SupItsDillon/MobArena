package com.garbagemule.MobArena.WileeCommands;

import com.garbagemule.MobArena.MAUtils;

import me.StevenLawson.TotalFreedomMod.Commands.PlayerNotFoundException;
import me.StevenLawson.TotalFreedomMod.TFM_RollbackManager;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_WorldEditBridge;
import me.StevenLawson.TotalFreedomMod.TFM_Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Command_wileemanage extends MA_Command
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /wileemanage <power> [arg]");
        }

        else if (args[0].equalsIgnoreCase("help"))
        {
            sender.sendMessage(ChatColor.GREEN + "=====Wileemanage Help Page=====");
            sender.sendMessage(ChatColor.GREEN + "Please do not abuse any commands or over-use them. Thanks.");
            sender.sendMessage(ChatColor.RED + "/wileemanage obliviate <player> - Superadmin command - Obliviate a bad player. Just for the really bad ones.");
            sender.sendMessage(ChatColor.RED + "/wileemanage nope <player> - Superadmin command - Nope a bad player.");
            sender.sendMessage(ChatColor.RED + "/wileemanage ebroadcast <message...> - Superadmin command - Broadcast to the server Essentials style.");
            sender.sendMessage(ChatColor.RED + "/wileemanage ride <player> - Superadmin command - Ride any player.");
            sender.sendMessage(ChatColor.RED + "/wileemanage machat <player <message...> - Superadmin command - Take someones chat and embarrass them.");
            sender.sendMessage(ChatColor.RED + "/wileemanage strength <on|off> - Superadmin command - Toggle strength epic powaaazzz.");
            sender.sendMessage(ChatColor.RED + "/wileemanage slam <player> - Superadmin command - Slam someone into the ground!");
            sender.sendMessage(ChatColor.RED + "/wileemanage hug <player> - Superadmin command - Hug a player <3");
            sender.sendMessage(ChatColor.RED + "/wileemanage warn <player> - Superadmin command - Warn a player for permban.");
            sender.sendMessage(ChatColor.RED + "/wileemanage facepalm - Superadmin command - Facepalm. All I have to say.");
            sender.sendMessage(ChatColor.RED + "/wileemanage report [custommsg...] - Report a player for breaking a rule.");
            sender.sendMessage(ChatColor.GREEN + "Please do not abuse any commands or over-use them. Thanks.");
            sender.sendMessage(ChatColor.GREEN + "=====Wileemanage Help Page=====");
        }

        else if (args[0].equalsIgnoreCase("obliviate"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage obliviate <player>");
                    return true;
                }

                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

                MAUtils.adminAction(sender.getName(), "Casting complete holy obliviation over " + player.getName(), ChatColor.RED);
                MAUtils.bcastMsg(player.getName() + " will be completely obliviated using thy satanic holy powers!", ChatColor.RED);
                MAUtils.bcastMsg(player.getName() + " has been a VERY naughty, naughty boy.", ChatColor.RED);

                final String IP = player.getAddress().getAddress().getHostAddress().trim();

                // remove from whitelist
                player.setWhitelisted(false);

                // deop
                if (player.isOp())
                {
                    player.setOp(false);
                }

                // ban IP
                TFM_ServerInterface.banIP(IP, null, null, null);

                // ban name
                TFM_ServerInterface.banUsername(player.getName(), null, null, null);

                // set gamemode to survival
                player.setGameMode(GameMode.SURVIVAL);

                // clear inventory
                player.closeInventory();
                player.getInventory().clear();

                // ignite player
                player.setFireTicks(10000);

                // rollback + undo
                TFM_WorldEditBridge.getInstance().undo(player, 15);

                TFM_RollbackManager.rollback(player.getName());

                // generate explosion
                player.getWorld().createExplosion(player.getLocation(), 7F);

                // go up into the sky
                player.setVelocity(new Vector(0, 20, 0));

                // runnables
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // ask the player a question
                        MAUtils.bcastMsg("Hey, " + player.getName() + ", what's the difference between jelly and jam?", ChatColor.LIGHT_PURPLE);
                    }
                }.runTaskLater(plugin, 40L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // answer it
                        MAUtils.bcastMsg("I can't jelly my banhammer down your throat.", ChatColor.LIGHT_PURPLE);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 100L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());

                        // kill (if not done already)
                        player.setHealth(0.0);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // go up into the sky
                        player.setVelocity(new Vector(0, 20, 0));
                    }
                }.runTaskLater(plugin, 140L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());

                        // kill (if not done already)
                        player.setHealth(0.0);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // go up into the sky
                        player.setVelocity(new Vector(0, 20, 0));
                    }
                }.runTaskLater(plugin, 160L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // message
                        MAUtils.adminAction(sender.getName(), "Banning: " + player.getName() + ", IP: " + IP, ChatColor.RED);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // kick player
                        player.kickPlayer(ChatColor.RED + "FUCKOFF, and get your MOTHER FUCKING shit together!");
                    }
                }.runTaskLater(plugin, 190L);

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("nope"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage nope <player>");
                    return true;
                }

                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

                final String IP = player.getAddress().getAddress().getHostAddress().trim();
                MAUtils.adminAction(sender.getName(), "Starting a huge nope fest over " + player.getName(), ChatColor.RED);

                // go up into the sky
                player.setVelocity(new org.bukkit.util.Vector(0, 3000, 0));

                // blow up
                player.getWorld().createExplosion(player.getLocation(), 4F);

                // strike lightning
                player.getWorld().strikeLightning(player.getLocation());

                // ban IP
                TFM_ServerInterface.banIP(IP, null, null, null);

                // ban name
                TFM_ServerInterface.banUsername(player.getName(), null, null, null);

                // rollback + undo
                TFM_WorldEditBridge.getInstance().undo(player, 15);

                TFM_RollbackManager.rollback(player.getName());

                // runnables
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // go up into the sky
                        player.setVelocity(new org.bukkit.util.Vector(0, 800, 0));

                        // blow up
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 50L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // go up into the sky
                        player.setVelocity(new org.bukkit.util.Vector(0, 800, 0));

                        // blow up
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 90L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // message
                        MAUtils.adminAction(sender.getName(), "Banning: " + player.getName() + ", IP: " + IP, ChatColor.RED);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // kick player
                        player.kickPlayer(ChatColor.RED + "NOPE!\nAppeal at totalfreedom.boards.net\nAnd make sure you follow the rules at totalfreedom.me!");
                    }
                }.runTaskLater(plugin, 120L);

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("ebroadcast"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage ebroadcast <message...>");
                    return true;
                }

                String message = "";
                for (int i = 1; i < args.length; i++)
                {
                    if (i > 1)
                    {
                        message += " ";
                    }
                    message += args[i];
                }

                MAUtils.bcastMsg(ChatColor.RED + "[" + ChatColor.GREEN + "Broadcast" + ChatColor.RED + "] " + ChatColor.AQUA + message);
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }
        else if (args[0].equalsIgnoreCase("ride"))
        {
            if (!TFM_SuperadminList.isUserSuperadmin(sender) || sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command, or you are runnning this command from the console.");
            }
            else
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage ride <player>");
                    return true;
                }

                Player player = Bukkit.getPlayer(args[1]);
                if (player.isOnline())
                {
                    if (!player.getName().equalsIgnoreCase(sender.getName()))
                    {
                        player.setPassenger(sender_p);
                        sender.sendMessage(ChatColor.GREEN + "You are now riding: " + player.getName());
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.RED + "You cannot ride yourself.");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "That player is not online.");
                }
            }
        }

        else if (args[0].equalsIgnoreCase("machat"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage machat <player> <message...>");
                    return true;
                }

                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

                String message = "";
                for (int i = 2; i < args.length; i++)
                {
                    if (i > 2)
                    {
                        message += " ";
                    }
                    message += args[i];
                }

                if (message.startsWith("/"))
                {
                    sender.sendMessage(ChatColor.RED + "You cannot start with a command, please use /gcmd for commands.");
                }
                else
                {
                    player.chat(message);
                }

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("strength"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage strength <on|off>");
                    return true;
                }
                else if (args[1].equalsIgnoreCase("on"))
                {
                    if (!MAUtils.strengthEnabled.contains(sender.getName()))
                    {
                        MAUtils.strengthEnabled.add(sender.getName());
                    }
                    sender.sendMessage(ChatColor.GREEN + "Strength has been enabled.");
                    return true;
                }
                else if (args[1].equalsIgnoreCase("off"))
                {
                    if (MAUtils.strengthEnabled.contains(sender.getName()))
                    {
                        MAUtils.strengthEnabled.remove(sender.getName());
                    }
                    sender.sendMessage(ChatColor.RED + "Strength has been disabled.");
                    return true;
                }

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("slam"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage slam <player>");
                    return true;
                }

                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

                MAUtils.adminAction(sender.getName(), "Slamming down: " + player.getName(), ChatColor.RED);

                player.setOp(false);
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().clear();

                Location loc = player.getLocation();
                loc.setY(loc.getY() - 10);
                player.teleport(loc);
                player.setHealth(0.0);
                player.setVelocity(new Vector(0, -100, 0));
                player.setVelocity(new Vector(0, 1000, 0));
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("hug"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage hug <player>");
                    return true;
                }

                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

                MAUtils.adminAction(sender.getName(), "Giving " + player.getName() + " a hug. <33333", ChatColor.LIGHT_PURPLE);
                Location otherloc = player.getLocation();
                sender_p.teleport(otherloc);
                TFM_Util.generateHollowCube(otherloc, 2, Material.GLASS);

                player.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + " gave you a hug. <333333");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "You have hugged " + player.getName() + ". <33333");
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("warn"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage warn <player>");
                    return true;
                }

                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

                MAUtils.adminAction(sender.getName(), "Warning " + player.getName() + " of permban", ChatColor.DARK_RED);

                player.setOp(false);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);

                player.sendMessage(ChatColor.DARK_RED + player.getName() + ", you are at high risk of being permanently banned (name and IP) from the Total Freedom server. Please immediately review all rules listed at www.totalfreedom.me and comply with them.");
                player.setVelocity(new Vector(0, 1000, 0));
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("facepalm"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                MAUtils.bcastMsg(sender.getName() + " really needs to have a facepalm + rage moment here...", ChatColor.RED);

                sender_p.chat("Oh my gosh... just omg... OMG!!!! FACEPALM!!!!!");
                sender_p.chat("im gonna go to bed. im getting MAD!!!");

                sender_p.setGameMode(GameMode.SURVIVAL);
                sender_p.getInventory().clear();
                sender_p.setHealth(0.0);

                sender_p.kickPlayer(ChatColor.RED + "FACEPALM RAGGGEQUIIIUTTTTTT!!!!!");
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("report"))
        {
            if (args.length == 1)
            {
                MAUtils.adminbcastMsg(sender.getName() + " is getting griefed!", ChatColor.RED);
                sender.sendMessage(ChatColor.GREEN + "The administration team has been notified that you are getting griefed. :)");
                return true;
            }

            String message = "";
            for (int i = 1; i < args.length; i++)
            {
                if (i > 1)
                {
                    message += " ";
                }
                message += args[i];
            }

            MAUtils.adminbcastMsg(String.format("[GrieferReport:%s] %s", sender.getName(), message), ChatColor.RED);
            sender.sendMessage(ChatColor.GREEN + "Your message has been sent to the administration team. :)");
            return true;
        }

        else
        {
            sender.sendMessage(ChatColor.RED + "Usage: /wileemanage <power> [arg]");
        }

        return true;
    }
}
