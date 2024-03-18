package com.reimnop.player_plush.client;

import com.reimnop.player_plush.ItemRegistry;
import com.reimnop.player_plush.client.rendering.item.PlushItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class PlayerPlushClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register item renderer
        BuiltinItemRendererRegistry.INSTANCE.register(ItemRegistry.PLUSH, new PlushItemRenderer());
    }
}
