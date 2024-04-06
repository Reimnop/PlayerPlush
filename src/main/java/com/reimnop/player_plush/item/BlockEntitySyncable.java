package com.reimnop.player_plush.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface BlockEntitySyncable {
    void syncFromBlockEntity(ItemStack stack, BlockEntity blockEntity);
}
