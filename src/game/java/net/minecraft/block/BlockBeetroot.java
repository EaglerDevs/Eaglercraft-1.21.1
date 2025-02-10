package net.minecraft.block;

import java.util.Random;

import net.eaglerdevs.modsEaglerItems;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockBeetroot extends BlockCrops {
    public int getMaxAge() {
        return 3;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, EaglercraftRandom rand)
    {
        if (rand.nextInt(3) == 0)
        {
            this.checkAndDropBlock(worldIn, pos, state);
        }
        else
        {
            super.updateTick(worldIn, pos, state, rand);
        }
    }

    protected int getBonemealAgeIncrease(World worldIn)
    {
        return super.getBonemealAgeIncrease(worldIn) / 3;
    }

    protected Item getSeed() {
        return EaglerItems.getEaglerItem("beetroot_seeds");
    }

    protected Item getCrop() {
        return EaglerItems.getEaglerItem("beetroot");
    }
}
