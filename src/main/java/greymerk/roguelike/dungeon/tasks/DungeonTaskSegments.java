package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonTunnel;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskSegments implements IDungeonTask{

	@Override
	public boolean execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings, int index) {
		
		List<IDungeonLevel> levels = dungeon.getLevels();
		
		// generate segments
		IDungeonLevel level = levels.get(index);
                {
			for(DungeonTunnel tunnel : level.getLayout().getTunnels()){
				tunnel.genSegments(editor, rand, level);
			}
		}
                return index == levels.size() - 1;
	}
}
