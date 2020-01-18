package greymerk.roguelike.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.server.v1_15_R1.*;

import java.util.Map;

public enum JsonNBT {

	END, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTEARRAY, STRING, LIST, COMPOUND, INTARRAY;


	public static NBTTagCompound jsonToCompound(JsonObject data) {

		NBTTagCompound toReturn = new NBTTagCompound();

		for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
			JsonObject obj = entry.getValue().getAsJsonObject();
			JsonNBT type = getType(obj.get("type").getAsString());
			setCompoundEntry(toReturn, entry.getKey(), type, obj.get("value"));
		}

		return toReturn;
	}

	public static NBTTagList jsonToList(JsonObject data) {
		NBTTagList toReturn = new NBTTagList();
		JsonNBT type = JsonNBT.valueOf(data.get("type").getAsString());
		for (JsonElement e : data.get("value").getAsJsonArray()) {
			append(toReturn, type, e);
		}

		return toReturn;
	}

	public static JsonNBT getType(String type) {
		return JsonNBT.valueOf(type);
	}

	public static void setCompoundEntry(NBTTagCompound nbt, String name, JsonNBT type, JsonElement data) {
		switch (type) {
			case END:
				return;
			case BYTE:
				nbt.setByte(name, data.getAsByte());
				return;
			case SHORT:
				nbt.setShort(name, data.getAsShort());
				return;
			case INT:
				nbt.setInt(name, data.getAsInt());
				return;
			case LONG:
				nbt.setLong(name, data.getAsLong());
				return;
			case FLOAT:
				nbt.setFloat(name, data.getAsFloat());
				return;
			case DOUBLE:
				nbt.setDouble(name, data.getAsDouble());
				return;
			case BYTEARRAY:
				nbt.setByteArray(name, jsonToByteArray(data.getAsJsonObject()));
				return;
			case STRING:
				nbt.setString(name, data.getAsString());
				return;
			case LIST:
				nbt.set(name, jsonToList(data.getAsJsonObject()));
				return;
			case COMPOUND:
				nbt.set(name, jsonToCompound(data.getAsJsonObject()));
				return;
			case INTARRAY:
				nbt.set(name, new NBTTagIntArray(jsonToIntArray(data.getAsJsonObject())));
				return;
		}
	}

	public static void append(NBTTagList nbt, JsonNBT type, JsonElement data) {
		switch (type) {
            case END:
                return;
            case BYTE:
                nbt.add(NBTTagByte.a(data.getAsByte()));
                return;
            case SHORT:
                nbt.add(NBTTagShort.a(data.getAsShort()));
                return;
            case INT:
                nbt.add(NBTTagInt.a(data.getAsInt()));
                return;
            case LONG:
                nbt.add(NBTTagLong.a(data.getAsLong()));
                return;
            case FLOAT:
                nbt.add(NBTTagFloat.a(data.getAsFloat()));
                return;
            case DOUBLE:
                nbt.add(NBTTagDouble.a(data.getAsDouble()));
                return;
            case BYTEARRAY:
                nbt.add(new NBTTagByteArray(jsonToByteArray(data.getAsJsonObject())));
                return;
            case STRING:
                nbt.add(NBTTagString.a(data.getAsString()));
                return;
            case LIST:
                nbt.add(jsonToList(data.getAsJsonObject()));
                return;
            case COMPOUND:
                nbt.add(jsonToCompound(data.getAsJsonObject()));
                return;
            case INTARRAY:
                nbt.add(new NBTTagIntArray(jsonToIntArray(data.getAsJsonObject())));
                return;
		}
	}

	public static byte[] jsonToByteArray(JsonObject data) {
		JsonArray arr = data.getAsJsonArray();

		byte[] bytes = new byte[arr.size()];

		int i = 0;
		for (JsonElement e : arr) {
			bytes[i] = e.getAsByte();
			i++;
		}

		return bytes;
	}

	public static int[] jsonToIntArray(JsonObject data) {
		JsonArray arr = data.getAsJsonArray();

		int[] ints = new int[arr.size()];

		int i = 0;
		for (JsonElement e : arr) {
			ints[i] = e.getAsInt();
			i++;
		}

		return ints;
	}
}
