package com.reimnop.player_plush.client.rendering.model;

import com.reimnop.player_plush.util.MathUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public interface PlushPose {
    PlushPose DEFAULT = part -> switch (part) {
        case ROOT -> PlushBodyPartPose.DEFAULT_ROOT;
        case HEAD -> PlushBodyPartPose.DEFAULT_HEAD;
        case BODY -> PlushBodyPartPose.DEFAULT_BODY;
        case RIGHT_ARM -> PlushBodyPartPose.DEFAULT_RIGHT_ARM;
        case LEFT_ARM -> PlushBodyPartPose.DEFAULT_LEFT_ARM;
        case RIGHT_LEG -> PlushBodyPartPose.DEFAULT_RIGHT_LEG;
        case LEFT_LEG -> PlushBodyPartPose.DEFAULT_LEFT_LEG;
    };

    PlushPose SITTING = part -> switch (part) {
        case RIGHT_ARM -> PlushBodyPartPose.DEFAULT_RIGHT_ARM
                .withPitch(-40.0F * MathUtil.DEG_2_RAD);
        case LEFT_ARM -> PlushBodyPartPose.DEFAULT_LEFT_ARM
                .withPitch(-40.0F * MathUtil.DEG_2_RAD);
        case RIGHT_LEG -> PlushBodyPartPose.DEFAULT_RIGHT_LEG
                .withYaw(20.0F * MathUtil.DEG_2_RAD)
                .withPitch(-85.0F * MathUtil.DEG_2_RAD);
        case LEFT_LEG -> PlushBodyPartPose.DEFAULT_LEFT_LEG
                .withYaw(-20.0F * MathUtil.DEG_2_RAD)
                .withPitch(-85.0F * MathUtil.DEG_2_RAD);
        default -> null;
    };

    @Nullable
    PlushBodyPartPose getPose(PlushBodyPart part);
}
