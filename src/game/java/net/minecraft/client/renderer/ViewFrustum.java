package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
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
public class ViewFrustum {
    protected final RenderGlobal renderGlobal;
    protected final World world;
    protected int countChunksY;
    protected int countChunksX;
    protected int countChunksZ;
    public RenderChunk[] renderChunks;

    public ViewFrustum(World worldIn, int renderDistanceChunks, RenderGlobal parRenderGlobal,
            IRenderChunkFactory renderChunkFactory) {
        this.renderGlobal = parRenderGlobal;
        this.world = worldIn;
        this.setCountChunksXYZ(renderDistanceChunks);
        this.createRenderChunks(renderChunkFactory);
    }

    /**
     * @reason Improving the performance of this method by using a single loop
     *         instead of multiple nested ones and avoiding allocating in loop.
     *         Improving the performance of this method is beneficial as it reduces
     *         lag when loading renderer.
     *         For example, when loading in a world, changing the render distance,
     *         changing graphics quality, etc.
     * @author Desoroxxx
     */
    protected void createRenderChunks(IRenderChunkFactory renderChunkFactory) {
        final int totalRenderChunks = countChunksX * countChunksY * countChunksZ;

        int xChunkIndex = 0;
        int yChunkIndex = 0;
        int zChunkIndex = 0;

        renderChunks = new RenderChunk[totalRenderChunks];

        for (int i = 0; i < totalRenderChunks; ++i) {
            if (xChunkIndex == countChunksX) {
                xChunkIndex = 0;
                ++yChunkIndex;
                if (yChunkIndex == countChunksY) {
                    yChunkIndex = 0;
                    ++zChunkIndex;
                }
            }

            renderChunks[i] = renderChunkFactory.makeRenderChunk(world, renderGlobal,
                    new BlockPos(xChunkIndex * 16, yChunkIndex * 16, zChunkIndex * 16), i);
            renderChunks[i].setPosition(new BlockPos(xChunkIndex * 16, yChunkIndex * 16, zChunkIndex * 16));

            ++xChunkIndex;
        }
    }

    public void deleteGlResources() {
        for (int i = 0; i < this.renderChunks.length; ++i) {
            renderChunks[i].deleteGlResources();
        }

    }

    protected void setCountChunksXYZ(int renderDistanceChunks) {
        int i = renderDistanceChunks * 2 + 1;
        this.countChunksX = i;
        this.countChunksY = 16;
        this.countChunksZ = i;
    }

    public void updateChunkPositions(double viewEntityX, double viewEntityZ) {
        int i = MathHelper.floor_double(viewEntityX) - 8;
        int j = MathHelper.floor_double(viewEntityZ) - 8;
        int k = this.countChunksX * 16;

        for (int l = 0; l < this.countChunksX; ++l) {
            int i1 = this.func_178157_a(i, k, l);

            for (int j1 = 0; j1 < this.countChunksZ; ++j1) {
                int k1 = this.func_178157_a(j, k, j1);

                for (int l1 = 0; l1 < this.countChunksY; ++l1) {
                    int i2 = l1 << 4;
                    RenderChunk renderchunk = this.renderChunks[(j1 * this.countChunksY + l1) * this.countChunksX + l];
                    BlockPos blockpos = new BlockPos(i1, i2, k1);
                    if (!blockpos.equals(renderchunk.getPosition())) {
                        renderchunk.setPosition(blockpos);
                    }
                }
            }
        }

    }

    private int func_178157_a(int base, int renderDistance, int chunkIndex) {
        final int coordinate = chunkIndex << 4;
        int offset = coordinate - base + (renderDistance >> 1);

        if (offset < 0)
            offset -= renderDistance - 1;

        return coordinate - offset / renderDistance * renderDistance;
    }

    public void markBlocksForUpdate(final int minX, final int minY, final int minZ, final int maxX, final int maxY,
            final int maxZ) {
        final int chunkMinX = minX >> 4;
        final int chunkMinY = minY >> 4;
        final int chunkMinZ = minZ >> 4;
        final int chunkMaxX = maxX >> 4;
        final int chunkMaxY = maxY >> 4;
        final int chunkMaxZ = maxZ >> 4;

        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            final int normalizedX = (x % countChunksX + countChunksX) % countChunksX;

            for (int y = chunkMinY; y <= chunkMaxY; ++y) {
                final int normalizedY = (y % countChunksY + countChunksY) % countChunksY;

                for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                    final int normalizedZ = (z % countChunksZ + countChunksZ) % countChunksZ;

                    renderChunks[(normalizedZ * countChunksY + normalizedY) * countChunksX + normalizedX]
                            .setNeedsUpdate(true);
                }
            }
        }
    }

    public RenderChunk getRenderChunk(final BlockPos blockPos) {
        int x = blockPos.getX() >> 4;
        final int y = blockPos.getY() >> 4;
        int z = blockPos.getZ() >> 4;

        if (y >= 0 && y < countChunksY) {
            x = (x % countChunksX + countChunksX) % countChunksX;
            z = (z % countChunksZ + countChunksZ) % countChunksZ;

            return renderChunks[(z * countChunksY + y) * countChunksX + x];
        } else
            return null;
    }
}