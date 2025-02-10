package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * +
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files (c) 2022-2024 lax1dude, hoosiertransfer,
 * ayunami2000. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class ItemTool extends Item {
	private Set<Block> effectiveBlocks;
	protected float efficiencyOnProperMaterial = 4.0F;
	protected float damageVsEntity;
    protected float attackSpeed;

	protected Item.ToolMaterial toolMaterial;

    protected ItemTool(float attackDamageIn, float attackSpeedIn, Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn)
    {
        this.efficiencyOnProperMaterial = 4.0F;
        this.toolMaterial = materialIn;
        this.effectiveBlocks = effectiveBlocksIn;
        this.maxStackSize = 1;
        this.setMaxDamage(materialIn.getMaxUses());
        this.efficiencyOnProperMaterial = materialIn.getEfficiencyOnProperMaterial();
        this.damageVsEntity = attackDamageIn + materialIn.getDamageVsEntity();
        this.attackSpeed = attackSpeedIn;
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    protected ItemTool(Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn)
    {
        this(0.0F, 0.0F, materialIn, effectiveBlocksIn);
    }

    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : 1.0F;
    }

	public float getStrVsBlock(ItemStack var1, Block block) {
		return this.effectiveBlocks.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
	}

	/**
	 * +
	 * Current implementations of this method in child classes do
	 * not use the entry argument beside ev. They just raise the
	 * damage on the stack.
	 */
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase var2, EntityLivingBase entitylivingbase) {
		itemstack.damageItem(2, entitylivingbase);
		return true;
	}

	/**
	 * +
	 * Called when a Block is destroyed using this Item. Return true
	 * to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, BlockPos blockpos,
			EntityLivingBase entitylivingbase) {
		if ((double) block.getBlockHardness(world, blockpos) != 0.0D) {
			itemstack.damageItem(1, entitylivingbase);
		}

		return true;
	}

	/**
	 * +
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D() {
		return true;
	}

	public Item.ToolMaterial getToolMaterial() {
		return this.toolMaterial;
	}

	/**
	 * +
	 * Return the enchantability factor of the item, most of the
	 * time is based on material.
	 */
	public int getItemEnchantability() {
		return this.toolMaterial.getEnchantability();
	}

	/**
	 * +
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName() {
		return this.toolMaterial.toString();
	}

	/**
	 * +
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack itemstack, ItemStack itemstack1) {
		return this.toolMaterial.getRepairItem() == itemstack1.getItem() ? true
				: super.getIsRepairable(itemstack, itemstack1);
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers(int equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        // if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        // {
            multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.damageVsEntity, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        // }

        return multimap;
    }
}