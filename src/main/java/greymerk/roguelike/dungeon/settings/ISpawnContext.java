package greymerk.roguelike.dungeon.settings;

import org.bukkit.block.Biome;
import zhehe.advanceddungeons.util.BiomeDictionary;

import java.util.List;

//import net.minecraft.util.ResourceLocation;
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary.Type;

public interface ISpawnContext {

    boolean biomeHasType(BiomeDictionary.Type type);

    Biome getBiome();

    boolean includesBiome(List<Biome> biomes);

    boolean includesBiomeType(List<BiomeDictionary.Type> biomeTypes);

    String getDimension();

}
