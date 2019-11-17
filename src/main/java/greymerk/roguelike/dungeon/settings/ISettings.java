package greymerk.roguelike.dungeon.settings;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.List;
import java.util.Random;
import java.util.Set;


public interface ISettings {

    boolean isValid(IWorldEditor editor, Coord pos);

    List<SettingIdentifier> getInherits();

    boolean isExclusive();

    LevelSettings getLevelSettings(int level);

    TowerSettings getTower();

    void processLoot(Random rand, TreasureManager treasure);

    int getNumLevels();

    Set<SettingsType> getOverrides();
}
