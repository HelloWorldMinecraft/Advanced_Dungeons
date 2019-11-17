package greymerk.roguelike.dungeon.settings;

import greymerk.roguelike.treasure.loot.LootRuleManager;

import java.util.HashMap;

public class SettingsBlank extends DungeonSettings {

    public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "blank");

    public SettingsBlank() {
        this.id = ID;
        levels = new HashMap<Integer, LevelSettings>();
        this.lootRules = new LootRuleManager();
    }
}
