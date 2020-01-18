/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.advanceddungeons;

import greymerk.roguelike.DungeonGenerator;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.WorldEditor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import zhehe.advanceddungeons.util.I18n;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;

/**
 * @author Zhehe
 */
public class AdvancedDungeons extends JavaPlugin {
    public static final String configDirName = "plugins" + File.separator + "advanced_dungeons";
    private static final String logfile = configDirName + File.separator + "log.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String version = "0.97";
    public static String date = "10/07/2019";
    public static boolean enabled = false;
    public static WorldConfig wc;
    public static JavaPlugin instance;

    public static void logMessage(String message) {
        Bukkit.getScheduler().runTaskAsynchronously(AdvancedDungeons.instance, () -> {
            try (FileWriter writer = new FileWriter(logfile, true)) {
                writer.write(AdvancedDungeons.dateFormat.format(new Date()) + " " + message);
                writer.write("\n");
            } catch (IOException e) {
//                    Bukkit.getLogger().info("Failed to write to log file " + logfile);
            }
        });
    }

    @Override
    public void onEnable() {
        instance = this;
        I18n.init();

        wc = new WorldConfig();
        wc.init();
        enabled = true;
        getServer().getPluginManager().registerEvents(new DLDWorldListener(), this);
        Bukkit.getLogger().log(Level.INFO, "              _                               _ ");
        Bukkit.getLogger().log(Level.INFO, "     /\\      | |                             | |");
        Bukkit.getLogger().log(Level.INFO, "    /  \\   __| |_   ____ _ _ __   ___ ___  __| |");
        Bukkit.getLogger().log(Level.INFO, "   / /\\ \\ / _` \\ \\ / / _` | '_ \\ / __/ _ \\/ _` |");
        Bukkit.getLogger().log(Level.INFO, "  / ____ \\ (_| |\\ V / (_| | | | | (_|  __/ (_| |");
        Bukkit.getLogger().log(Level.INFO, " /_/    \\_\\__,_| \\_/ \\__,_|_| |_|\\___\\___|\\__,_|");
        Bukkit.getLogger().log(Level.INFO, "  _____                                         ");
        Bukkit.getLogger().log(Level.INFO, " |  __ \\                                        ");
        Bukkit.getLogger().log(Level.INFO, " | |  | |_   _ _ __   __ _  ___  ___  _ __  ___ ");
        Bukkit.getLogger().log(Level.INFO, " | |  | | | | | '_ \\ / _` |/ _ \\/ _ \\| '_ \\/ __|");
        Bukkit.getLogger().log(Level.INFO, " | |__| | |_| | | | | (_| |  __/ (_) | | | \\__ \\");
        Bukkit.getLogger().log(Level.INFO, " |_____/ \\__,_|_| |_|\\__, |\\___|\\___/|_| |_|___/");
        Bukkit.getLogger().log(Level.INFO, "                      __/ |                     ");
        Bukkit.getLogger().log(Level.INFO, "                     |___/                      ");

        RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN);
        Dungeon.init = true;

//        initAllThemes();
    }

    private boolean senderHasOPPermission(final CommandSender sender) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (!player.hasPermission("advanceddungeons.op")) {
                player.sendMessage("You don't have the permission required to use this plugin");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("advanceddungeons")) {
            switch (args.length) {
                default:  // /advanceddungeons help
                    sender.sendMessage("Advanced Dungeons Help");
                    sender.sendMessage("/advanceddungeons enter worldname");
                    sender.sendMessage("[OP] /advanceddungeons apply worldname");
                    sender.sendMessage("[OP] /advanceddungeons unapply worldname");
                    sender.sendMessage("[OP] /advanceddungeons reload");
                    break;
                case 1:
                    String op1 = args[0];
                    if (op1.equals("reload")) {
                        if (!senderHasOPPermission(sender)) return true;
                        sender.sendMessage("Debug INFO:");
                        sender.sendMessage(Dungeon.dict.toString());
//                        sender.sendMessage(Boolean.toString(DungeonGenerator.isSpawn));
                        wc.init();
                        RogueConfig.reload(true);
                        Dungeon.settingsResolver.getSettings().doLootRuleOverride();
                        sender.sendMessage("Done");
                        return true;
                    } else {
                        sender.sendMessage("Invalid options...");
                        return true;
                    }
                case 2: // /advanceddungeons options worldname
                    String option = args[0];
                    String worldName = args[1];
                    if (option.equals("enter")) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage("Player only command");
                            return true;
                        }
                        if (!wc.isDungeon(worldName)) {
                            sender.sendMessage(worldName + " is not a dungeon world");
                            return true;
                        }
                        Player player = (Player) sender;
                        World world = Bukkit.getWorld(worldName);
                        if (world == null) {
                            sender.sendMessage("Invalid world name");
                            return true;
                        }
                        player.teleport(world.getSpawnLocation());
                    } else if (option.equals("apply")) {
                        if (!senderHasOPPermission(sender)) return true;
                        wc.addWorld(worldName);
                        sender.sendMessage("Done");
                        return true;
                    } else if (option.equals("unapply")) {
                        if (!senderHasOPPermission(sender)) return true;
                        wc.removeWorld(worldName);
                        sender.sendMessage("Done");
                        return true;
                    } else {
                        sender.sendMessage("Invalid options...");
                        return true;
                    }
                    break;
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("advanceddungeons_place")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Player only command");
                return true;
            }
            if (!senderHasOPPermission(sender)) return true;

            Player player = (Player) sender;
            World world = player.getWorld();
            IWorldEditor editor = new WorldEditor(world);
            Dungeon dungeon = new Dungeon(editor);
            Location loc = player.getLocation();

            Random rand = new Random();
            boolean flag = true;
            try {
                if (Dungeon.settingsResolver.getSettings(editor, rand, new Coord(loc.getBlockX(), 0, loc.getBlockZ())) == null) {
                    flag = false;
                }
            } catch (Exception ex) {
                flag = false;
            }
            if (!flag)
                sender.sendMessage("No valid themes is available at this location, will use random dungeon theme");

            dungeon.forceGenerateNear(rand, loc.getBlockX(), loc.getBlockZ());
            sender.sendMessage("Done.");
            return true;
        }
        return false;
    }

    private class DLDWorldListener implements Listener {
        @EventHandler(priority = EventPriority.LOW)
        public void onWorldInit(WorldInitEvent event) {
            if (!enabled) return;
            String world_name = event.getWorld().getName();
            if (wc.isDungeon(world_name)) {
                Bukkit.getLogger().log(Level.INFO, "Add AdvancedDungeons Populator to world: " + world_name);
                event.getWorld().getPopulators().add(new DungeonGenerator());
            } else {
                Bukkit.getLogger().log(Level.INFO, "AdvancedDungeons Populator is not used in " + world_name);
            }
        }
    }
}
