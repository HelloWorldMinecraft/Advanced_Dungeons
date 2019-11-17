package greymerk.roguelike.dungeon.tasks;

import greymerk.roguelike.dungeon.DungeonTunnel;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.List;
import java.util.Random;

public class DungeonTaskTunnels implements IDungeonTask {

    @Override
    public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {
        List<IDungeonLevel> levels = dungeon.getLevels();

        // generate tunnels
        IDungeonLevel level = levels.get(index);
        {
            for (DungeonTunnel t : level.getLayout().getTunnels()) {
                t.construct(editor, rand, level.getSettings());
            }
        }
        return index == levels.size() - 1;
    }
}
