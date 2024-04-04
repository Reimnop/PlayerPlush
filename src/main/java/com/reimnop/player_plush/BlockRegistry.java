package com.reimnop.player_plush;

import com.reimnop.player_plush.block.PlushBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockRegistry {
    public static final PlushBlock PLUSH_BLOCK = new PlushBlock(BlockBehaviour.Properties.of()
            .sound(SoundType.WOOL));

    public static void register() {
        // Register blocks
        Registry.register(BuiltInRegistries.BLOCK, PlayerPlush.id("plush"), PLUSH_BLOCK);
    }
}
