package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.util.IWeighted;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

//import net.minecraft.item.ItemStack;

public abstract class ItemBase implements IWeighted<ItemStack> {

    int level;
    private int weight;

    public ItemBase(int weight) {
        this.weight = weight;
        this.level = 0;
    }

    public ItemBase(int weight, int level) {
        this.weight = weight;
        this.level = level;
    }

    public abstract ItemStack getLootItem(Random rand, int level);

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public ItemStack get(Random rand) {
        return getLootItem(rand, level);
    }
}
