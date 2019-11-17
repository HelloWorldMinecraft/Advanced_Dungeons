package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.*;
import greymerk.roguelike.dungeon.settings.base.SettingsBase;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.worldgen.filter.Filter;
import org.bukkit.Material;
import zhehe.advanceddungeons.util.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;
//import net.minecraft.init.Items;
//import net.minecraftforge.common.BiomeDictionary;

public class SettingsEndTheme extends DungeonSettings {

    public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "end");

    public SettingsEndTheme() {

        this.id = ID;
        this.inherit.add(SettingsBase.ID);

        this.criteria = new SpawnCriteria();
        List<BiomeDictionary.Type> biomes = new ArrayList<>();
        biomes.add(BiomeDictionary.Type.END);
        this.criteria.setBiomeTypes(biomes);

        this.towerSettings = new TowerSettings(Tower.RUIN, Theme.getTheme(Theme.ENDER));

        this.lootRules = new LootRuleManager();
        for (int i = 0; i < 5; ++i) {
            this.lootRules.add(null, new WeightedRandomLoot(Material.EMERALD, 0, 1, 1 + i, 1), i, false, 6);
            this.lootRules.add(null, new WeightedRandomLoot(Material.DIAMOND, 1), i, false, 3 + i * 3);
        }


        Theme[] themes = {Theme.SANDSTONE, Theme.ENDER, Theme.ENDER, Theme.ENDER, Theme.PURPUR};

        SegmentGenerator segments;
        for (int i = 0; i < 5; ++i) {
            LevelSettings level = new LevelSettings();
            if (i < 4) {
                level.setDifficulty(5);
                segments = new SegmentGenerator(Segment.MOSSYARCH);
                segments.add(Segment.SHELF, 2);
                segments.add(Segment.INSET, 2);
                segments.add(Segment.SKULL, 1);
                segments.add(Segment.CHEST, 1);
                segments.add(Segment.SPAWNER, 2);
                level.setSegments(segments);
                level.addFilter(Filter.ENCASE);
            }

            level.setTheme(Theme.getTheme(themes[i]));
            levels.put(i, level);
        }
    }
}