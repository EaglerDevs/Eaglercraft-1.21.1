package net.minecraft.util;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;

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

// HoosierTransfer mod

public class BlockPos extends Vec3i {
	/**
	 * +
	 * The BlockPos with all coordinates 0
	 */
	public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
	private static final int NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
	private static final int NUM_Z_BITS = NUM_X_BITS;
	private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
	private static final int Y_SHIFT = 0 + NUM_Z_BITS;
	private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
	private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
	private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
	private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;

	public BlockPos(int x, int y, int z) {
		super(x, y, z);
	}

	public BlockPos(double x, double y, double z) {
		super(x, y, z);
	}

	public BlockPos(Entity source) {
		this(source.posX, source.posY, source.posZ);
	}

	public BlockPos(Vec3 source) {
		this(source.xCoord, source.yCoord, source.zCoord);
	}

	public BlockPos(Vec3i source) {
		this(source.getX(), source.getY(), source.getZ());
	}

	/**
	 * +
	 * Add the given coordinates to the coordinates of this BlockPos
	 */
	public BlockPos add(double x, double y, double z) {
		return x == 0.0D && y == 0.0D && z == 0.0D ? this
				: new BlockPos((double) this.getX() + x, (double) this.getY() + y, (double) this.getZ() + z);
	}

	/**
	 * +
	 * Add the given coordinates to the coordinates of this BlockPos
	 */
	public BlockPos add(int x, int y, int z) {
		return x == 0 && y == 0 && z == 0 ? this : new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
	}

