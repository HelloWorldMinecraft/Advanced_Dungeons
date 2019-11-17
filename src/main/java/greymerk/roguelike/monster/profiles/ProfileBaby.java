package greymerk.roguelike.monster.profiles;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import org.bukkit.World;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ProfileBaby implements IMonsterProfile {

    @Override
    public void addEquipment(World world, Random rand, int level, IEntity mob) {
        mob.setChild(true);

        if (rand.nextBoolean()) {
            MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, level, mob);
        }

        ItemStack weapon = ItemTool.getRandom(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
        mob.setSlot(EquipmentSlot.HAND, weapon);
    }

}
