package net.minecraft.world.biome;

import net.eaglerdevs.modsConfig;
import net.eaglerdevs.modsSodium.SodiumBlockAccess;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

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
public class BiomeColorHelper {
	private static final BiomeColorHelper.ColorResolver field_180291_a = new BiomeColorHelper.ColorResolver() {
		public int getColorAtPos(BiomeGenBase blockPosition, BlockPos parBlockPos) {
			return blockPosition.getGrassColorAtPos(parBlockPos);
		}
	};
	private static final BiomeColorHelper.ColorResolver field_180289_b = new BiomeColorHelper.ColorResolver() {
		public int getColorAtPos(BiomeGenBase biomegenbase, BlockPos blockpos) {
			return biomegenbase.getFoliageColorAtPos(blockpos);
		}
	};
	private static final BiomeColorHelper.ColorResolver field_180290_c = new BiomeColorHelper.ColorResolver() {
		public int getColorAtPos(BiomeGenBase biomegenbase, BlockPos var2) {
			return biomegenbase.waterColorMultiplier;
		}
	};

	private static int func_180285_a(IBlockAccess parIBlockAccess, BlockPos parBlockPos,
			BiomeColorHelper.ColorResolver parColorResolver) {
		if (parIBlockAccess instanceof SodiumBlockAccess) {
			return ((SodiumBlockAccess) parIBlockAccess).getBlockTint(parBlockPos, parColorResolver);
		}

		int radius = Config.biomeBlendRadius;

		if (radius == 0) {
			parColorResolver.getColorAtPos(parIBlockAccess.getBiomeGenForCoords(parBlockPos), parBlockPos);
		} else {
			int blockCount = (radius * 2 + 1) * (radius * 2 + 1);
			int i = 0;
			int j = 0;
			int k = 0;

			BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

			for (int z = -radius; z <= radius; z++) {
				for (int x = -radius; x <= radius; x++) {
					mutablePos.setPos(parBlockPos.getX() + x, parBlockPos.getY(), parBlockPos.getZ() + z);
					int l = parColorResolver.getColorAtPos(parIBlockAccess.getBiomeGenForCoords(mutablePos),
							mutablePos);
					i += (l & 16711680) >> 16;
					j += (l & 65280) >> 8;
					k += l & 255;
				}
			}

			return (i / blockCount & 255) << 16 | (j / blockCount & 255) << 8 | k / blockCount & 255;
		}

		return 0; // we should never reach this point
	}

	public static int getGrassColorAtPos(IBlockAccess parIBlockAccess, BlockPos parBlockPos) {
		return func_180285_a(parIBlockAccess, parBlockPos, field_180291_a);
	}

	public static int getFoliageColorAtPos(IBlockAccess parIBlockAccess, BlockPos parBlockPos) {
		return func_180285_a(parIBlockAccess, parBlockPos, field_180289_b);
	}

	public static int getWaterColorAtPos(IBlockAccess parIBlockAccess, BlockPos parBlockPos) {
		return func_180285_a(parIBlockAccess, parBlockPos, field_180290_c);
	}

	public interface ColorResolver {
		int getColorAtPos(BiomeGenBase var1, BlockPos var2);
	}
}