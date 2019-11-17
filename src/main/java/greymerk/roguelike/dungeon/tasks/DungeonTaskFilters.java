package greymerk.roguelike.dungeon.tasks;

import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.List;
import java.util.Random;

public class DungeonTaskFilters implements IDungeonTask {

    @Override
    public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {

        List<IDungeonLevel> levels = dungeon.getLevels();

        IDungeonLevel level = levels.get(index);
        level.applyFilters(editor, rand);
        return index == levels.size() - 1;
    }
}
