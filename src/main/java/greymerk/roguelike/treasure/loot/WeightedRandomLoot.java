package greymerk.roguelike.treasure.loot;


import com.google.gson.JsonObject;
import greymerk.roguelike.util.IWeighted;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import zhehe.advanceddungeons.util.FormatItem;
import zhehe.advanceddungeons.util.nbt.JsonToNBT;

import java.util.Random;
import java.util.logging.Level;
//import net.minecraft.block.Block;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.JsonToNBT;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.ResourceLocation;

public class WeightedRandomLoot implements Comparable<WeightedRandomLoot>, IWeighted<ItemStack> {


    private String name;
    private ItemStack item;
    private Material block;
    private int damage;
    private int min;
    private int max;
    private int enchLevel;
    private int weight;

    private NBTTagCompound nbt;

    public WeightedRandomLoot(Material block, int damage, int minStackSize, int maxStackSize, int weight) {
        this.name = (new FormatItem(block)).getUnlocalizedName();
        this.block = block;
        this.damage = damage;
        this.min = minStackSize;
        this.max = maxStackSize;
        this.weight = weight;
        this.enchLevel = 0;
    }

    public WeightedRandomLoot(Material item, int damage, int minStackSize, int maxStackSize, int weight, int ench) {

        this.name = (new FormatItem(item)).getUnlocalizedName();
        this.item = new ItemStack(item);
        this.damage = damage;
        this.min = minStackSize;
        this.max = maxStackSize;
        this.weight = weight;
        this.enchLevel = ench;
    }

    public WeightedRandomLoot(Material item, int damage, int weight) {
        this(item, damage, 1, 1, weight, 0);
    }

    public WeightedRandomLoot(Material item, int weight) {
        this(item, 0, 1, 1, weight, 0);
    }

    public WeightedRandomLoot(JsonObject json, int weight) throws Exception {

        this.name = json.get("name").getAsString();
        String[] array = name.split(":");
        Material material;
        try {
            material = Material.valueOf(array[1].toUpperCase());
        } catch (Exception ex) {
            material = Material.AIR;
            Bukkit.getLogger().log(Level.SEVERE, "Invalid item: " + name);
        }
        this.item = new ItemStack(material);
//		ResourceLocation location = new ResourceLocation(name);
//		this.item = (Item) Item.REGISTRY.getObject(location);
//		try{
//			this.item.getUnlocalizedName();
//		} catch (NullPointerException e){
//			throw new Exception("Invalid item: " + this.name);
//		}
        this.damage = json.has("meta") ? json.get("meta").getAsInt() : 0;
        this.weight = weight;
        this.enchLevel = json.has("ench") ? json.get("ench").getAsInt() : 0;

        if (json.has("min") && json.has("max")) {
            min = json.get("min").getAsInt();
            max = json.get("max").getAsInt();
        } else {
            min = 1;
            max = 1;
        }

        if (json.has("nbt")) this.nbt = JsonToNBT.getTagFromJson(json.get("nbt").getAsString());

    }

    private int getStackSize(Random rand) {
        if (max == 1) return 1;

        return rand.nextInt(max - min) + min;
    }


    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public ItemStack get(Random rand) {
        ItemStack item = null;
        if (this.item != null) item = this.item;
        if (this.block != null) item = new ItemStack(this.block, this.getStackSize(rand));
        try {
            if (this.enchLevel > 0 && this.enchLevel <= 30) Enchant.enchantItem(rand, item, this.enchLevel);
        } catch (NullPointerException e) {
            // ignore
        }
        if (this.nbt != null) {
            net.minecraft.server.v1_16_R3.ItemStack tmp = CraftItemStack.asNMSCopy(item);
            tmp.setTag(this.nbt);
            item = CraftItemStack.asBukkitCopy(tmp);
        }
        return item;
    }

    @Override
    public int compareTo(WeightedRandomLoot other) {
        if (this.weight > other.weight) return -1;
        if (this.weight < other.weight) return 1;

        return 0;
    }
}