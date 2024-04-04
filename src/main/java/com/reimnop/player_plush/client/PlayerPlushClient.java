package com.reimnop.player_plush.client;

import com.reimnop.player_plush.BlockEntityRegistry;
import com.reimnop.player_plush.ItemRegistry;
import com.reimnop.player_plush.client.rendering.item.PlushItemRenderer;
import com.reimnop.player_plush.event.ServicesLifecycleCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import com.reimnop.player_plush.client.rendering.block_entity.PlushBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class PlayerPlushClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register block entity renderer
        BlockEntityRenderers.register(BlockEntityRegistry.PLUSH_BLOCK_ENTITY, PlushBlockEntityRenderer::new);

        // Register item renderer
        BuiltinItemRendererRegistry.INSTANCE.register(ItemRegistry.PLUSH, new PlushItemRenderer());

        // Subscribe to events
        ServicesLifecycleCallback.INIT.register(FetchedGameProfileCache::initialize);
        ServicesLifecycleCallback.DEINIT.register(services -> FetchedGameProfileCache.clear());
    }
}
