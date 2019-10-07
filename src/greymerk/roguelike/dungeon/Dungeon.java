package greymerk.roguelike.dungeon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

//import org.apache.commons.io.FilenameUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsRandom;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.dungeon.tasks.DungeonTaskRegistry;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import org.bukkit.Bukkit;
//import net.minecraft.block.material.Material;
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary.Type;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;
import zhehe.advanceddungeons.AdvancedDungeons;
import static zhehe.advanceddungeons.AdvancedDungeons.configDirName;

public class Dungeon implements IDungeon{
        public static int count = 0;
        public static boolean isSpawning = false;
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
        
        public static boolean init = false;
	
	private static final String SETTINGS_DIRECTORY = configDirName + "/settings";
	public static SettingsResolver settingsResolver;
	
	
	private Coord origin;
	private List<IDungeonLevel> levels;

	private IWorldEditor editor;
        
        private static class Node {
            int x, z;
            public Node(int x, int z) {
                this.x = x;
                this.z = z;
            }
            @Override
            public String toString() {
                return "["+ x + "," + z + "]";
            }
        }
        public final static Map<String, List<Node>> dict = new HashMap<>();
        private static int max_len = 8;
        private static double THRESHOLD = 40;
        	
	static{
		try{
			RogueConfig.reload(false);
			initResolver();
		} catch(Exception e) {
			// do nothing
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        Bukkit.getLogger().log(Level.SEVERE, sw.toString());
		}
	}
	
	public static void initResolver() throws Exception{
		File settingsDir = new File(SETTINGS_DIRECTORY);
		
		if(settingsDir.exists() && !settingsDir.isDirectory()){
			throw new Exception("Settings directory is a file");
		}
		
		if(!settingsDir.exists()){
			settingsDir.mkdir();
		}
		
		File[] settingsFiles = settingsDir.listFiles();
		Arrays.sort(settingsFiles);
		
		SettingsContainer settings = new SettingsContainer();
		settingsResolver = new SettingsResolver(settings);
		
		Map<String, String> files = new HashMap<>();
		
		for(File file : Arrays.asList(settingsFiles)){
			
			if(!FilenameUtils.getExtension(file.getName()).equals("json")) continue;
			
			try {
				String content = Files.toString(file, Charsets.UTF_8);
				files.put(file.getName(), content); 
			} catch (IOException e) {				
				throw new Exception("Error reading file : " + file.getName());
			}
		}

		settings.parseCustomSettings(files);			
	}
	
	public Dungeon(IWorldEditor editor){
		this.editor = editor;
		this.levels = new ArrayList<>();
	}
        
	public void forceGenerateNear(Random rand, int x, int z){
		if(Dungeon.settingsResolver == null) return;
		int attempts = 50;
		
		for(int i = 0;i < attempts;i++){
			Coord location = new Coord(x, 0 ,z);
			
			if(!validLocation(rand, location)) continue;
			ISettings setting;
			
			try{
				setting = Dungeon.settingsResolver.getSettings(editor, rand, location);
			} catch(Exception e){
                                StringWriter sw = new StringWriter();
                                PrintWriter pw = new PrintWriter(sw);
                                e.printStackTrace(pw);
                                Bukkit.getLogger().log(Level.SEVERE, sw.toString());
				return;
			}
                        if(setting == null) setting = new SettingsRandom(rand);
			 
                        synchronized(dict) {
                            List<Node> queue = dict.get(editor.getWorldName());
                            if(queue == null) {
                                queue = new ArrayList<>();
                                dict.put(editor.getWorldName(), queue);
                            }
                            for(Node n : queue) {
                                int dx = (x-4)/16 - n.x;
                                int dz = (z-4)/16 - n.z;
                                double distance = Math.sqrt(dx*dx+dz*dz);
                                if(distance < THRESHOLD) return;
                            }
                            
                            queue.add(new Node((x-4)/16, (z-4)/16));
                            if(queue.size() > max_len) queue.remove(0);
                            count++;
                        }
			int size = generate(setting, location);

                        AdvancedDungeons.logMessage("[Command] Place dungeon @" + editor.getWorldName() + " x=" + location.getX() + ", z=" + location.getZ());
			return;
		}
	}

        
	public void generateNear(Random rand, int x, int z){
		if(Dungeon.settingsResolver == null) return;
		int attempts = 50;
		
		for(int i = 0;i < attempts;i++){
//			Coord location = getNearbyCoord(rand, x, z, 40, 100);
                        Coord location = new Coord(x, 0 ,z);
			
			if(!validLocation(rand, location)) continue;
			ISettings setting;
			
			try{
				setting = Dungeon.settingsResolver.getSettings(editor, rand, location);	
			} catch(Exception e){
                                StringWriter sw = new StringWriter();
                                PrintWriter pw = new PrintWriter(sw);
                                e.printStackTrace(pw);
                                Bukkit.getLogger().log(Level.SEVERE, sw.toString());
				return;
			}
			 
			if(setting == null) return;
                        synchronized(dict) {
                            List<Node> queue = dict.get(editor.getWorldName());
                            if(queue == null) {
                                queue = new ArrayList<>();
                                dict.put(editor.getWorldName(), queue);
                            }
                            for(Node n : queue) {
                                int dx = (x-4)/16 - n.x;
                                int dz = (z-4)/16 - n.z;
                                double distance = Math.sqrt(dx*dx+dz*dz);
                                if(distance < THRESHOLD) return;
                            }
                            
                            queue.add(new Node((x-4)/16, (z-4)/16));
                            if(queue.size() > max_len) queue.remove(0);
                            count++;
                        }
			int size = generate(setting, location);

                        AdvancedDungeons.logMessage("Place dungeon @" + editor.getWorldName() + " x=" + location.getX() + ", z=" + location.getZ());
			return;
		}
	}
	
