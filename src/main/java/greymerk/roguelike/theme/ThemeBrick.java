package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.*;

public class ThemeBrick extends ThemeBase {

    public ThemeBrick() {

        MetaBlock walls = BlockType.get(BlockType.BRICK);

        MetaStair stair = new MetaStair(StairType.BRICK);
        MetaBlock pillar = Log.getLog(Wood.SPRUCE);

        this.primary = new BlockSet(walls, stair, walls);

        MetaBlock segmentWall = Wood.get(Wood.SPRUCE, WoodBlock.PLANK);

        this.secondary = new BlockSet(segmentWall, stair, pillar);

    }
}
