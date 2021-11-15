package me.combathax.worlds;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            return false;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("world")) {
            if (args.length < 2) {
                if (args[0].equalsIgnoreCase("load")) {
                    for (File f : p.getServer().getWorldContainer().listFiles()) {
                        if (f.isDirectory()) {
                            for (String s : f.list()) {
                                if (s.equals("level.dat")) {
                                    p.getServer().createWorld(new WorldCreator(f.getName()));
                                }
                            }
                        }
                    }
                    p.sendMessage(ChatColor.AQUA + "Done!");
                    return true;
                }
                p.sendMessage("§cUsage: /world <create/go/load> <worldname>");
                return true;
            }
            if (args[0].equalsIgnoreCase("create")) {
                String n = "Worlds." + args[1];
                switch (args.length) {
                    case 2:
                        WorldCreator worldCreator = new WorldCreator(args[1]);
                        worldCreator.createWorld();
                        p.sendMessage(ChatColor.AQUA + "Done!");
                        break;
                    case 3:
                        try {
                            WorldCreator wc = new WorldCreator(args[1]);
                            wc.seed(Long.parseLong(args[2]));
                            wc.createWorld();
                            p.sendMessage(ChatColor.AQUA + "Done!");
                        } catch (NumberFormatException e) {
                            p.sendMessage("§cInvalid Seed");
                        }
                        break;
                    case 4:
                        try {
                            WorldCreator wc2 = new WorldCreator(args[1]);
                            wc2.seed(Long.parseLong(args[2]));
                            if (args[3].equalsIgnoreCase("void")) {
                                wc2.generator(new VoidWorld());
                                World world = wc2.createWorld();
                                world.getBlockAt(world.getSpawnLocation()).setType(Material.GRASS_BLOCK);
                                p.sendMessage(ChatColor.AQUA + "Done!");
                                return true;
                            }
                            else if (args[3].equalsIgnoreCase("flat")){
                                wc2.type(WorldType.FLAT);
                                wc2.createWorld();
                                p.sendMessage(ChatColor.AQUA + "Done!");
                                return true;
                            }
                            wc2.environment(World.Environment.valueOf(args[3].toUpperCase()));
                            wc2.createWorld();
                            p.sendMessage(ChatColor.AQUA + "Done!");
                        } catch (Exception e) {
                            p.sendMessage("§cSomething went wrong :/");
                            return true;
                        }
                }
            } else if (args[0].equalsIgnoreCase("go")) {
                if (p.getServer().getWorld(args[1]) != null) {
                    World w = p.getServer().getWorld(args[1]);
                    p.teleport(w.getSpawnLocation());
                    p.sendMessage(ChatColor.AQUA + "Done!");
                    return true;
                }
                p.sendMessage(ChatColor.RED + "Invalid World Name");
                return true;
            } else if (args[0].equalsIgnoreCase("delete")) {
                World w = p.getServer().getWorld(args[1]);
                if (w != null) {
                    p.getServer().unloadWorld(w, false);
                    w.getWorldFolder().delete();
                    p.sendMessage(ChatColor.AQUA + "Done!");
                    return true;
                }
                p.sendMessage("§cInvalid World Name");
            }
        }
        return true;
    }
}
