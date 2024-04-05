package com.reimnop.player_plush.item;

import com.reimnop.player_plush.util.PlushNbtHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PlushItem extends BlockItem {
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
}
