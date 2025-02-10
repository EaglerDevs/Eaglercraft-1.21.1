package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.eaglerdevs.modsEaglerItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
public class ItemSpade extends ItemTool {
	private static Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass,
		Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand,
		EaglerItems.getEaglerBlock("grass_path") });

    public ItemSpade(Item.ToolMaterial material) {
        super(1.5F, -3.0F, material, EFFECTIVE_ON);
    }

	/**
	 * +
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(Block block) {
		return block == Blocks.snow_layer ? true : block == Blocks.snow;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing facing,
			float hitX, float hitY, float hitZ) {
		if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)) {
			return false;
		} else {
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (facing != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getBlock().getMaterial() == Material.air
					&& block == Blocks.grass) {
				IBlockState iblockstate1 = EaglerItems.getEaglerBlock("grass_path").getDefaultState();
				// worldIn.playSound(playerIn, pos, "item.shovel.flatten", SoundCategory.BLOCKS,
				// 1.0F,
				// 1.0F);

				if (!worldIn.isRemote) {
					worldIn.setBlockState(pos, iblockstate1, 11);
					stack.damageItem(1, playerIn);
				}

				return true;
			} else {
				return false;
			}
		}
	}
}