package greymerk.roguelike.worldgen.shapes;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.List;
import java.util.Random;

public interface IShape extends Iterable<Coord> {

    void fill(IWorldEditor editor, Random rand, IBlockFactory block);

    void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid);

    List<Coord> get();

}
