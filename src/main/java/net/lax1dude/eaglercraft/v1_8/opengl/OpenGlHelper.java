package net.lax1dude.eaglercraft.v1_8.opengl;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * Copyright (c) 2022 lax1dude. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class OpenGlHelper {

	public static final int defaultTexUnit = RealOpenGLEnums.GL_TEXTURE0;

	public static final int lightmapTexUnit = RealOpenGLEnums.GL_TEXTURE1;

	public static void setLightmapTextureCoords(int unit, float x, float y) {
		GlStateManager.setActiveTexture(lightmapTexUnit);
		GlStateManager.texCoords2D(x, y);
		GlStateManager.setActiveTexture(defaultTexUnit);
	}

	public static void renderDirections(int p_188785_0_)
    {
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        EaglercraftGPU.glLineWidth(2.0F);
        vertexbuffer.pos(0.0D, 0.0D, 0.0D).color(255, 0, 0, 255).endVertex();
        vertexbuffer.pos((double)p_188785_0_, 0.0D, 0.0D).color(255, 0, 0, 255).endVertex();
        vertexbuffer.pos(0.0D, 0.0D, 0.0D).color(0, 255, 0, 255).endVertex();
        vertexbuffer.pos(0.0D, (double)p_188785_0_, 0.0D).color(0, 255, 0, 255).endVertex();
        vertexbuffer.pos(0.0D, 0.0D, 0.0D).color(0, 0, 255, 255).endVertex();
        vertexbuffer.pos(0.0D, 0.0D, (double)p_188785_0_).color(0, 0, 255, 255).endVertex();
        tessellator.draw();
        EaglercraftGPU.glLineWidth(1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
    }
}
