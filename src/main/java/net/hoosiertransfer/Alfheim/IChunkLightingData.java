package net.eaglerdevs.modsAlfheim;

import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;

public interface IChunkLightingData {

    short[] alfheim$getNeighborLightChecks();

    void alfheim$setNeighborLightChecks(final short[] data);

    boolean alfheim$isLightInitialized();

    void alfheim$setLightInitialized(final boolean lightInitialized);

    void alfheim$setSkylightUpdatedPublic();

    void alfheim$initNeighborLightChecks();

    byte alfheim$getCachedLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos pos);
}