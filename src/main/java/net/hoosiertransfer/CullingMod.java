package net.eaglerdevs.modshoosiertransfer;

import com.logisticscraft.occlusionculling.OcclusionCullingInstance;

import net.eaglerdevs.modsCulling.CullTask;
import net.eaglerdevs.modsCulling.Provider;

public class CullingMod {
    public static OcclusionCullingInstance culling;
    public static CullTask cullTask;
    private static Thread cullThread;

    public static int skippedBlockEntities = 0;
    public static int renderedBlockEntities = 0;
    public static int skippedEntities = 0;
    public static int renderedEntities = 0;

    public static void intialize() {
        culling = new OcclusionCullingInstance(Config.tracingDistance, new Provider());
        cullTask = new CullTask(culling, Config.blockEntityWhitelist);

        cullThread = new Thread(cullTask, "CullThread");

        cullThread.setUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Error in culling thread");
            throwable.printStackTrace();
        });
        cullThread.start();
    }

    public static void setRequestCull(boolean cull) {
        cullTask.requestCull = cull;
    }
}