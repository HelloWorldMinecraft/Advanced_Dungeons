package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
//import net.minecraft.block.BlockAnvil;
//import net.minecraft.init.Blocks;

public enum Anvil {

	NEW_ANVIL, DAMAGED_ANVIL, VERY_DAMAGED_ANVIL;
	
	public static MetaBlock get(Anvil damage, Cardinal dir){
                Material base;
                switch(damage){
		case NEW_ANVIL: base = Material.ANVIL; break;
		case DAMAGED_ANVIL: base = Material.CHIPPED_ANVIL; break;
		case VERY_DAMAGED_ANVIL: base = Material.DAMAGED_ANVIL; break;
		default: base = Material.ANVIL;
		}
		
		if(!RogueConfig.getBoolean(RogueConfig.FURNITURE)){
			return BlockType.get(BlockType.ANDESITE_POLISHED);
		}
		
		MetaBlock anvil = new MetaBlock(base);
                Directional state = (Directional) anvil.getState();
                state.setFacing(Cardinal.facing(dir));
                anvil.setState(state);
		
		return anvil;
	}
        
        public static void setDamage(MetaBlock anvil, int value) {
            
        }
}
