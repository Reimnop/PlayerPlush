package com.reimnop.player_plush;

import com.reimnop.player_plush.item.PlushItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public final class ItemRegistry {
    public static final Item PLUSH = new PlushItem(BlockRegistry.PLUSH_BLOCK, new Item.Properties());

    public static void register() {
        // Register items
        Registry.register(BuiltInRegistries.ITEM, PlayerPlush.id("plush"), PLUSH);
    }
}
