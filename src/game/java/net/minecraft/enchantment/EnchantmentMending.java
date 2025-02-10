package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentMending extends Enchantment {
    public EnchantmentMending(int parInt1, ResourceLocation parResourceLocation, int parInt2) {
        super(parInt1, parResourceLocation, parInt2, EnumEnchantmentType.BREAKABLE);
        this.setName("mending");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level
     * passed.
     */
    public int getMinEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 25;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level
     * passed.
     */
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }

    public boolean isTreasureEnchantment() {
        return true;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 1;
    }
}
