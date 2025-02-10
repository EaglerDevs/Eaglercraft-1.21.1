package net.minecraft.world.gen;

import java.util.List;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenEndIsland;

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
public class ChunkProviderEnd implements IChunkProvider {
	private EaglercraftRandom rand;
	protected static final IBlockState END_STONE = Blocks.end_stone.getDefaultState();
	protected static final IBlockState AIR = Blocks.air.getDefaultState();
	private final NoiseGeneratorOctaves lperlinNoise1;
	private final NoiseGeneratorOctaves lperlinNoise2;
	private final NoiseGeneratorOctaves perlinNoise1;

	public NoiseGeneratorOctaves noiseGen5;

	public NoiseGeneratorOctaves noiseGen6;

	private final World worldObj;

	private final boolean mapFeaturesEnabled;
	// private final MapGenEndCity endCityGen = new MapGenEndCity(this);
	private final NoiseGeneratorSimplex islandNoise;
	private double[] buffer;

	private BiomeGenBase[] biomesForGeneration;
	double[] pnr;
	double[] ar;
	double[] br;
	private final WorldGenEndIsland endIslands = new WorldGenEndIsland();

	public ChunkProviderEnd(World worldIn, long parLong1) {
		this.worldObj = worldIn;
		this.mapFeaturesEnabled = true; // TODO: add this
		this.rand = new EaglercraftRandom(parLong1, !worldIn.getWorldInfo().isOldEaglercraftRandom());
		this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.islandNoise = new NoiseGeneratorSimplex(this.rand);
	}

