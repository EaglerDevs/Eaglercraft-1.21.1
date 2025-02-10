package net.eaglerdevs.modshoosiertransfer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;

public class Config {
    public static int biomeBlendRadius = 2;
    public static boolean fancyLeaves = false;

    public static boolean enableCulling = true;
    public static boolean renderNameTagsThroguthWalls = true;
    public static boolean skipMarkerArmorStands = true;
    public static int tracingDistance = 128;
    public static Set<String> blockEntityWhitelist = new HashSet<>(Arrays.asList("minecraft:beacon"));
    public static int SleepDuration = 10;
    public static int hitboxLimit = 50;

    public static boolean disableAlpha() {
        return Minecraft.getMinecraft().gameSettings.disableAlpha && !Minecraft.getMinecraft().gameSettings.shaders;
    }

    public static boolean audioEnabled() {
        return Minecraft.getMinecraft().gameSettings.enableSound;
    }

    public static boolean animateTick() {
        return false;
    }

    public static boolean renderParticles() {
        return true;
    }
}
