package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.eaglerdevs.modsEaglerItems;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockChorusFlower extends Block {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);

    protected BlockChorusFlower() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setTickRandomly(true);
    }

    @Nullable

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, EaglercraftRandom rand, int fortune) {
        return null;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, EaglercraftRandom rand) {
        if (!this.canSurvive(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        } else {
            BlockPos blockpos = pos.up();

            if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 256) {
                int i = ((Integer) state.getValue(AGE)).intValue();

                if (i < 5 && rand.nextInt(1) == 0) {
                    boolean flag = false;
                    boolean flag1 = false;
                    Block block = worldIn.getBlockState(pos.down()).getBlock();

                    if (block == Blocks.end_stone) {
                        flag = true;
                    } else if (block == EaglerItems.getEaglerBlock("chorus_plant")) {
                        int j = 1;

                        for (int k = 0; k < 4; ++k) {
                            Block block1 = worldIn.getBlockState(pos.down(j + 1)).getBlock();

                            if (block1 != EaglerItems.getEaglerBlock("chorus_plant")) {
                                if (block1 == Blocks.end_stone) {
                                    flag1 = true;
                                }

                                break;
                            }

                            ++j;
                        }

                        int i1 = 4;

                        if (flag1) {
                            ++i1;
                        }

                        if (j < 2 || rand.nextInt(i1) >= j) {
                            flag = true;
                        }
                    } else if (block == Blocks.air) {
                        flag = true;
                    }

                    if (flag && areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing) null)
                            && worldIn.isAirBlock(pos.up(2))) {
                        worldIn.setBlockState(pos, EaglerItems.getEaglerBlock("chorus_plant").getDefaultState(), 2);
                        this.placeGrownFlower(worldIn, blockpos, i);
                    } else if (i < 4) {
                        int l = rand.nextInt(4);
                        boolean flag2 = false;

                        if (flag1) {
                            ++l;
                        }

                        for (int j1 = 0; j1 < l; ++j1) {
                            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                            BlockPos blockpos1 = pos.offset(enumfacing);

                            if (worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down())
                                    && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite())) {
                                this.placeGrownFlower(worldIn, blockpos1, i + 1);
                                flag2 = true;
                            }
                        }

                        if (flag2) {
                            worldIn.setBlockState(pos, EaglerItems.getEaglerBlock("chorus_plant").getDefaultState(), 2);
                        } else {
                            this.placeDeadFlower(worldIn, pos);
                        }
                    } else if (i == 4) {
                        this.placeDeadFlower(worldIn, pos);
                    }
                }
            }
        }
    }

    private void placeGrownFlower(World p_185602_1_, BlockPos p_185602_2_, int p_185602_3_) {
        p_185602_1_.setBlockState(p_185602_2_, this.getDefaultState().withProperty(AGE, Integer.valueOf(p_185602_3_)),
                2);
        p_185602_1_.playAuxSFX(1033, p_185602_2_, 0);
    }

    private void placeDeadFlower(World p_185605_1_, BlockPos p_185605_2_) {
        p_185605_1_.setBlockState(p_185605_2_, this.getDefaultState().withProperty(AGE, Integer.valueOf(5)), 2);
        p_185605_1_.playAuxSFX(1034, p_185605_2_, 0);
    }

    private static boolean areAllNeighborsEmpty(World p_185604_0_, BlockPos p_185604_1_, EnumFacing p_185604_2_) {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (enumfacing != p_185604_2_ && !p_185604_0_.isAirBlock(p_185604_1_.offset(enumfacing))) {
                return false;
            }
        }

        return true;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canSurvive(worldIn, pos);
    }

    public void onNeighborBlockChange(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_,
            Block p_189540_4_) {
        if (!this.canSurvive(p_189540_2_, p_189540_3_)) {
            p_189540_2_.scheduleUpdate(p_189540_3_, this, 1);
        }
    }

    public boolean canSurvive(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();

        if (block != EaglerItems.getEaglerBlock("chorus_plant") && block != Blocks.end_stone) {
            if (block == Blocks.air) {
                int i = 0;

                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                    Block block1 = worldIn.getBlockState(pos.offset(enumfacing)).getBlock();

                    if (block1 == EaglerItems.getEaglerBlock("chorus_plant")) {
                        ++i;
                    } else if (block1 != Blocks.air) {
                        return false;
                    }
                }

                return i == 1;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state,
            @Nullable TileEntity te) {
        super.harvestBlock(worldIn, player, pos, state, te);
        spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this)));
    }

    protected ItemStack createStackedBlock(IBlockState state) {
        return null;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return ((Integer) state.getValue(AGE)).intValue();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { AGE });
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
    }

    public static void generatePlant(World worldIn, BlockPos pos, EaglercraftRandom rand, int p_185603_3_) {
        worldIn.setBlockState(pos, EaglerItems.getEaglerBlock("chorus_plant").getDefaultState(), 2);
        growTreeRecursive(worldIn, pos, rand, pos, p_185603_3_, 0);
    }

    private static void growTreeRecursive(World worldIn, BlockPos p_185601_1_, EaglercraftRandom rand,
            BlockPos p_185601_3_,
            int p_185601_4_, int p_185601_5_) {
        int i = rand.nextInt(4) + 1;

        if (p_185601_5_ == 0) {
            ++i;
        }

        for (int j = 0; j < i; ++j) {
            BlockPos blockpos = p_185601_1_.up(j + 1);

            if (!areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing) null)) {
                return;
            }

            worldIn.setBlockState(blockpos, EaglerItems.getEaglerBlock("chorus_plant").getDefaultState(), 2);
        }

        boolean flag = false;

        if (p_185601_5_ < 4) {
            int l = rand.nextInt(4);

            if (p_185601_5_ == 0) {
                ++l;
            }

            for (int k = 0; k < l; ++k) {
                EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                BlockPos blockpos1 = p_185601_1_.up(i).offset(enumfacing);

                if (Math.abs(blockpos1.getX() - p_185601_3_.getX()) < p_185601_4_
                        && Math.abs(blockpos1.getZ() - p_185601_3_.getZ()) < p_185601_4_
                        && worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down())
                        && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite())) {
                    flag = true;
                    worldIn.setBlockState(blockpos1, EaglerItems.getEaglerBlock("chorus_plant").getDefaultState(), 2);
                    growTreeRecursive(worldIn, blockpos1, rand, p_185601_3_, p_185601_4_, p_185601_5_ + 1);
                }
            }
        }

        if (!flag) {
            worldIn.setBlockState(p_185601_1_.up(i),
                    EaglerItems.getEaglerBlock("chorus_flower").getDefaultState().withProperty(AGE, Integer.valueOf(5)),
                    2);
        }
    }
}