	public void func_180520_a(int parInt1, int parInt2, ChunkPrimer parChunkPrimer) {
		int i = 2;
		int j = i + 1;
		int k = 33;
		int l = i + 1;
		this.buffer = this.initializeNoiseField(this.buffer, parInt1 * i, 0, parInt2 * i, j, k, l);

		for (int i1 = 0; i1 < i; ++i1) {
			for (int j1 = 0; j1 < i; ++j1) {
				for (int k1 = 0; k1 < 32; ++k1) {
					double d0 = 0.25D;
					double d1 = this.buffer[((i1 + 0) * l + j1 + 0) * k + k1 + 0];
					double d2 = this.buffer[((i1 + 0) * l + j1 + 1) * k + k1 + 0];
					double d3 = this.buffer[((i1 + 1) * l + j1 + 0) * k + k1 + 0];
					double d4 = this.buffer[((i1 + 1) * l + j1 + 1) * k + k1 + 0];
					double d5 = (this.buffer[((i1 + 0) * l + j1 + 0) * k + k1 + 1] - d1) * d0;
					double d6 = (this.buffer[((i1 + 0) * l + j1 + 1) * k + k1 + 1] - d2) * d0;
					double d7 = (this.buffer[((i1 + 1) * l + j1 + 0) * k + k1 + 1] - d3) * d0;
					double d8 = (this.buffer[((i1 + 1) * l + j1 + 1) * k + k1 + 1] - d4) * d0;

					for (int l1 = 0; l1 < 4; ++l1) {
						double d9 = 0.125D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i2 = 0; i2 < 8; ++i2) {
							double d14 = 0.125D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;

							for (int j2 = 0; j2 < 8; ++j2) {
								IBlockState iblockstate = AIR;

								if (d15 > 0.0D) {
									iblockstate = END_STONE;
								}

								int k2 = i2 + i1 * 8;
								int l2 = l1 + k1 * 4;
								int i3 = j2 + j1 * 8;
								parChunkPrimer.setBlockState(k2, l2, i3, iblockstate);
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	public void func_180519_a(ChunkPrimer parChunkPrimer) {
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				int k = 1;
				int l = -1;
				IBlockState iblockstate = END_STONE;
				IBlockState iblockstate1 = END_STONE;

				for (int i1 = 127; i1 >= 0; --i1) {
					IBlockState iblockstate2 = parChunkPrimer.getBlockState(i, i1, j);

					if (iblockstate2.getBlock().getMaterial() == Material.air) {
						l = -1;
					} else if (iblockstate2.getBlock() == Blocks.stone) {
						if (l == -1) {
							if (k <= 0) {
								iblockstate = AIR;
								iblockstate1 = END_STONE;
							}

							l = k;

							if (i1 >= 0) {
								parChunkPrimer.setBlockState(i, i1, j, iblockstate);
							} else {
								parChunkPrimer.setBlockState(i, i1, j, iblockstate1);
							}
						} else if (l > 0) {
							--l;
							parChunkPrimer.setBlockState(i, i1, j, iblockstate1);
						}
					}
				}
			}
		}

	}

	/**
	 * +
	 * Will return back a chunk, if it doesn't exist and its not a
	 * MP client it will generates all the blocks for the specified
	 * chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk(int x, int z) {
		this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration,
				x * 16, z * 16, 16, 16);
		this.func_180520_a(x, z, chunkprimer);
		this.func_180519_a(chunkprimer);

		if (this.mapFeaturesEnabled) {
			// this.endCityGen.generate(this.worldObj, x, z, chunkprimer);
		}

		Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
		byte[] abyte = chunk.getBiomeArray();

		for (int k = 0; k < abyte.length; ++k) {
			abyte[k] = (byte) this.biomesForGeneration[k].biomeID;
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	public Chunk getLoadedChunk(int parInt1, int parInt2) {
		return this.provideChunk(parInt1, parInt2);
	}

	private float getIslandHeightValue(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_) {
		float f = (float) (p_185960_1_ * 2 + p_185960_3_);
		float f1 = (float) (p_185960_2_ * 2 + p_185960_4_);
		float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;

		if (f2 > 80.0F) {
			f2 = 80.0F;
		}

		if (f2 < -100.0F) {
			f2 = -100.0F;
		}

		for (int i = -12; i <= 12; ++i) {
			for (int j = -12; j <= 12; ++j) {
				long k = (long) (p_185960_1_ + i);
				long l = (long) (p_185960_2_ + j);

				if (k * k + l * l > 4096L
						&& this.islandNoise.func_151605_a((double) k, (double) l) < -0.8999999761581421D) {
					float f3 = (MathHelper.abs((float) k) * 3439.0F + MathHelper.abs((float) l) * 147.0F) % 13.0F
							+ 9.0F;
					f = (float) (p_185960_3_ - i * 2);
					f1 = (float) (p_185960_4_ - j * 2);
					float f4 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * f3;

					if (f4 > 80.0F) {
						f4 = 80.0F;
					}

					if (f4 < -100.0F) {
						f4 = -100.0F;
					}

					if (f4 > f2) {
						f2 = f4;
					}
				}
			}
		}

		return f2;
	}

	public boolean isIslandChunk(int p_185961_1_, int p_185961_2_) {
		return (long) p_185961_1_ * (long) p_185961_1_ + (long) p_185961_2_ * (long) p_185961_2_ > 4096L
				&& this.getIslandHeightValue(p_185961_1_, p_185961_2_, 1, 1) >= 0.0F;
	}

	/**
	 * +
	 * generates a subset of the level's terrain data. Takes 7
	 * arguments: the [empty] noise array, the position, and the
	 * size.
	 */
	private double[] initializeNoiseField(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_,
			int p_185963_5_, int p_185963_6_, int p_185963_7_) {
		if (p_185963_1_ == null) {
			p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
		}

		double d0 = 684.412D;
		double d1 = 684.412D;
		d0 = d0 * 2.0D;
		this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_,
				p_185963_6_, p_185963_7_, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
		this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_,
				p_185963_6_, p_185963_7_, d0, d1, d0);
		this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_,
				p_185963_6_, p_185963_7_, d0, d1, d0);
		int i = p_185963_2_ / 2;
		int j = p_185963_4_ / 2;
		int k = 0;

		for (int l = 0; l < p_185963_5_; ++l) {
			for (int i1 = 0; i1 < p_185963_7_; ++i1) {
				float f = this.getIslandHeightValue(i, j, l, i1);

				for (int j1 = 0; j1 < p_185963_6_; ++j1) {
					double d2 = 0.0D;
					double d3 = this.ar[k] / 512.0D;
					double d4 = this.br[k] / 512.0D;
					double d5 = (this.pnr[k] / 10.0D + 1.0D) / 2.0D;

					if (d5 < 0.0D) {
						d2 = d3;
					} else if (d5 > 1.0D) {
						d2 = d4;
					} else {
						d2 = d3 + (d4 - d3) * d5;
					}

					d2 = d2 - 8.0D;
					d2 = d2 + (double) f;
					int k1 = 2;

					if (j1 > p_185963_6_ / 2 - k1) {
						double d6 = (double) ((float) (j1 - (p_185963_6_ / 2 - k1)) / 64.0F);
						d6 = MathHelper.clamp_double(d6, 0.0D, 1.0D);
						d2 = d2 * (1.0D - d6) + -3000.0D * d6;
					}

					k1 = 8;

					if (j1 < k1) {
						double d7 = (double) ((float) (k1 - j1) / ((float) k1 - 1.0F));
						d2 = d2 * (1.0D - d7) + -30.0D * d7;
					}

					p_185963_1_[k] = d2;
					++k;
				}
			}
		}

		return p_185963_1_;
	}

	/**
	 * +
	 * Checks to see if a chunk exists at x, z
	 */
	public boolean chunkExists(int var1, int var2) {
		return true;
	}

	/**
	 * +
	 * Populates chunk with ores etc etc
	 */
	public void populate(IChunkProvider var1, int x, int z) {
		BlockFalling.fallInstantly = true;
        BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);

        if (this.mapFeaturesEnabled)
        {
            // this.endCityGen.generateStructure(this.worldObj, this.rand, new ChunkPos(x, z));
        }

        this.worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16)).decorate(this.worldObj, this.worldObj.rand, blockpos);
        long i = (long)x * (long)x + (long)z * (long)z;

