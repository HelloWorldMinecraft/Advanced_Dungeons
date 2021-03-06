package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.*;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import java.util.Random;

public class SegmentFireArch extends SegmentBase {


    @Override
    protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

        IStair stair = theme.getPrimary().getStair();
        IBlockFactory walls = theme.getPrimary().getWall();

        Coord start;
        Coord end;
        Coord cursor;

        Cardinal[] orths = Cardinal.orthogonal(dir);

        start = new Coord(origin);
        start.add(dir, 3);
        end = new Coord(start);
        start.add(orths[0]);
        end.add(orths[0]);
        end.add(Cardinal.UP, 2);
        end.add(dir);
        RectSolid.fill(editor, rand, start, end, walls);
        cursor = new Coord(origin);
        cursor.add(dir, 2);
        stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
        cursor.add(Cardinal.UP, 2);
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
        cursor.add(Cardinal.DOWN, 2);
        cursor.add(dir);
        BlockType.get(BlockType.NETHERRACK).set(editor, cursor);
        cursor.add(Cardinal.UP);
        BlockType.get(BlockType.FIRE).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        BlockType.get(BlockType.IRON_BAR).set(editor, cursor);

        for (Cardinal orth : orths) {

            cursor = new Coord(origin);
            cursor.add(dir);
            cursor.add(orth);
            cursor.add(Cardinal.UP, 2);
            stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);

        }
    }
}
