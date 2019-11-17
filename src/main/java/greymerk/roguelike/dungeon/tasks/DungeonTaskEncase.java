package greymerk.roguelike.dungeon.tasks;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;

import java.util.List;
import java.util.Random;

public class DungeonTaskEncase implements IDungeonTask {

    @Override
    public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {

        List<IDungeonLevel> levels = dungeon.getLevels();

        // encase
        if (RogueConfig.getBoolean(RogueConfig.ENCASE)) {
            IDungeonLevel level = levels.get(index);
            level.filter(editor, rand, Filter.get(Filter.ENCASE));
            return levels.size() == index + 1;
        }
        return true;
    }
}
