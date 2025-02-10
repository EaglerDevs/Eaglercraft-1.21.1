package net.eaglerdevs.modshoosiertransfer;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import net.lax1dude.eaglercraft.v1_8.internal.PlatformAssets;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.sp.SingleplayerServerController;
import net.lax1dude.eaglercraft.v1_8.sp.ipc.IPCPacket1DUploadServerAsset;

public class ServerAssets {
    public static final Logger logger = LogManager.getLogger("ServerAssets");
    private static Map<String,byte[]> serverAssets = new HashMap<>();
    private static List<String> filenamesForClient = new ArrayList<>();

    // ! I might want to call initialize when the server starts up instead of when the client starts up
    public static void initialize() {
        filenamesForClient = PlatformAssets.listFilesAtPath("assets/minecraft/server");
    }

    public static void sendAssetsToServer() {
        for (String path : filenamesForClient) {
            byte[] data = PlatformAssets.getResourceBytes(path);
            if (data != null) {
                logger.info("Uploading asset: " + path);
                SingleplayerServerController.sendIPCPacket(new IPCPacket1DUploadServerAsset(path, data));
            }
        }
    }

    public static void uploadAsset(String path, byte[] data) {
        serverAssets.put(path, data);
        logger.info("Uploaded asset: " + path);
    }

    public static byte[] getAssetBytes(String path) {
        return serverAssets.get(path);
    }

    public static InputStream getAssetStream(String path) {
        byte[] data = serverAssets.get(path);
        if (data != null) {
            return new ByteArrayInputStream(data);
        } else {
            return null;
        }
    }
}
