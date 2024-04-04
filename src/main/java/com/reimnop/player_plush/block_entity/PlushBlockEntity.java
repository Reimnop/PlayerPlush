package com.reimnop.player_plush.block_entity;

import com.reimnop.player_plush.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PlushBlockEntity extends BlockEntity {
    public PlushBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.PLUSH_BLOCK_ENTITY, pos, blockState);
    }

    // public static void tick(Level level1, BlockPos blockPos, BlockState blockState, PlushBlockEntity plushBlockEntity) {
    //     // Do something
    // }
}
