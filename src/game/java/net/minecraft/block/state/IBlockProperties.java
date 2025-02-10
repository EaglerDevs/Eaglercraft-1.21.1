package net.minecraft.block.state;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IBlockProperties
{
    Material getMaterial();

    boolean isFullBlock();

    int getLightOpacity();

    int getLightValue();

    boolean isTranslucent();

    boolean useNeighborBrightness();

    MapColor getMapColor();

    /**
     * Returns the blockstate with the given rotation. If inapplicable, returns itself.
     */
    IBlockState withRotation(Rotation rot);

    /**
     * Returns the blockstate mirrored in the given way. If inapplicable, returns itself.
     */
    IBlockState withMirror(Mirror mirrorIn);

    boolean isFullCube();

    float getAmbientOcclusionLightValue();

    boolean isBlockNormalCube();

    boolean isNormalCube();

    boolean canProvidePower();

    boolean hasComparatorInputOverride();

    int getComparatorInputOverride(World worldIn, BlockPos pos);

    float getBlockHardness(World worldIn, BlockPos pos);

    float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos);

    int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side);

    IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos);

    boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing);

    boolean isOpaqueCube();

    boolean isFullyOpaque();
}
