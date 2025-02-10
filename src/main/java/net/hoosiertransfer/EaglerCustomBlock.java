package net.eaglerdevs.modshoosiertransfer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;

public class EaglerCustomBlock {
    private final Block block;
    private final BlockSlab slabDouble;
    private final boolean isSlab;
    private final List<String> variants;
    private final boolean hasItem;

    public EaglerCustomBlock(Block block) {
        this.block = block;
        this.isSlab = false;
        this.slabDouble = null;
        this.variants = new ArrayList<>();
        this.hasItem = true;
    }

    public EaglerCustomBlock(BlockSlab slabHalf, BlockSlab slabDouble, List<String> variants) {
        this.block = (Block) slabHalf;
        this.slabDouble = slabDouble;
        this.isSlab = true;
        this.variants = variants;
        this.hasItem = true;
    }

    public EaglerCustomBlock(Block block, boolean hasItem) {
        this.block = block;
        this.isSlab = false;
        this.slabDouble = null;
        this.variants = new ArrayList<>();
        this.hasItem = hasItem;
    }

    public Block getBlock() {
        return block;
    }

    public BlockSlab getSlabDouble() {
        return slabDouble;
    }

    public boolean isSlab() {
        return isSlab;
    }

    public List<String> getVariants() {
        return variants;
    }

    public boolean hasItem() {
        return hasItem;
    }
}
