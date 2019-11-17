package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Coord;

import java.util.List;
import java.util.Random;

public interface IDungeon {

    int generate(ISettings setting, Coord pos);

    void spawnInChunk(Random rand, int chunkX, int chunkZ);

    Coord getPosition();

    Coord getTaskPosition();

    void setTaskPosition(Coord pos);

    List<IDungeonLevel> getLevels();

    List<ITreasureChest> getChests();

}
