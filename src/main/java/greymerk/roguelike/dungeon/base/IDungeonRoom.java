package greymerk.roguelike.dungeon.base;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.Random;

public interface IDungeonRoom {

    boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin);

    int getSize();

    boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos);

}
