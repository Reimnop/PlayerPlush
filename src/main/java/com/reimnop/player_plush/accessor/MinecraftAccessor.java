package com.reimnop.player_plush.accessor;

import net.minecraft.server.Services;
import org.jetbrains.annotations.Nullable;

public interface MinecraftAccessor {
    @Nullable
    Services playerPlush$getServices();
}
