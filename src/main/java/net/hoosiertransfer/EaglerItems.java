package net.eaglerdevs.modshoosiertransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.Item;

public class EaglerItems {
    protected static Map<String, EaglerCustomBlock> blockMap = new HashMap<>();
    protected static Map<String, Item> itemMap = new HashMap<>();
    protected static Map<String, Boolean> itemModelRegisterMap = new HashMap<>();

    public static void registerEaglerBlock(String id, Block block) {
        blockMap.put(id, new EaglerCustomBlock(block));
    }

    public static void registerEaglerBlock(String id, Block block, boolean hasItem) {
        blockMap.put(id, new EaglerCustomBlock(block, hasItem));
    }

    public static void registerEaglerSlab(String id, BlockSlab block, BlockSlab slabDouble, List<String> variants) {
        blockMap.put(id, new EaglerCustomBlock(block, slabDouble, variants));
    }

    public static void registerEaglerItem(String id, Item item) {
        itemMap.put(id, item);
        itemModelRegisterMap.put(id, true);
    }

    public static void registerEaglerItem(String id, Item item, boolean registerModel) {
        itemMap.put(id, item);
        itemModelRegisterMap.put(id, registerModel);
    }

    public static EaglerCustomBlock getEaglerCustomBlock(String id) {
        EaglerCustomBlock customBlock = blockMap.get(id);
        if (customBlock == null) {
            throw new IllegalArgumentException("No such block: " + id);
        }
        return customBlock;
    }

    public static Block getEaglerBlock(String id) {
        EaglerCustomBlock customBlock = blockMap.get(id);
        if (customBlock == null) {
            throw new IllegalArgumentException("No such block: " + id);
        }
        return customBlock.getBlock();
    }

    public static Item getEaglerItem(String id) {
        Item item = itemMap.get(id);
        if (item == null) {
            throw new IllegalArgumentException("No such item: " + id);
        }
        return item;
    }

    public static List<EaglerCustomBlock> getEaglerCustomBlocks() {
        return new ArrayList<>(blockMap.values());
    }

    public static List<Item> getEaglerItems() {
        return new ArrayList<>(itemMap.values());
    }

    public static List<String> getEaglerBlockIds() {
        return new ArrayList<>(blockMap.keySet());
    }

    public static List<String> getEaglerItemIds() {
        return new ArrayList<>(itemMap.keySet());
    }

    public static List<Map.Entry<String, EaglerCustomBlock>> getEaglerCustomBlockEntries() {
        return new ArrayList<>(blockMap.entrySet());
    }

    public static List<Map.Entry<String, Item>> getEaglerItemEntries() {
        return new ArrayList<>(itemMap.entrySet());
    }

    public static boolean shouldRegisterModel(String id) {
        return itemModelRegisterMap.get(id);
    }
}