	/**
	 * +
	 * Add the given coordinates to the coordinates of this BlockPos
	 */
	public BlockPos add(Vec3i vec) {
		return vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0 ? this
				: new BlockPos(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
	}

	/**
	 * +
	 * Subtract the given Vector from this BlockPos
	 */
	public BlockPos subtract(Vec3i vec) {
		return vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0 ? this
				: new BlockPos(this.getX() - vec.getX(), this.getY() - vec.getY(), this.getZ() - vec.getZ());
	}

	/**
	 * +
	 * Offset this BlockPos 1 block up
	 */
	public BlockPos up() {
		return new BlockPos(this.getX(), this.getY() + 1, this.getZ());
	}

	/**
	 * eagler
	 */
	/**
	 * +
	 * Offset this BlockPos 1 block up
	 */
	public BlockPos up(BlockPos dst) {
		dst.x = x;
		dst.y = y + 1;
		dst.z = z;
		return dst;
	}

	/**
	 * +
	 * Offset this BlockPos 1 block up
	 */
	public BlockPos up(int offset) {
		return offset == 0 ? (BlockPos) (Object) this : new BlockPos(this.getX(), this.getY() + offset, this.getZ());
	}

	/**
	 * +
	 * Offset this BlockPos 1 block down
	 */
	public BlockPos down() {
		return new BlockPos(this.getX(), this.getY() - 1, this.getZ());
	}

	/**
	 * eagler
	 */
	/**
	 * +
	 * Offset this BlockPos 1 block down
	 */
	public BlockPos down(BlockPos dst) {
		dst.x = x;
		dst.y = y - 1;
		dst.z = z;
		return dst;
	}

	/**
	 * +
	 * Offset this BlockPos 1 block down
	 */
	public BlockPos down(int offset) {
		return offset == 0 ? (BlockPos) (Object) this : new BlockPos(this.getX(), this.getY() - offset, this.getZ());
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in northern direction
	 */
	public BlockPos north() {
		return new BlockPos(this.getX(), this.getY(), this.getZ() - 1);
	}

	/**
	 * eagler
	 */
	/**
	 * +
	 * Offset this BlockPos 1 block in northern direction
	 */
	public BlockPos north(BlockPos dst) {
		dst.x = x;
		dst.y = y;
		dst.z = z - 1;
		return dst;
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in northern direction
	 */
	public BlockPos north(int offset) {
		return offset == 0 ? (BlockPos) (Object) this : new BlockPos(this.getX(), this.getY(), this.getZ() - offset);
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in southern direction
	 */
	public BlockPos south() {
		return new BlockPos(this.getX(), this.getY(), this.getZ() + 1);
	}

	/**
	 * eagler
	 */
	/**
	 * +
	 * Offset this BlockPos 1 block in southern direction
	 */
	public BlockPos south(BlockPos dst) {
		dst.x = x;
		dst.y = y;
		dst.z = z + 1;
		return dst;
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in southern direction
	 */
	public BlockPos south(int offset) {
		return offset == 0 ? (BlockPos) (Object) this : new BlockPos(this.getX(), this.getY(), this.getZ() + offset);
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in western direction
	 */
	public BlockPos west() {
		return new BlockPos(this.getX() - 1, this.getY(), this.getZ());
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in western direction
	 */
	public BlockPos west(int offset) {
		return offset == 0 ? (BlockPos) (Object) this : new BlockPos(this.getX() - offset, this.getY(), this.getZ());
	}

	/**
	 * eagler
	 */
	/**
	 * +
	 * Offset this BlockPos 1 block in western direction
	 */
	public BlockPos west(BlockPos dst) {
		dst.x = x - 1;
		dst.y = y;
		dst.z = z;
		return dst;
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in eastern direction
	 */
	public BlockPos east() {
		return new BlockPos(this.getX() + 1, this.getY(), this.getZ());
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in eastern direction
	 */
	public BlockPos east(int offset) {
		return offset == 0 ? (BlockPos) (Object) this : new BlockPos(this.getX() + offset, this.getY(), this.getZ());
	}

	/**
	 * eagler
	 */
	/**
	 * +
	 * Offset this BlockPos 1 block in eastern direction
	 */
	public BlockPos east(BlockPos dst) {
		dst.x = x + 1;
		dst.y = y;
		dst.z = z;
		return dst;
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in the given direction
	 */
	public BlockPos offset(EnumFacing direction) {
		return new BlockPos(this.getX() + direction.getFrontOffsetX(), this.getY() + direction.getFrontOffsetY(),
				this.getZ() + direction.getFrontOffsetZ());
	}

	public BlockPos offsetFaster(EnumFacing facing, BlockPos ret) {
		ret.x = this.getX() + facing.getFrontOffsetX();
		ret.y = this.getY() + facing.getFrontOffsetY();
		ret.z = this.getZ() + facing.getFrontOffsetZ();
		return ret;
	}

	/**
	 * only use with a regular "net.minecraft.util.BlockPos"!
	 */
	public BlockPos offsetEvenFaster(EnumFacing facing, BlockPos ret) {
		ret.x = this.x + facing.getFrontOffsetX();
		ret.y = this.y + facing.getFrontOffsetY();
		ret.z = this.z + facing.getFrontOffsetZ();
		return ret;
	}

	/**
	 * +
	 * Offset this BlockPos 1 block in the given direction
	 */
	public BlockPos offset(EnumFacing facing, int n) {
		return n == 0 ? this
				: new BlockPos(this.x + facing.getFrontOffsetX() * n, this.y + facing.getFrontOffsetY() * n,
						this.z + facing.getFrontOffsetZ() * n);
	}

	/**
	 * +
	 * Calculate the cross product of this and the given Vector
	 */
	public BlockPos crossProduct(Vec3i vec3i) {
		return new BlockPos(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(),
				this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(),
				this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
	}

	/**
	 * +
	 * Serialize this BlockPos into a long value
	 */
	public long toLong() {
		return ((long) this.getX() & X_MASK) << X_SHIFT | ((long) this.getY() & Y_MASK) << Y_SHIFT
				| ((long) this.getZ() & Z_MASK) << 0;
	}

	/**
	 * +
	 * Create a BlockPos from a serialized long value (created by
	 * toLong)
	 */
	public static BlockPos fromLong(long serialized) {
		int i = (int) (serialized << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
		int j = (int) (serialized << 64 - Y_SHIFT - NUM_Y_BITS >> 64 - NUM_Y_BITS);
		int k = (int) (serialized << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);
		return new BlockPos(i, j, k);
	}

	public BlockPos toImmutable() {
		return this;
	}

	/**
	 * +
	 * Create an Iterable that returns all positions in the box
	 * specified by the given corners
	 */
	public static Iterable<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
		final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()),
				Math.min(from.getZ(), to.getZ()));
		final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()),
				Math.max(from.getZ(), to.getZ()));
		return new Iterable<BlockPos>() {
			public Iterator<BlockPos> iterator() {
				return new AbstractIterator<BlockPos>() {
					private BlockPos lastReturned = null;

					protected BlockPos computeNext() {
						if (this.lastReturned == null) {
							this.lastReturned = blockpos;
							return this.lastReturned;
						} else if (this.lastReturned.equals(blockpos1)) {
							return (BlockPos) this.endOfData();
						} else {
							int i = this.lastReturned.getX();
							int j = this.lastReturned.getY();
							int k = this.lastReturned.getZ();
							if (i < blockpos1.getX()) {
								++i;
							} else if (j < blockpos1.getY()) {
								i = blockpos.getX();
								++j;
							} else if (k < blockpos1.getZ()) {
								i = blockpos.getX();
								j = blockpos.getY();
								++k;
							}

							this.lastReturned = new BlockPos(i, j, k);
							return this.lastReturned;
						}
					}
				};
			}
		};
	}

	/**
	 * +
	 * Like getAllInBox but reuses a single MutableBlockPos instead.
	 * If this method is used, the resulting BlockPos instances can
	 * only be used inside the iteration loop.
	 */
	public static Iterable<BlockPos.MutableBlockPos> getAllInBoxMutable(BlockPos from, BlockPos to) {
		final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()),
				Math.min(from.getZ(), to.getZ()));
		final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()),
				Math.max(from.getZ(), to.getZ()));
		return new Iterable<BlockPos.MutableBlockPos>() {
			public Iterator<BlockPos.MutableBlockPos> iterator() {
				return new AbstractIterator<BlockPos.MutableBlockPos>() {
					private BlockPos.MutableBlockPos theBlockPos = null;

					protected BlockPos.MutableBlockPos computeNext() {
						if (this.theBlockPos == null) {
							this.theBlockPos = new BlockPos.MutableBlockPos(blockpos.getX(), blockpos.getY(),
									blockpos.getZ());
							return this.theBlockPos;
						} else if (this.theBlockPos.equals(blockpos1)) {
							return (BlockPos.MutableBlockPos) this.endOfData();
						} else {
							int i = this.theBlockPos.getX();
							int j = this.theBlockPos.getY();
							int k = this.theBlockPos.getZ();
							if (i < blockpos1.getX()) {
								++i;
							} else if (j < blockpos1.getY()) {
								i = blockpos.getX();
								++j;
							} else if (k < blockpos1.getZ()) {
								i = blockpos.getX();
								j = blockpos.getY();
								++k;
							}

							this.theBlockPos.x = i;
							this.theBlockPos.y = j;
							this.theBlockPos.z = k;
							return this.theBlockPos;
						}
					}
				};
			}
		};
	}

	public static class MutableBlockPos extends BlockPos {

		public MutableBlockPos() {
			this(0, 0, 0);
		}

		public MutableBlockPos(int x_, int y_, int z_) {
			super(x_, y_, z_);
		}

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		public int getZ() {
			return this.z;
		}

		public BlockPos.MutableBlockPos setPos(Entity entityIn) {
			return this.setPos(entityIn.posX, entityIn.posY, entityIn.posZ);
		}

		public BlockPos.MutableBlockPos setPos(double xIn, double yIn, double zIn) {
			return this.setPos(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn),
					MathHelper.floor_double(zIn));
		}

		public BlockPos.MutableBlockPos setPos(Vec3i vec) {
			return this.setPos(vec.getX(), vec.getY(), vec.getZ());
		}

		public BlockPos.MutableBlockPos move(EnumFacing facing) {
			return this.move(facing, 1);
		}

		public BlockPos.MutableBlockPos move(EnumFacing facing, int p_189534_2_) {
			return this.setPos(this.x + facing.getFrontOffsetX() * p_189534_2_,
					this.y + facing.getFrontOffsetY() * p_189534_2_, this.z + facing.getFrontOffsetZ() * p_189534_2_);
		}

		public void setY(int yIn) {
			this.y = yIn;
		}

		public BlockPos.MutableBlockPos setPos(int x_, int y_, int z_) {
			this.x = x_;
			this.y = y_;
			this.z = z_;
			return this;
		}

		public BlockPos.MutableBlockPos func_181079_c(int parInt1, int parInt2, int parInt3) {
			this.x = parInt1;
			this.y = parInt2;
			this.z = parInt3;
			return this;
		}

		public BlockPos toImmutable() {
			return new BlockPos(this);
		}
	}

	public static final class PooledMutableBlockPos extends BlockPos.MutableBlockPos {
		private boolean released;
		private static final List<BlockPos.PooledMutableBlockPos> POOL = Lists.<BlockPos.PooledMutableBlockPos>newArrayList();

		private PooledMutableBlockPos(int xIn, int yIn, int zIn) {
			super(xIn, yIn, zIn);
		}

		public static BlockPos.PooledMutableBlockPos retain() {
			return retain(0, 0, 0);
		}

		public static BlockPos.PooledMutableBlockPos retain(double xIn, double yIn, double zIn) {
			return retain(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
		}

		public static BlockPos.PooledMutableBlockPos retain(Vec3i vec) {
			return retain(vec.getX(), vec.getY(), vec.getZ());
		}

		public static BlockPos.PooledMutableBlockPos retain(int xIn, int yIn, int zIn) {
			synchronized (POOL) {
				if (!POOL.isEmpty()) {
					BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = (BlockPos.PooledMutableBlockPos) POOL
							.remove(POOL.size() - 1);

					if (blockpos$pooledmutableblockpos != null && blockpos$pooledmutableblockpos.released) {
						blockpos$pooledmutableblockpos.released = false;
						blockpos$pooledmutableblockpos.setPos(xIn, yIn, zIn);
						return blockpos$pooledmutableblockpos;
					}
				}
			}

			return new BlockPos.PooledMutableBlockPos(xIn, yIn, zIn);
		}

		public void release() {
			synchronized (POOL) {
				if (POOL.size() < 100) {
					POOL.add(this);
				}

				this.released = true;
			}
		}

		public BlockPos.PooledMutableBlockPos setPos(int xIn, int yIn, int zIn) {
			if (this.released) {
				this.released = false;
			}

			return (BlockPos.PooledMutableBlockPos) super.setPos(xIn, yIn, zIn);
		}

		public BlockPos.PooledMutableBlockPos setPos(Entity entityIn) {
			return (BlockPos.PooledMutableBlockPos) super.setPos(entityIn);
		}

		public BlockPos.PooledMutableBlockPos setPos(double xIn, double yIn, double zIn) {
			return (BlockPos.PooledMutableBlockPos) super.setPos(xIn, yIn, zIn);
		}

		public BlockPos.PooledMutableBlockPos setPos(Vec3i vec) {
			return (BlockPos.PooledMutableBlockPos) super.setPos(vec);
		}

		public BlockPos.PooledMutableBlockPos move(EnumFacing facing) {
			return (BlockPos.PooledMutableBlockPos) super.move(facing);
		}

		public BlockPos.PooledMutableBlockPos move(EnumFacing facing, int p_189534_2_) {
			return (BlockPos.PooledMutableBlockPos) super.move(facing, p_189534_2_);
		}
	}
}