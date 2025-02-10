package net.minecraft.world.biome;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import java.util.Collections;
import java.util.List;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;

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
public class BiomeEndDecorator extends BiomeDecorator {

	protected WorldGenSpikes spikeGen = new WorldGenSpikes();

	protected void genDecorations(BiomeGenBase var1) {
        this.generateOres();
        WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = getSpikesForWorld(this.currentWorld);

        for (WorldGenSpikes.EndSpike worldgenspikes$endspike : aworldgenspikes$endspike)
        {
            if (worldgenspikes$endspike.doesStartInChunk(this.field_180294_c))
            {
                this.spikeGen.setSpike(worldgenspikes$endspike);
                this.spikeGen.generate(this.currentWorld, this.randomGenerator, new BlockPos(worldgenspikes$endspike.getCenterX(), 45, worldgenspikes$endspike.getCenterZ()));
            }
        }

	}

	public static WorldGenSpikes.EndSpike[] getSpikesForWorld(World world)
    {
        EaglercraftRandom random = new EaglercraftRandom(world.getSeed());
        long seed = random.nextLong() & 65535L;
        return generateSpikes(seed);
    }

    public static WorldGenSpikes.EndSpike[] generateSpikes(long seed)
    {
        List<Integer> list = Lists.newArrayList(ContiguousSet.create(Range.closedOpen(0, 10), DiscreteDomain.integers()));
        Collections.shuffle(list);
        WorldGenSpikes.EndSpike[] spikes = new WorldGenSpikes.EndSpike[10];

        for (int i = 0; i < 10; ++i)
        {
            int x = (int)(42.0D * Math.cos(2.0D * (-Math.PI + (Math.PI / 10D) * i)));
            int z = (int)(42.0D * Math.sin(2.0D * (-Math.PI + (Math.PI / 10D) * i)));
            int index = list.get(i);
            int radius = 2 + index / 3;
            int height = 76 + index * 3;
            boolean isGuarded = index == 1 || index == 2;
            spikes[i] = new WorldGenSpikes.EndSpike(x, z, radius, height, isGuarded);
        }

        return spikes;
    }
}