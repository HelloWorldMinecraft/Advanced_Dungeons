package greymerk.roguelike.monster.profiles;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import org.bukkit.World;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ProfilePigman implements IMonsterProfile {

    @Override
    public void addEquipment(World world, Random rand, int level, IEntity mob) {
        mob.setMobClass(MobType.PIGZOMBIE, true);
        ItemStack weapon = ItemWeapon.getSword(rand, level, true);
        mob.setSlot(EquipmentSlot.HAND, weapon);
        mob.setSlot(EquipmentSlot.OFF_HAND, Shield.get(rand));
        MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, level, mob);
    }

}