        @Override
	public int generate(ISettings settings, Coord pos){
		this.origin = new Coord(pos.getX(), Dungeon.TOPLEVEL, pos.getZ());
		return DungeonGenerator.generate(editor, this, settings, DungeonTaskRegistry.getTaskRegistry());	
	}
	
	public static boolean canSpawnInChunk(int chunkX, int chunkZ, IWorldEditor editor){
		
		if(!RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN)) return false;
                
		if(!isVillageChunk(editor, chunkX, chunkZ)) return false;
		
		double spawnChance = RogueConfig.getDouble(RogueConfig.SPAWNCHANCE);
		Random rand = new Random(Objects.hash(chunkX, chunkZ, 31));
		
                if(rand.nextFloat() < spawnChance) {
                    synchronized(dict) {
                        List<Node> queue = dict.get(editor.getWorldName());
                        if(queue != null) {
                            for(Node n : queue) {
                                int dx = chunkX - n.x;
                                int dz = chunkZ - n.z;
                                double distance = Math.sqrt(dx*dx+dz*dz);
                                if(distance < THRESHOLD) return false;
                            }
                        }
                    }
                    return true;
                }
                return false;
	}
	
	public static boolean isVillageChunk(IWorldEditor editor, int chunkX, int chunkZ){
            // TODO
            return true;
	}
	
        @Override
	public void spawnInChunk(Random rand, int chunkX, int chunkZ) {
		if(Dungeon.canSpawnInChunk(chunkX, chunkZ, editor)){
                    
			int x = chunkX * 16 + rand.nextInt(12);
			int z = chunkZ * 16 + rand.nextInt(12);
//                      int x = chunkX * 16 + 4;
//			int z = chunkZ * 16 + 4;

//			isSpawning = true;
			generateNear(rand, x, z);
//                        isSpawning = false;
		}
	}
        
        public void forceSpawnInChunk(Random rand, int chunkX, int chunkZ) {
		int x = chunkX * 16 + 4;
		int z = chunkZ * 16 + 4;
			
		generateNear(rand, x, z);
	}
	
	public static int getLevel(int y){
		
		if (y < 15)	return 4;
		if (y < 25) return 3;
		if (y < 35) return 2;
		if (y < 45) return 1;
		return 0;
	}
        
        public boolean validLocation(Random rand, Coord column) {
            return true;
        }
	
	public static Coord getNearbyCoord(Random rand, int x, int z, int min, int max){
		
		int distance = min + rand.nextInt(max - min);
		
		double angle = rand.nextDouble() * 2 * Math.PI;
		
		int xOffset = (int) (Math.cos(angle) * distance);
		int zOffset = (int) (Math.sin(angle) * distance);
		
		Coord nearby = new Coord(x + xOffset, 0, z + zOffset);		
		return nearby;
	}
	
	public static Random getRandom(IWorldEditor editor, Coord pos){
		return new Random(Objects.hash(editor.getSeed(), pos));
	}

	@Override
	public List<ITreasureChest> getChests() {
		return this.editor.getTreasure().getChests();
	}

	@Override
	public Coord getPosition(){
		return new Coord(this.origin);
	}
        
        private Coord backup;
        
        @Override
	public Coord getTaskPosition(){
            return backup;
	}
        
        @Override
        public void setTaskPosition(Coord pos) {
            backup = pos;
        }

	@Override
	public List<IDungeonLevel> getLevels(){
		return this.levels;
	}
}
