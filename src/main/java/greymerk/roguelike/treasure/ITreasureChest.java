package greymerk.roguelike.treasure;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;

import java.util.Random;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;

public interface ITreasureChest {

    ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException;

    boolean setSlot(int slot, ItemStack item);

    boolean setRandomEmptySlot(ItemStack item);

    void setLootTable(LootTable table);

    boolean isEmptySlot(int slot);

    Treasure getType();

    int getSize();

    int getLevel();

}