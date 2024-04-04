package com.reimnop.player_plush;

import com.reimnop.player_plush.block_entity.PlushBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry {
    public static final BlockEntityType<PlushBlockEntity> PLUSH_BLOCK_ENTITY = BlockEntityType.Builder
            .of(PlushBlockEntity::new, BlockRegistry.PLUSH_BLOCK)
            .build(null);

    public static void register() {
        // Register block entities
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, PlayerPlush.id("plush"), PLUSH_BLOCK_ENTITY);
    }
}
