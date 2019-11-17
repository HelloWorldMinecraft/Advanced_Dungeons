package greymerk.roguelike.theme;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemePrismarine extends ThemeBase {

    public ThemePrismarine() {

        MetaBlock floor = BlockType.get(BlockType.PRISMARINE_DARK);
        MetaBlock walls = BlockType.get(BlockType.PRISMARINE);

        MetaStair stair = new MetaStair(StairType.SANDSTONE);
        MetaBlock pillar = BlockType.get(BlockType.OBSIDIAN);


        this.primary = new BlockSet(floor, walls, stair, pillar);

        this.secondary = new BlockSet(
                floor,
                BlockType.get(BlockType.PRISMITE),
                stair,
                pillar
        );
    }
}
