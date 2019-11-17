package greymerk.roguelike.monster;

//import net.minecraft.inventory.EntityEquipmentSlot;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.item.ItemStack;

public interface IEntity {

	void setSlot(EquipmentSlot slot, ItemStack item);
	
	void setMobClass(MobType type, boolean clear);
	
	void setChild(boolean child);
	
	boolean instance(Class<?> type);
	
	void setName(String name);
	
}
