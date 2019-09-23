/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.advanceddungeons.util.nms;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zhehe
 */
public class NmsHandler {
    private static boolean enable = false;
    public static Class<?> NBTTagCompound;
    public static Class<?> NBTTagIntArray;
    public static Class<?> NBTTagList;
    public static Class<?> CraftItemStack;
    public static Class<?> NBTTagString;
    public static Class<?> NBTTagByte, NBTTagByteArray, NBTTagDouble, NBTTagFloat, NBTTagInt, NBTTagLong, NBTTagShort;
    public static Class<?> MinecraftItemStack;
    public static Class<?> NBTBase;
    public static Constructor NBTTagCompoundConstructor;
    public static Constructor NBTTagListConstructor;
    public static Constructor NBTTagIntArrayConstructor;
    public static Class<?> TileEntity;
    public static Class<?> BlockPosition;
    public static Constructor BlockPositionConstructor;
    public static Class<?> CraftWorld;
    public static Class<?> WorldServer;
    
    public static Constructor NBTTagStringConstructor, NBTTagByteConstructor, NBTTagByteArrayConstructor,
            NBTTagDoubleConstructor, NBTTagFloatConstructor, NBTTagIntConstructor, NBTTagLongConstructor,
            NBTTagShortConstructor;
    
    static {
        try {
            WorldServer = getMinecraftNMSClass("WorldServer");
            
            TileEntity = getMinecraftNMSClass("TileEntity");
            BlockPosition = getMinecraftNMSClass("BlockPosition");
            BlockPositionConstructor = getMinecraftNMSClass("BlockPosition").getConstructor(int.class, int.class, int.class);
            CraftWorld = getCraftBukkitNMSClass("CraftWorld");
            
            NBTBase = getMinecraftNMSClass("NBTBase");
            NBTTagCompound = getMinecraftNMSClass("NBTTagCompound");
            NBTTagIntArray = getMinecraftNMSClass("NBTTagIntArray");
            NBTTagList = getMinecraftNMSClass("NBTTagList");
            CraftItemStack = getCraftBukkitNMSClass("inventory.CraftItemStack");
            NBTTagString = getMinecraftNMSClass("NBTTagString");
            NBTTagByte = getMinecraftNMSClass("NBTTagByte");
            NBTTagByteArray = getMinecraftNMSClass("NBTTagByteArray");
            NBTTagDouble = getMinecraftNMSClass("NBTTagDouble");
            NBTTagFloat = getMinecraftNMSClass("NBTTagFloat");
            NBTTagInt = getMinecraftNMSClass("NBTTagInt");
            NBTTagLong = getMinecraftNMSClass("NBTTagLong");
            NBTTagShort = getMinecraftNMSClass("NBTTagShort");
            MinecraftItemStack = getMinecraftNMSClass("ItemStack");
            
            NBTTagCompoundConstructor = getMinecraftNMSClass("NBTTagCompound").getConstructor();
            NBTTagListConstructor = getMinecraftNMSClass("NBTTagList").getConstructor();
            NBTTagIntArrayConstructor = getMinecraftNMSClass("NBTTagIntArray").getConstructor(int[].class);
            NBTTagStringConstructor = getMinecraftNMSClass("NBTTagString").getConstructor(String.class);
            NBTTagByteConstructor = getMinecraftNMSClass("NBTTagByte").getConstructor(byte.class);
            NBTTagByteArrayConstructor = getMinecraftNMSClass("NBTTagByteArray").getConstructor(byte[].class);
            NBTTagDoubleConstructor = getMinecraftNMSClass("NBTTagDouble").getConstructor(double.class);
            NBTTagFloatConstructor = getMinecraftNMSClass("NBTTagFloat").getConstructor(float.class);
            NBTTagIntConstructor = getMinecraftNMSClass("NBTTagInt").getConstructor(int.class);
            NBTTagLongConstructor = getMinecraftNMSClass("NBTTagLong").getConstructor(long.class);
            NBTTagShortConstructor = getMinecraftNMSClass("NBTTagShort").getConstructor(short.class);
            
            enable = true;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            Bukkit.getLogger().log(Level.SEVERE, sw.toString());
            sendErrorMessage(ex);
            enable = false;
        }
    }
    
