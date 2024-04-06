package com.reimnop.player_plush.item;

import com.reimnop.player_plush.util.PlushNbtHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class PlushItem extends BlockItem implements BlockEntitySyncable {
    public PlushItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @NotNull
    public Component getName(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            String ownerName = PlushNbtHelper.getOwnerName(tag);
            if (ownerName != null) {
                return Component.translatable(getDescriptionId(stack) + ".named", ownerName);
            }
        }
        return super.getName(stack);
    }

    @Override
    public void syncFromBlockEntity(ItemStack stack, BlockEntity blockEntity) {
        CompoundTag tag = blockEntity.saveWithFullMetadata();
        CompoundTag stackTag = stack.getOrCreateTag();
        if (tag.contains("Owner", Tag.TAG_COMPOUND)) {
            stackTag.put("Owner", tag.getCompound("Owner"));
        }
        if (tag.contains("Owner", Tag.TAG_STRING)) {
            stackTag.putString("Owner", tag.getString("Owner"));
        }
        if (tag.contains("Color", Tag.TAG_INT)) {
            stackTag.putInt("Color", tag.getInt("Color"));
        }
    }
}
