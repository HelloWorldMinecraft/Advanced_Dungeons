package greymerk.roguelike.worldgen.filter;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.Vine;
import greymerk.roguelike.worldgen.shapes.Shape;

import java.util.Random;

public class VineFilter implements IFilter {

    @Override
    public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
        for (Coord pos : box.getShape(Shape.RECTSOLID)) {
            if (rand.nextInt(10) == 0) Vine.set(editor, pos);
        }
    }
}
