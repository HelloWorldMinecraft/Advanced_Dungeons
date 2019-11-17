package greymerk.roguelike.dungeon.tasks;

import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.Random;

public interface IDungeonTask {

    boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index);

}
