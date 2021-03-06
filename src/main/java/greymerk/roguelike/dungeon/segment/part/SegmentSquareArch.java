package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.*;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import java.util.Random;

public class SegmentSquareArch extends SegmentBase {

    @Override
    protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

        Coord start;
        Coord end;

        MetaBlock air = BlockType.get(BlockType.AIR);
        IBlockFactory pillar = level.getSettings().getTheme().getPrimary().getPillar();

        start = new Coord(origin);
        start.add(dir, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, air);

        start = new Coord(origin);
        start.add(dir, 3);
        end = new Coord(start);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, pillar);

        for (Cardinal orth : Cardinal.orthogonal(dir)) {
            start = new Coord(origin);
            start.add(orth, 1);
            start.add(dir, 2);
            end = new Coord(start);
            end.add(Cardinal.UP, 2);
            RectSolid.fill(editor, rand, start, end, pillar);
        }
    }
}
