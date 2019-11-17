/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.advanceddungeons.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import static zhehe.advanceddungeons.AdvancedDungeons.configDirName;

/**
 * @author Zhehe
 */
public class I18n {
    public static final transient String configFileName = "lang.json";
    public static I18n instance = new I18n();
    public SpecialItem Eldritch_Blade_of_Plundering = new SpecialItem("Eldritch Blade of Plundering", "The loot taker");
    public SpecialItem Eldritch_Blade_of_the_Inferno = new SpecialItem("Eldritch Blade of the Inferno", "From the fiery depths");
    public SpecialItem Eldritch_Blade = new SpecialItem("Eldritch Blade", "Rune Etched");
    public SpecialItem Tempered_Blade = new SpecialItem("Tempered Blade", "Highly Durable");
    public SpecialItem Yew_Longbow = new SpecialItem("Yew Longbow", "Superior craftsmanship");
    public SpecialItem Laminated_Bow = new SpecialItem("Laminated Bow", "Highly polished");
    public SpecialItem Recurve_Bow = new SpecialItem("Recurve Bow", "Beautifully crafted");
    public SpecialItem Eldritch_Bow = new SpecialItem("Eldritch Bow", "Warm to the touch");
    public ArmourPrefix armour_prefix = new ArmourPrefix();
    public String Soul_Spade = "Soul Spade";
    public String Grave_Spade = "Grave Spade";
    public String Crystal_Head_Axe = "Crystal Head Axe";
    public String Woodland_Hatchet = "Woodland Hatchet";
    public String Crystal_Pick_of_Precision = "Crystal Pick of Precision";
    public String Crystal_Pick_of_Prospecting = "Crystal Pick of Prospecting";
    public String Crystal_Pick = "Crystal Pick";
    public String Case_Hardened_Pick_of_Precision = "Case Hardened Pick of Precision";
    public String Case_Hardened_Pick_of_Prospecting = "Case Hardened Pick of Prospecting";
    public String Case_Hardened_Pick = "Case Hardened Pick";
    public String Bonnet = "Bonnet";
    public String Coif = "Coif";
    public String Sallet = "Sallet";
    public String Helm = "Helm";
    public String Of_Diving = "of Diving";
    public String Of_Deflection = "of Deflection";
    public String Of_Defense = "of Defense";
    public String Shoes = "Shoes";
    public String Greaves = "Greaves";
    public String Sabatons = "Sabatons";
    public String Boots = "Boots";
    public String Of_Warding = "of Warding";
    public String Of_Lightness = "of Lightness";
    public String Pantaloons = "Pantaloons";
    public String Chausses = "Chausses";
    public String Leg_plates = "Leg-plates";
    public String Leggings = "Leggings";
    public String Of_Integrity = "of Integrity";
    public String Tunic = "Tunic";
    public String Hauberk = "Hauberk";
    public String Cuirass = "Cuirass";
    public String Plate = "Plate";
    public String Of_Flamewarding = "of Flamewarding";

    private I18n() {

    }

    public static void init() {
        File directory = new File(configDirName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String file_path = configDirName + File.separator + configFileName;
        File file = new File(file_path);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file_path), StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                I18n lang = (new Gson()).fromJson(sb.toString(), I18n.class);
                instance = lang;
            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                Bukkit.getLogger().log(Level.SEVERE, sw.toString());
            }
        }
        save();
    }

    public static void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(instance);
        String file_path = configDirName + File.separator + configFileName;
        File file = new File(file_path);
        try (OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            oStreamWriter.append(json);
            oStreamWriter.close();
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while saving lang file.");
        }
    }

    public static class ArmourPrefix {
        public String Surplus = "Surplus";
        public String Riveted = "Riveted";
        public String Gothic = "Gothic";
        public String Jewelled = "Jewelled";
        public String Crystal = "Crystal";
        public String Strange = "Strange";
    }

    public static class SpecialItem {
        public String Name;
        public String Lore;

        public SpecialItem(String Name, String Lore) {
            this.Name = Name;
            this.Lore = Lore;
        }
    }
}
