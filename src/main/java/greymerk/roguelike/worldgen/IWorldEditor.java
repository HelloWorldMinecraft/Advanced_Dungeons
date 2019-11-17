package greymerk.roguelike.worldgen;

import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import zhehe.advanceddungeons.util.DelayData;
import zhehe.advanceddungeons.util.DelayNode;

import java.util.List;
import java.util.Map;
import java.util.Random;
//import net.minecraft.block.Block;
//import net.minecraft.tileentity.TileEntity;

public interface IWorldEditor {

    String getWorldName();

    boolean setBlock(Coord pos, MetaBlock metaBlock, boolean fillAir, boolean replaceSolid);

    Block getBlock(Coord pos);

    MetaBlock getMetaBlock(Coord pos);

    Material getMaterial(Coord pos);

    boolean isAirBlock(Coord pos);

//	TileEntity getTileEntity(Coord pos);

    long getSeed();

//	Random getSeededRandom(int m, int n, int i);

    void fillDown(Random rand, Coord pos, IBlockFactory pillar);

    boolean canPlace(MetaBlock block, Coord pos, Cardinal dir);

    boolean validGroundBlock(Coord pos);

    void spiralStairStep(Random rand, Coord pos, IStair stair, IBlockFactory pillar);

    int getStat(Block block);

    Map<Material, Integer> getStats();

    TreasureManager getTreasure();

    void addChest(ITreasureChest chest);

    IPositionInfo getInfo(Coord pos);

    Biome getBiome(Coord pos);

    List<DelayNode> getDelayList();

    void resetDelayList();

    void setBlockDelay(Coord pos, Material material);

    World getWorld();

    List<DelayData> getDataList();

//        public void setDataDelay(Coord pos, BlockData data);

    void resetDataList();

//	Coord findNearestStructure(VanillaStructure type, Coord pos);

}
