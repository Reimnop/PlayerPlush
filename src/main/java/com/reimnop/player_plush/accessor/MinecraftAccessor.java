package com.reimnop.player_plush.accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.Services;
import org.jetbrains.annotations.Nullable;

public interface MinecraftAccessor {
    @Nullable
    Services playerPlush$getServices();
}
