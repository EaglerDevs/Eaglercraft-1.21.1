package net.eaglerdevs.modsAlfheim.lighting;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class LightUtil {
    public static int getLightValueForState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.getLightValue(blockAccess, blockPos);
    }
}
