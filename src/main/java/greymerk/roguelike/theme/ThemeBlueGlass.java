package greymerk.roguelike.theme;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeBlueGlass extends ThemeBase {

    public ThemeBlueGlass() {

        MetaBlock floor = ColorBlock.get(ColorBlock.GLASS, DyeColor.BLUE);
        MetaBlock walls = ColorBlock.get(ColorBlock.GLASS, DyeColor.LIGHT_BLUE);

        MetaStair stair = new MetaStair(StairType.SANDSTONE);
        MetaBlock pillar = BlockType.get(BlockType.PRISMARINE);


        this.primary = new BlockSet(floor, walls, stair, pillar);

        this.secondary = new BlockSet(
                floor,
                BlockType.get(BlockType.PRISMARINE_DARK),
                stair,
                pillar
        );
    }
}
