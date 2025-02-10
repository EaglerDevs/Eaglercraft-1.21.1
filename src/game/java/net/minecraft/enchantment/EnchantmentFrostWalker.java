package net.minecraft.enchantment;

import net.eaglerdevs.modsEaglerItems;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EnchantmentFrostWalker extends Enchantment {
    protected EnchantmentFrostWalker(int parInt1, ResourceLocation parResourceLocation, int parInt2) {
        super(parInt1, parResourceLocation, parInt2, EnumEnchantmentType.ARMOR_FEET);
        this.setName("frostWalker");
    }

    public int getMinEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    public boolean isTreasureEnchantment() {
        return true;
    }

    public int getMaxLevel() {
        return 2;
    }

    public static void freezeNearby(EntityLivingBase living, World worldIn, BlockPos pos, int level) {
        if (living.onGround) {
            float f = (float) Math.min(16, 2 + level);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(
                    pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
                if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY,
                        living.posZ) <= (double) (f * f)) {
                    blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(),
                            blockpos$mutableblockpos1.getY() + 1,
                            blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getBlock() == Blocks.air) {
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getBlock().getMaterial() == Material.water
                                && ((Integer) iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0
                                && worldIn.canBlockBePlaced(EaglerItems.getEaglerBlock("frosted_ice"),
                                        blockpos$mutableblockpos1, false,
                                        EnumFacing.DOWN, (Entity) null, (ItemStack) null)) {
                            worldIn.setBlockState(blockpos$mutableblockpos1,
                                    EaglerItems.getEaglerBlock("frosted_ice").getDefaultState());
                            worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(),
                                    EaglerItems.getEaglerBlock("frosted_ice"),
                                    MathHelper.getRandomIntegerInRange(living.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines if the enchantment passed can be applyied together with this
     * enchantment.
     */
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantment.depthStrider;
    }
}
