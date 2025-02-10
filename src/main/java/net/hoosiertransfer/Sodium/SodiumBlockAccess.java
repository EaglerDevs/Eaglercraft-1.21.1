package net.eaglerdevs.modsSodium;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraft.util.BlockPos;

public interface SodiumBlockAccess extends IBlockAccess {
    int getBlockTint(BlockPos pos, BiomeColorHelper.ColorResolver resolver);
}