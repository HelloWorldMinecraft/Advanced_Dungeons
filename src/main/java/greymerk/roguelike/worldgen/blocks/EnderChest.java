package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

import java.util.Arrays;
//import net.minecraft.block.BlockEnderChest;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.EnumFacing;


public class EnderChest {
    public static void set(IWorldEditor editor, Cardinal dir, Coord pos) {

        BlockFace facing = Arrays.asList(Cardinal.directions).contains(dir)
                ? Cardinal.facing(Cardinal.reverse(dir))
                : Cardinal.facing(Cardinal.SOUTH);

        MetaBlock chest = new MetaBlock(Material.ENDER_CHEST);
        Directional state = (Directional) chest.getState();
        state.setFacing(facing);
        chest.setState(state);
        chest.set(editor, pos);
    }
}
