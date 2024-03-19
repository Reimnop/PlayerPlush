package com.reimnop.player_plush.client.rendering.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public record PlushBodyPartPose(float x, float y, float z, float pitch, float yaw, float roll) {
    public static final PlushBodyPartPose DEFAULT_ROOT = new PlushBodyPartPose(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    public static final PlushBodyPartPose DEFAULT_HEAD = new PlushBodyPartPose(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    public static final PlushBodyPartPose DEFAULT_BODY = new PlushBodyPartPose(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    public static final PlushBodyPartPose DEFAULT_RIGHT_ARM = new PlushBodyPartPose(-5.0F, 2.5F, 0.0F, 0.0F, 0.0F, 0.0F);
    public static final PlushBodyPartPose DEFAULT_LEFT_ARM = new PlushBodyPartPose(5.0F, 2.5F, 0.0F, 0.0F, 0.0F, 0.0F);
    public static final PlushBodyPartPose DEFAULT_RIGHT_LEG = new PlushBodyPartPose(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    public static final PlushBodyPartPose DEFAULT_LEFT_LEG = new PlushBodyPartPose(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F);

    public PlushBodyPartPose withX(float x) {
        return new PlushBodyPartPose(x, y, z, pitch, yaw, roll);
    }

    public PlushBodyPartPose withY(float y) {
        return new PlushBodyPartPose(x, y, z, pitch, yaw, roll);
    }

    public PlushBodyPartPose withZ(float z) {
        return new PlushBodyPartPose(x, y, z, pitch, yaw, roll);
    }

    public PlushBodyPartPose withPitch(float pitch) {
        return new PlushBodyPartPose(x, y, z, pitch, yaw, roll);
    }

    public PlushBodyPartPose withYaw(float yaw) {
        return new PlushBodyPartPose(x, y, z, pitch, yaw, roll);
    }

    public PlushBodyPartPose withRoll(float roll) {
        return new PlushBodyPartPose(x, y, z, pitch, yaw, roll);
    }
}