    public static void TileEntityLoad(Object te, Object nbt) {
        if(te == null || nbt == null || !enable) return;
        try {
            TileEntity.getMethod("load", NBTTagCompound).invoke(te, nbt);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
        }
    }
    
    public static Object WorldServerGetTileEntity(Object ws, Object pos) {
        if(ws == null || pos == null || !enable) return null;
        try {
            return WorldServer.getMethod("getTileEntity", BlockPosition).invoke(ws, pos);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object CraftWorldGetHandle(Object cw) {
        if(cw == null || !enable) return null;
        try {
            return CraftWorld.getMethod("getHandle").invoke(CraftWorld.cast(cw));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            ex.printStackTrace(pw);
            enable = false;
            return null;
        }
    }
    
    public static Object createBlockPosition(int x, int y, int z) {
        if(!enable) return null;
        try {
            return BlockPositionConstructor.newInstance(x, y, z);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundClone(Object nbt) {
        if(nbt == null || !enable) return null;
        try {
            return NBTTagList.getMethod("clone").invoke(nbt);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static void NBTTagListAdd(Object nbt, Object element) {
        if(nbt == null || element == null || !enable) return;
        try {
            NBTTagList.getMethod("add", int.class, NBTBase).invoke(nbt, 0, element);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            ex.printStackTrace(pw);
//            Bukkit.getLogger().log(Level.SEVERE, sw.toString());
        }
    }
    
    public static Object createNBTTagShort(short s) {
        if(!enable) return null;
        try {
            return NBTTagShortConstructor.newInstance(s);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagLong(long l) {
        if(!enable) return null;
        try {
            return NBTTagLongConstructor.newInstance(l);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagInt(int i) {
        if(!enable) return null;
        try {
            return NBTTagIntConstructor.newInstance(i);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagFloat(float f) {
        if(!enable) return null;
        try {
            return NBTTagFloatConstructor.newInstance(f);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagDouble(double d) {
        if(!enable) return null;
        try {
            return NBTTagDoubleConstructor.newInstance(d);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagByteArray(byte[] b) {
        if(!enable) return null;
        try {
            return NBTTagByteArrayConstructor.newInstance((Object) b);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagByte(byte b) {
        if(!enable) return null;
        try {
            return NBTTagByteConstructor.newInstance(b);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagString(String s) {
        if(!enable) return null;
        try {
            return NBTTagStringConstructor.newInstance(s);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static void sendErrorMessage(Exception ex) {
        Bukkit.getLogger().log(Level.SEVERE, "[AdvancedDungeons] Fail to handle NMS... Please check if there's a new version");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        Bukkit.getLogger().log(Level.SEVERE, sw.toString());
    }
    
    public static Class<?> getMinecraftNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }
    public static Class<?> getCraftBukkitNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "org.bukkit.craftbukkit." + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }
    
    public static Object ItemStackGetTag(ItemStack item) {
        if(!enable) return null;
        try {
            Object craft_item = CraftItemStack.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
            return MinecraftItemStack.getMethod("getTag").invoke(craft_item);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static boolean NBTTagCompoundHasKey(Object nbt, String key) {
        if(!enable) return false;
        try {
            Boolean res = (Boolean) NBTTagCompound.getMethod("hasKey", String.class).invoke(nbt, key);
            return !Boolean.FALSE.equals(res);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return false;
        }
    }
    
    public static Object NBTTagCompoundGetCompound(Object nbt, String key) {
        if(!enable) return null;
        try {
            return NBTTagCompound.getMethod("getCompound", String.class).invoke(nbt, key);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static ItemStack setItemStackNBT(ItemStack item, Object obj) {
        if(!enable) return item;
        try {
            Object craft_item = CraftItemStack.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
            MinecraftItemStack.getMethod("setTag", NBTTagCompound).invoke(craft_item, obj);
            ItemStack res = (ItemStack) CraftItemStack.getMethod("asBukkitCopy", MinecraftItemStack).invoke(null, craft_item);
            return res;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return item;
        }
    }
    
    public static Object createNBTTagIntArray(int[] colorArr) {
        if(!enable) return null;
        try {
            return NBTTagIntArrayConstructor.newInstance((Object)colorArr);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagList() {
        if(!enable) return null;
        try {
            return NBTTagListConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object createNBTTagCompound() {
        if(!enable) return null;
        try {
            return NBTTagCompoundConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetByte(Object nbt, String key, byte b) {
        if(nbt == null || !enable) return null;
        try {
            NBTTagCompound.getMethod("setByte", String.class, byte.class).invoke(nbt, key, b);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetShort(Object nbt, String key, short s) {
        if(nbt == null || !enable) return null;
        try {
            NBTTagCompound.getMethod("setShort", String.class, short.class).invoke(nbt, key, s);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetLong(Object nbt, String key, long l) {
        if(nbt == null || !enable) return null;
        try {
            NBTTagCompound.getMethod("setLong", String.class, long.class).invoke(nbt, key, l);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetFloat(Object nbt, String key, float f) {
        if(nbt == null || !enable) return null;
        try {
            NBTTagCompound.getMethod("setFloat", String.class, float.class).invoke(nbt, key, f);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetDouble(Object nbt, String key, double d) {
        if(nbt == null || !enable) return null;
        try {
            NBTTagCompound.getMethod("setDouble", String.class, double.class).invoke(nbt, key, d);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetByteArray(Object nbt, String key, byte[] b) {
        if(nbt == null || !enable) return null;
        try {
            NBTTagCompound.getMethod("setByteArray", String.class, byte[].class).invoke(nbt, key, b);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetString(Object nbt, String key, String s) {
        if(nbt == null || !enable) return null;
        try {
            NBTTagCompound.getMethod("setString", String.class, String.class).invoke(nbt, key, s);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetInt(Object nbt, String key, int i) {
        if(nbt == null || !enable) return null;
        try {
            NBTTagCompound.getMethod("setInt", String.class, int.class).invoke(nbt, key, i);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetIntArray(Object nbt, String key, Object int_array) {
        if(nbt == null || int_array == null || !enable) return null;
        try {
//            NBTTagCompound.getMethod("set", String.class, NBTTagIntArray).invoke(nbt, key, int_array);
            NBTTagCompound.getMethod("set", String.class, NBTBase).invoke(nbt, key, int_array);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetTagList(Object nbt, String key, Object tag_list) {
        if(nbt == null || tag_list == null || !enable) return null;
        try {
//            NBTTagCompound.getMethod("set", String.class, NBTTagList).invoke(nbt, key, tag_list);
            NBTTagCompound.getMethod("set", String.class, NBTBase).invoke(nbt, key, tag_list);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSetTagCompound(Object nbt, String key, Object tag) {
        if(nbt == null || tag == null || !enable) return null;
        try {
//            NBTTagCompound.getMethod("set", String.class, NBTTagCompound).invoke(nbt, key, tag);
            NBTTagCompound.getMethod("set", String.class, NBTBase).invoke(nbt, key, tag);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagCompoundSet(Object nbt, String key, Object tag) {
        if(nbt == null || tag == null || !enable) return null;
        try {
//            NBTTagCompound.getMethod("set", String.class, NBTTagCompound).invoke(nbt, key, tag);
            NBTTagCompound.getMethod("set", String.class, NBTBase).invoke(nbt, key, tag);
            return nbt;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
    
    public static Object NBTTagListAddNBTTagCompound(Object list, Object nbt) {
        if(nbt == null || list == null || !enable) return null;
        try {
//            NBTTagList.getMethod("add", NBTTagCompound).invoke(list, nbt);
            NBTTagList.getMethod("add", int.class, NBTBase).invoke(list, 0, nbt);
            return list;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sendErrorMessage(ex);
            enable = false;
            return null;
        }
    }
}
