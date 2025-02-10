package net.minecraft.block;

import java.util.List;

import javax.annotation.Nullable;

import net.eaglerdevs.modsEaglerItems;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChorusPlant extends Block {
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    protected BlockChorusPlant() {
        super(Material.plants);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false))
                .withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false))
                .withProperty(WEST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false))
                .withProperty(DOWN, Boolean.valueOf(false)));
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies
     * properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        Block block2 = worldIn.getBlockState(pos.north()).getBlock();
        Block block3 = worldIn.getBlockState(pos.east()).getBlock();
        Block block4 = worldIn.getBlockState(pos.south()).getBlock();
        Block block5 = worldIn.getBlockState(pos.west()).getBlock();
        return state
                .withProperty(DOWN,
                        Boolean.valueOf(block == this || block == EaglerItems.getEaglerBlock("chorus_flower")
                                || block == Blocks.end_stone))
                .withProperty(UP,
                        Boolean.valueOf(block1 == this || block1 == EaglerItems.getEaglerBlock("chorus_flower")))
                .withProperty(NORTH,
                        Boolean.valueOf(block2 == this || block2 == EaglerItems.getEaglerBlock("chorus_flower")))
                .withProperty(EAST,
                        Boolean.valueOf(block3 == this || block3 == EaglerItems.getEaglerBlock("chorus_flower")))
                .withProperty(SOUTH,
                        Boolean.valueOf(block4 == this || block4 == EaglerItems.getEaglerBlock("chorus_flower")))
                .withProperty(WEST,
                        Boolean.valueOf(block5 == this || block5 == EaglerItems.getEaglerBlock("chorus_flower")));
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
        boolean north = canConnectTo(world.getBlockState(pos.north()));
        boolean south = canConnectTo(world.getBlockState(pos.south()));
        boolean west = canConnectTo(world.getBlockState(pos.west()));
        boolean east = canConnectTo(world.getBlockState(pos.east()));
        boolean up = canConnectTo(world.getBlockState(pos.up()));
        boolean down = canConnectTo(world.getBlockState(pos.down()));

        float minX = 0.3125F; // Default minimum X dimension if not connected on west
        float minY = 0.3125F; // Default minimum Y dimension if not connected on down
        float minZ = 0.3125F; // Default minimum Z dimension if not connected on north
        float maxX = 0.6875F; // Default maximum X dimension if not connected on east
        float maxY = 0.6875F; // Default maximum Y dimension if not connected on up
        float maxZ = 0.6875F; // Default maximum Z dimension if not connected on south

        if (north)
            minZ = 0.0F;
        if (south)
            maxZ = 1.0F;
        if (west)
            minX = 0.0F;
        if (east)
            maxX = 1.0F;
        if (down)
            minY = 0.0F;
        if (up)
            maxY = 1.0F;

        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public boolean canConnectTo(IBlockState state) {
        Block block = state.getBlock();
        return block == EaglerItems.getEaglerBlock("chorus_plant")
                || block == EaglerItems.getEaglerBlock("chorus_flower")
                || block == Blocks.end_stone;
    }

    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.setBlockBoundsBasedOnState(world, blockpos);
        return super.getCollisionBoundingBox(world, blockpos, iblockstate);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, EaglercraftRandom rand) {
        if (!this.canSurviveAt(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    @Nullable

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, EaglercraftRandom rand, int fortune) {
        return EaglerItems.getEaglerItem("chorus_fruit");
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(EaglercraftRandom random) {
        return random.nextInt(2);
    }

    public boolean isFullCube() {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for
     * render
     */
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canSurviveAt(worldIn, pos) : false;
    }

    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (!this.canSurviveAt(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    public boolean canSurviveAt(World wordIn, BlockPos pos) {
        boolean flag = wordIn.isAirBlock(pos.up());
        boolean flag1 = wordIn.isAirBlock(pos.down());

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            BlockPos blockpos = pos.offset(enumfacing);
            Block block = wordIn.getBlockState(blockpos).getBlock();

            if (block == this) {
                if (!flag && !flag1) {
                    return false;
                }

                Block block1 = wordIn.getBlockState(blockpos.down()).getBlock();

                if (block1 == this || block1 == Blocks.end_stone) {
                    return true;
                }
            }
        }

        Block block2 = wordIn.getBlockState(pos.down()).getBlock();
        return block2 == this || block2 == Blocks.end_stone;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos,
            EnumFacing side) {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
        return block != this && block != EaglerItems.getEaglerBlock("chorus_flower")
                && (side != EnumFacing.DOWN || block != Blocks.end_stone);
    }

    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { NORTH, EAST, SOUTH, WEST, UP, DOWN });
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }
}
