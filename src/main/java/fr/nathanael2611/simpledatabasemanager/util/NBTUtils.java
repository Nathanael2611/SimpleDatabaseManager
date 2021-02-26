package fr.nathanael2611.simpledatabasemanager.util;

import net.minecraft.nbt.*;

import java.util.*;

public class NBTUtils {

    public static NBTBase serializeNBT(Object nbt) {
        if (nbt instanceof Boolean) return new NBTTagByte((byte)(((boolean)nbt) ? 1 : 0));
        if (nbt instanceof Integer) return new NBTTagInt((Integer) nbt);
        if (nbt instanceof String) return new NBTTagString((String)nbt);
        if (nbt instanceof Float) return new NBTTagFloat((Float) nbt);
        if (nbt instanceof Double) return new NBTTagDouble((Double) nbt);
        if (nbt instanceof Map) {
            NBTTagList list = new NBTTagList();
            NBTTagCompound index = new NBTTagCompound();
            index.setBoolean("list", false);
            list.appendTag(index);
            Map map = (Map)nbt;
            map.entrySet().forEach(o -> {
                Map.Entry object = (Map.Entry) o;
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setTag("K", serializeNBT(object.getKey()));
                tagCompound.setTag("V", serializeNBT(object.getValue()));
                list.appendTag(tagCompound);
            });
            return list;
        }
        if (nbt instanceof ArrayList) {
            NBTTagList list = new NBTTagList();
            NBTTagCompound index = new NBTTagCompound();
            index.setBoolean("list", true);
            list.appendTag(index);
            ArrayList collection = (ArrayList)nbt;
            for (Object object : collection) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setTag("V", serializeNBT(object));
                list.appendTag(tagCompound);
            }
            return list;
        }
        //exception ?
        return null;
    }

    public static Object deserializeNBT(NBTBase nbt) {
        switch (nbt.getId()) {
            case 1:
                return ((NBTTagByte)nbt).getByte();
            case 3:
                return ((NBTTagInt)nbt).getInt();
            case 4:
                return ((NBTTagLong)nbt).getLong();
            case 5:
                return ((NBTTagFloat)nbt).getFloat();
            case 6:
                return ((NBTTagDouble)nbt).getDouble();
            case 8:
                return ((NBTTagString)nbt).getString();
            case 9: {
                NBTTagList nbtTagList = (NBTTagList)nbt;
                NBTTagCompound index = nbtTagList.getCompoundTagAt(0);
                if (index.getBoolean("list")) {
                    List<Object> arrayList = new ArrayList<>();
                    for (int i = 1; i < nbtTagList.tagCount(); i++) {
                        NBTTagCompound tagCompound = (NBTTagCompound) nbtTagList.get(i);
                        arrayList.add(deserializeNBT(tagCompound.getTag("V")));
                    }
                    return arrayList;
                } else {
                    Map<Object, Object> hashMap = new HashMap<>();
                    for (int i = 1; i < nbtTagList.tagCount(); i++) {
                        NBTTagCompound tagCompound = (NBTTagCompound) nbtTagList.get(i);
                        hashMap.put(deserializeNBT(tagCompound.getTag("K")), deserializeNBT(tagCompound.getTag("V")));
                    }
                    return hashMap;
                }
            }
            default:
                //exception ?
                return null;
        }
    }
}
