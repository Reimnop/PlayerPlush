package com.reimnop.player_plush.client;

import com.reimnop.player_plush.ItemRegistry;
import com.reimnop.player_plush.client.rendering.item.PlushItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;

@Environment(EnvType.CLIENT)
public class PlayerPlushClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register item renderer
        BuiltinItemRendererRegistry.INSTANCE.register(ItemRegistry.PLUSH, new PlushItemRenderer());
    }
}
