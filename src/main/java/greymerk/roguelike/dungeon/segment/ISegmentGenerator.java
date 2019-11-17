package greymerk.roguelike.dungeon.segment;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.List;
import java.util.Random;

public interface ISegmentGenerator {

    List<ISegment> genSegment(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos);

}