        if (i > 4096L)
        {
            float f = this.getIslandHeightValue(x, z, 1, 1);

            if (f < -20.0F && this.rand.nextInt(14) == 0)
            {
                this.endIslands.generate(this.worldObj, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), this.rand.nextInt(16) + 8));

                if (this.rand.nextInt(4) == 0)
                {
                    this.endIslands.generate(this.worldObj, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), this.rand.nextInt(16) + 8));
                }
            }

            if (this.getIslandHeightValue(x, z, 1, 1) > 40.0F)
            {
                int j = this.rand.nextInt(5);

                for (int k = 0; k < j; ++k)
                {
                    int l = this.rand.nextInt(16) + 8;
                    int i1 = this.rand.nextInt(16) + 8;
                    int j1 = this.worldObj.getHeight(blockpos.add(l, 0, i1)).getY();

                    if (j1 > 0)
                    {
                        int k1 = j1 - 1;

                        if (this.worldObj.isAirBlock(blockpos.add(l, k1 + 1, i1)) && this.worldObj.getBlockState(blockpos.add(l, k1, i1)).getBlock() == Blocks.end_stone)
                        {
                            BlockChorusFlower.generatePlant(this.worldObj, blockpos.add(l, k1 + 1, i1), this.rand, 8);
                        }
                    }
                }
            }
        }

        BlockFalling.fallInstantly = false;
	}

	public boolean func_177460_a(IChunkProvider var1, Chunk var2, int var3, int var4) {
		return false;
	}

	/**
	 * +
	 * Two modes of operation: if passed true, save all Chunks in
	 * one go. If passed false, save up to two chunks. Return true
	 * if all chunks have been saved.
	 */
	public boolean saveChunks(boolean var1, IProgressUpdate var2) {
		return true;
	}

	/**
	 * +
	 * Save extra data not associated with any Chunk. Not saved
	 * during autosave, only during world unload. Currently
	 * unimplemented.
	 */
	public void saveExtraData() {
	}

	/**
	 * +
	 * Unloads chunks that are marked to be unloaded. This is not
	 * guaranteed to unload every such chunk.
	 */
	public boolean unloadQueuedChunks() {
		return false;
	}

	/**
	 * +
	 * Returns if the IChunkProvider supports saving.
	 */
	public boolean canSave() {
		return true;
	}

	/**
	 * +
	 * Converts the instance data to a readable string.
	 */
	public String makeString() {
		return "RandomLevelSource";
	}

	public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumcreaturetype,
			BlockPos blockpos) {
		return this.worldObj.getBiomeGenForCoords(blockpos).getSpawnableList(enumcreaturetype);
	}

	public BlockPos getStrongholdGen(World var1, String var2, BlockPos var3) {
		return null;
	}

	public int getLoadedChunkCount() {
		return 0;
	}

	public void recreateStructures(Chunk var1, int var2, int var3) {
	}

	/**
	 * +
	 * Will return back a chunk, if it doesn't exist and its not a
	 * MP client it will generates all the blocks for the specified
	 * chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk(BlockPos blockpos) {
		return this.provideChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4);
	}
}