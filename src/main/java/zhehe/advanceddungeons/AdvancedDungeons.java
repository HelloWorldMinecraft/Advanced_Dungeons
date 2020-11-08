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

    public void onEnable() {
        instance = this;
        I18n.init();

        wc = new WorldConfig();
        wc.init();
        enabled = true;
        getServer().getPluginManager().registerEvents(new DLDWorldListener(), this);

        RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN);
        Dungeon.init = true;

//        initAllThemes();
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
