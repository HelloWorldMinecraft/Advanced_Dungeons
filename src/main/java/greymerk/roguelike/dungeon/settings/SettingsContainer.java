package greymerk.roguelike.dungeon.settings;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import greymerk.roguelike.dungeon.settings.base.*;
import greymerk.roguelike.dungeon.settings.builtin.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import zhehe.advanceddungeons.AdvancedDungeons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;

public class SettingsContainer implements ISettingsContainer {

    public static final String DEFAULT_NAMESPACE = "default";
    public static final String BUILTIN_NAMESPACE = "builtin";
    public static final String configDirName = "plugins" + File.separator + "advanced_dungeons";
    private static final String lootfile = configDirName + File.separator + "custom_loot.json";
    private static SettingsLootRules loot;
    private Map<String, Map<String, DungeonSettings>> settingsByNamespace;

    public SettingsContainer() {
        this.settingsByNamespace = new HashMap<String, Map<String, DungeonSettings>>();

        this.put(new SettingsRooms());
        this.put(new SettingsSecrets());
        this.put(new SettingsSegments());
        this.put(new SettingsLayout());
        this.put(new SettingsTheme());

        loot = new SettingsLootRules();

        this.put(loot);
        this.put(new SettingsBase());

        this.put(new SettingsDesertTheme());
        this.put(new SettingsEndTheme());
        this.put(new SettingsForestTheme());
        this.put(new SettingsGrasslandTheme());
        this.put(new SettingsIceTheme());
        this.put(new SettingsJungleTheme());
        this.put(new SettingsMesaTheme());
        this.put(new SettingsMountainTheme());
        this.put(new SettingsNetherTheme());
        this.put(new SettingsOceanTheme());
        this.put(new SettingsSwampTheme());

        doLootRuleOverride();
    }

    public final void doLootRuleOverride() {
        {
            String txt = "guide_coexistence_loot.txt";
            String coexistence_loot = configDirName + File.separator + txt;
            File file = new File(coexistence_loot);
            if (!file.exists()) {
                try {
                    InputStream stream = AdvancedDungeons.instance.getResource(txt);
                    FileUtils.copyInputStreamToFile(stream, file);
                } catch (IOException e) {
                    Bukkit.getLogger().log(Level.SEVERE, "[Advanced Dungeons] Fail to create " + coexistence_loot);
                }
            }
        }

        File file = new File(lootfile);
        if (!file.exists()) {
            try {
                InputStream stream = AdvancedDungeons.instance.getResource("custom_loot.json");
                FileUtils.copyInputStreamToFile(stream, file);
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "[Advanced Dungeons] Fail to create " + lootfile);
            }
        } else {
            String content;
            try {
                content = Files.toString(file, Charsets.UTF_8);
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "[Advanced Dungeons] Fail to read " + lootfile);
                content = "";
            }
            if (!content.isEmpty()) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject root = (JsonObject) jParser.parse(content);
                    if (root.has("active") && root.get("active").getAsBoolean()) {
                        DungeonSettings setting = parseFile(content);

                        Bukkit.getLogger().log(Level.INFO, "[Advanced Dungeons] Using custom loottable");
                        if (root.has("coexistence") && root.get("coexistence").getAsBoolean()) {
                            Bukkit.getLogger().log(Level.INFO, "[Advanced Dungeons] Using Coexistence Mode for loottable");
                            loot.lootRules.getRules().addAll(setting.lootRules.getRules());
                        } else {
                            Bukkit.getLogger().log(Level.INFO, "[Advanced Dungeons] Buildin loottable is disabled");
                            loot.lootRules = setting.lootRules;
                        }
                    }
                } catch (Exception ex) {
                    Bukkit.getLogger().log(Level.SEVERE, "[Advanced Dungeons] Format error in " + lootfile);
                }
            }
        }
    }

    public void parseCustomSettings(Map<String, String> files) throws Exception {
        for (String name : files.keySet()) {
            DungeonSettings toAdd = null;
            try {
                toAdd = parseFile(files.get(name));
            } catch (Exception e) {
                throw new Exception("Error in: " + name + " : " + e.getMessage());
            }
            this.put(toAdd);
        }
    }

    private DungeonSettings parseFile(String content) throws Exception {

        JsonParser jParser = new JsonParser();
        JsonObject root = null;
        DungeonSettings toAdd = null;

        try {
            root = (JsonObject) jParser.parse(content);
        } catch (JsonSyntaxException e) {
            Throwable cause = e.getCause();
            throw new Exception(cause.getMessage());
        } catch (Exception e) {
            throw new Exception("An unknown error occurred while parsing json");
        }

        toAdd = new DungeonSettings(root);

        return toAdd;
    }

    public void put(DungeonSettings setting) {
        String namespace = setting.getNameSpace() != null ? setting.getNameSpace() : BUILTIN_NAMESPACE; //DEFAULT_NAMESPACE;
        String name = setting.getName();

        if (!settingsByNamespace.containsKey(namespace)) {
            settingsByNamespace.put(namespace, new HashMap<String, DungeonSettings>());
        }

        Map<String, DungeonSettings> settings = this.settingsByNamespace.get(namespace);
        settings.put(name, setting);
    }

    public Collection<DungeonSettings> getByNamespace(String namespace) {
        if (!this.settingsByNamespace.containsKey(namespace)) return new ArrayList<>();
        return this.settingsByNamespace.get(namespace).values();
    }

    @Override
    public Collection<DungeonSettings> getBuiltinSettings() {
        List<DungeonSettings> settings = new ArrayList<>();

        for (String namespace : settingsByNamespace.keySet()) {
            if (!namespace.equals(SettingsContainer.BUILTIN_NAMESPACE)) continue;
            settings.addAll(settingsByNamespace.get(namespace).values());
        }

        return settings;
    }

    @Override
    public Collection<DungeonSettings> getCustomSettings() {

        List<DungeonSettings> settings = new ArrayList<>();

        for (String namespace : settingsByNamespace.keySet()) {
            if (namespace.equals(SettingsContainer.BUILTIN_NAMESPACE)) continue;
            settings.addAll(settingsByNamespace.get(namespace).values());
        }

        return settings;
    }

    public DungeonSettings get(SettingIdentifier id) {
        if (!contains(id)) return null;
        Map<String, DungeonSettings> settings = settingsByNamespace.get(id.getNamespace());
        return settings.get(id.getName());
    }

    public boolean contains(SettingIdentifier id) {

        if (!settingsByNamespace.containsKey(id.getNamespace())) return false;

        Map<String, DungeonSettings> settings = settingsByNamespace.get(id.getNamespace());
        return settings.containsKey(id.getName());
    }

    @Override
    public String toString() {
        String strg = "";
        for (String namespace : settingsByNamespace.keySet()) {
            Map<String, DungeonSettings> settings = settingsByNamespace.get(namespace);

            for (DungeonSettings setting : settings.values()) {
                strg += setting.id.toString() + " ";
            }
        }

        return strg;
    }
}
