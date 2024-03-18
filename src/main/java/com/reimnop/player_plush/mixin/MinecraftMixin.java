package com.reimnop.player_plush.mixin;

import com.reimnop.player_plush.accessor.MinecraftAccessor;
import com.reimnop.player_plush.event.ServicesLifecycleCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.time.Instant;

@Mixin(Minecraft.class)
public class MinecraftMixin implements MinecraftAccessor {
    @Unique
    @Nullable
    private Services services;

    @Nullable
    public Services playerPlush$getServices() {
        return services;
    }

    @Inject(method = "doWorldLoad",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/server/players/GameProfileCache;setExecutor(Ljava/util/concurrent/Executor;)V",
                     shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void doWorldLoadInjectLevelStorageSource(
            LevelStorageSource.LevelStorageAccess levelStorage,
            PackRepository packRepository,
            WorldStem worldStem,
            boolean newWorld,
            CallbackInfo ci,
            Instant instant,
            Services services) {
        this.services = services;
        ServicesLifecycleCallback.INIT.invoker().onServicesLifecycle(services);
    }

    @Inject(method = "setLevel",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/server/players/GameProfileCache;setExecutor(Ljava/util/concurrent/Executor;)V",
                     shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void setLevelInject(
            ClientLevel levelClient,
            CallbackInfo ci,
            ProgressScreen progressScreen,
            Services services) {
        this.services = services;
        ServicesLifecycleCallback.INIT.invoker().onServicesLifecycle(services);
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At("TAIL"))
    public void disconnectInject(Screen nextScreen, CallbackInfo ci) {
        ServicesLifecycleCallback.DEINIT.invoker().onServicesLifecycle(services);
        services = null;
    }

    @Inject(method = "clearClientLevel", at = @At("TAIL"))
    public void clearClientLevelInject(Screen nextScreen, CallbackInfo ci) {
        ServicesLifecycleCallback.DEINIT.invoker().onServicesLifecycle(services);
        services = null;
    }
}
