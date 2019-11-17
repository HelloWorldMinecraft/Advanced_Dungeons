package greymerk.roguelike.treasure.loot;

import greymerk.roguelike.util.IWeighted;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.item.ItemStack;

public interface ILoot {
	
	IWeighted<ItemStack> get(Loot type, int level);
	
}
