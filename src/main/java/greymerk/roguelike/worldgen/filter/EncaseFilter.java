package greymerk.roguelike.worldgen.filter;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.shapes.Shape;

import java.util.Random;

public class EncaseFilter implements IFilter {

    @Override
    public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
        box.getShape(Shape.RECTSOLID).fill(editor, rand, theme.getPrimary().getWall());
    }
}
