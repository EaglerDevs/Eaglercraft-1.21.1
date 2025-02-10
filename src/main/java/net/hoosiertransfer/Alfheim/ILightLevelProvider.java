package net.eaglerdevs.modsAlfheim;

import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;

/**
 * @author Luna Lage (Desoroxxx)
 * @since 1.0
 */
public interface ILightLevelProvider {

    int alfheim$getLight(final EnumSkyBlock lightType, final BlockPos blockPos);
}