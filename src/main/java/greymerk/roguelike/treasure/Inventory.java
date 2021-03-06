package greymerk.roguelike.treasure;

import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntityChest;

public class Inventory {
    List<Integer> shuffledSlots;
    private InventoryHolder chest;

    public Inventory(Random rand, InventoryHolder chest) {
        this.chest = chest;
        this.shuffledSlots = new ArrayList<>();
        for (int i = 0; i < this.getInventorySize(); ++i) {
            shuffledSlots.add(i);
        }

        Collections.shuffle(shuffledSlots, rand);
    }

    public boolean setRandomEmptySlot(ItemStack item) {
        int slot = this.getRandomEmptySlot();
        if (slot < 0) return false;
        return setInventorySlot(slot, item);
    }

    private int getRandomEmptySlot() {
        for (int slot : this.shuffledSlots) {
            if (isEmptySlot(slot)) return slot;
        }
        return -1;
    }

    public boolean isEmptySlot(int slot) {
        try {
            ItemStack item = chest.getInventory().getItem(slot);
            return item == null || item.getType() == Material.AIR;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean setInventorySlot(int slot, ItemStack item) {
        try {
            chest.getInventory().setItem(slot, item);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public int getInventorySize() {

        if (chest == null) {
            return 0;
        }

        try {
            return chest.getInventory().getSize();
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
