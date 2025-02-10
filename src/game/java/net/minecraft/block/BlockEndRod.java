package net.minecraft.block;

import net.eaglerdevs.modsEaglerItems;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndRod extends Block {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    protected BlockEndRod() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withProperty(FACING, mirrorIn.mirror((EnumFacing)state.getValue(FACING)));
    }

    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.setBlockBoundsBasedOnState(world, blockpos);
        return super.getCollisionBoundingBox(world, blockpos, iblockstate);
    }

    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockpos) {
        this.setBlockBoundsBasedOnState(world, blockpos);
        return super.getSelectedBoundingBox(world, blockpos);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos blockpos) {
        IBlockState iblockstate = iblockaccess.getBlockState(blockpos);
        if (iblockstate.getBlock() != this) {
            return;
        }
        EnumFacing enumfacing = (EnumFacing) iblockstate.getValue(FACING);
        switch (enumfacing.getAxis()) {
            case X:
            default:
                this.setBlockBounds(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
                break;

            case Z:
                this.setBlockBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F,
                        1.0F);
                break;

            case Y:
                this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return true;
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer) {
        IBlockState iblockstate = worldIn.getBlockState(pos.offset(facing.getOpposite()));

        if (iblockstate.getBlock() == EaglerItems.getEaglerBlock("end_rod")) {
            EnumFacing enumfacing = (EnumFacing) iblockstate.getValue(FACING);

            if (enumfacing == facing) {
                return this.getDefaultState().withProperty(FACING, facing.getOpposite());
            }
        }

        return this.getDefaultState().withProperty(FACING, facing);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    }

    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, EaglercraftRandom rand) {
        EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
        double d0 = (double) pos.getX() + 0.55D - (double) (rand.nextFloat() * 0.1F);
        double d1 = (double) pos.getY() + 0.55D - (double) (rand.nextFloat() * 0.1F);
        double d2 = (double) pos.getZ() + 0.55D - (double) (rand.nextFloat() * 0.1F);
        double d3 = (double) (0.4F - (rand.nextFloat() + rand.nextFloat()) * 0.4F);

        if (rand.nextInt(5) == 0) {
            worldIn.spawnParticle(EnumParticleTypes.END_ROD, d0 + (double)
                enumfacing.getFrontOffsetX() * d3,
                d1 + (double) enumfacing.getFrontOffsetY() * d3, d2 + (double)
                enumfacing.getFrontOffsetZ() * d3,
                rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D,
                rand.nextGaussian() * 0.005D,
                new int[0]);
        }
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();
        iblockstate = iblockstate.withProperty(FACING, EnumFacing.getFront(meta));
        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { FACING });
    }

    public int getMobilityFlag(IBlockState state) {
        return 0;
    }
}