package net.eaglerdevs.modsCulling;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

import com.logisticscraft.occlusionculling.OcclusionCullingInstance;
import com.logisticscraft.occlusionculling.util.Vec3d;

import net.eaglerdevs.modsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class CullTask implements Runnable {
    public boolean requestCull = false;
    
    private final OcclusionCullingInstance culling;
    private final Set<String> unCullable;
    private final Minecraft client = Minecraft.getMinecraft();

    private final int hitboxLimit = Config.hitboxLimit;

    public long lastTime = 0;

    private Vec3d lastPos = new Vec3d(0, 0, 0);
    private Vec3d aabbMin = new Vec3d(0, 0, 0);
	private Vec3d aabbMax = new Vec3d(0, 0, 0);

    public CullTask(OcclusionCullingInstance culling, Set<String> unCullable) {
        this.culling = culling;
        this.unCullable = unCullable;
    }

    @Override
    public void run() {
        // while (client != null) {
        //     try {
        //         Thread.sleep(Config.SleepDuration);

        //         if (Config.enableCulling && client.theWorld != null && client.thePlayer != null && client.thePlayer.ticksExisted > 0 && client.getRenderViewEntity() != null) {
        //             Vec3 cameraMC = getCameraPos();
        //             if (requestCull || !(cameraMC.xCoord == lastPos.x && cameraMC.yCoord == lastPos.y && cameraMC.zCoord == lastPos.z)) {
        //                 long start = System.currentTimeMillis();
        //                 requestCull = false;
        //                 lastPos.set(cameraMC.xCoord, cameraMC.yCoord, cameraMC.zCoord);
        //                 Vec3d camera = lastPos;
        //                 culling.resetCache();
        //                 boolean noCulling = client.thePlayer.isSpectator() || client.gameSettings.thirdPersonView != 0;
        //                 Iterator<TileEntity> iterator = client.theWorld.loadedTileEntityList.iterator();
        //                 TileEntity entry;
        //                 while(iterator.hasNext()) {
        //                     try {
        //                         entry = iterator.next();
        //                     } catch(NullPointerException | ConcurrentModificationException ex) {
		// 						break; // We are not synced to the main thread, so NPE's/CME are allowed here and way less
		// 						// overhead probably than trying to sync stuff up for no really good reason
		// 					}
        //                     if (unCullable.contains(entry.getBlockType().getUnlocalizedName())) {
        //                         continue;
        //                     }
        //                     if (!entry.isForcedVisible()) {
        //                         if (noCulling) {
        //                             entry.setCulled(true);
        //                             continue;
        //                         }
        //                         BlockPos pos = entry.getPos();
        //                         if(pos.distanceSq(cameraMC.xCoord, cameraMC.yCoord, cameraMC.zCoord) < 64*64) { // 64 is the fixed max tile view distance
		// 						    aabbMin.set(pos.getX(), pos.getY(), pos.getZ());
		// 						    aabbMax.set(pos.getX()+1d, pos.getY()+1d, pos.getZ()+1d);
		// 							boolean visible = culling.isAABBVisible(aabbMin, aabbMax, camera);
		// 							entry.setCulled(!visible);
		// 						}
        //                     }
        //                 }
        //                 Entity entity = null;
        //                 Iterator<Entity> iterable = client.theWorld.getLoadedEntityList().iterator();
        //                 while(iterable.hasNext()) {
        //                     try {
        //                         entity = iterable.next();
        //                     } catch(NullPointerException | ConcurrentModificationException ex) {
        //                         break; // We are not synced to the main thread, so NPE's/CME are allowed here and way less
        //                         // overhead probably than trying to sync stuff up for no really good reason
        //                     }
        //                     if (entity == null) {
        //                         continue;
        //                     }
        //                     if (!entity.isForcedVisible()) {
        //                         if (noCulling || isSkippableArmorstand(entity)) {
        //                             entity.setCulled(false);
        //                             continue;
        //                         }
        //                         if(entity.getPositionVector().squareDistanceTo(cameraMC) > Config.tracingDistance * Config.tracingDistance) {
		// 					        entity.setCulled(false); // If your entity view distance is larger than tracingDistance just render it
		// 					        continue;
		// 					    }
        //                         AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
        //                         if(boundingBox.maxX - boundingBox.minX > hitboxLimit || boundingBox.maxY - boundingBox.minY > hitboxLimit || boundingBox.maxZ - boundingBox.minZ > hitboxLimit) {
		// 						    entity.setCulled(false); // To big to bother to cull
		// 						    continue;
		// 						}
		// 					    aabbMin.set(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
		// 					    aabbMax.set(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
		// 						boolean visible = culling.isAABBVisible(aabbMin, aabbMax, camera);
		// 						entity.setCulled(!visible);
        //                     }
        //                 }
        //                 lastTime = (System.currentTimeMillis()-start);
        //             }
        //         }
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
        // System.out.println("Culling thread stopped");
    }

    private Vec3 getCameraPos() {
        if (client.gameSettings.thirdPersonView == 0) {
            return client.thePlayer.getPositionEyes(0);
        }
        return client.getRenderViewEntity().getPositionEyes(0);
    }

    private boolean isSkippableArmorstand(Entity entity) {
        if (!Config.skipMarkerArmorStands) return false;
        return entity instanceof EntityArmorStand && ((EntityArmorStand) entity).func_181026_s(); // i think this is the marker flag
    }
}
