package greymerk.roguelike.citadel;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.Random;

public class Citadel {

    public static final int EDGE_LENGTH = 17;

    public static void generate(IWorldEditor editor, int x, int z) {
        Random rand = getRandom(editor, x, z);
        MinimumSpanningTree mst = new MinimumSpanningTree(rand, 7, EDGE_LENGTH);
        //mst.generate(world, rand, new MetaBlock(Blocks.glowstone), new Coord(x, 100, z));

        CityGrounds.generate(editor, rand, mst, Theme.getTheme(Theme.OAK), new Coord(x, 50, z));
    }


    public static Random getRandom(IWorldEditor editor, int x, int z) {
        return new Random(editor.getSeed() * x * z);
    }
}
