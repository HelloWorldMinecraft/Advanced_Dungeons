package greymerk.roguelike.monster;

import org.bukkit.World;

import java.util.Random;

//import net.minecraft.world.World;

public interface IMonsterProfile {

    void addEquipment(World world, Random rand, int level, IEntity mob);

}
