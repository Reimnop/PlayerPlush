package com.reimnop.player_plush.item;

import com.mojang.authlib.GameProfile;
import com.reimnop.player_plush.client.FetchedGameProfileCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlushItem extends BlockItem {
    public PlushItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @NotNull
    public Component getName(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            String ownerName = getOwnerName(tag);
            if (ownerName != null) {
                return Component.translatable(getDescriptionId(stack) + ".named", ownerName);
            }
        }
        return super.getName(stack);
    }

    @Nullable
    public static GameProfile getProfile(CompoundTag tag) {
        if (tag.contains("Owner", Tag.TAG_COMPOUND)) {
            return NbtUtils.readGameProfile(tag.getCompound("Owner"));
        } else if (tag.contains("Owner", Tag.TAG_STRING)) {
            FetchedGameProfileCache cache = FetchedGameProfileCache.get();
            if (cache == null)
                return null;
            return cache.getProfile(tag.getString("Owner"));
        }
        return null;
    }

    @Nullable
    private static String getOwnerName(CompoundTag tag) {
        if (tag.contains("Owner", Tag.TAG_COMPOUND)) {
            GameProfile gameProfile = NbtUtils.readGameProfile(tag.getCompound("Owner"));
            if (gameProfile == null)
                return null;
            return gameProfile.getName();
        } else if (tag.contains("Owner", Tag.TAG_STRING)) {
            return tag.getString("Owner");
        }
        return null;
    }

    public static float getColorR(@Nullable CompoundTag tag) {
        if (tag != null && tag.contains("Color", 99)) {
            int color = tag.getInt("Color");
            return (float) (color >> 24 & 255) / 255.0F;
        }
        return 1.0F;
    }

    public static float getColorG(@Nullable CompoundTag tag) {
        if (tag != null && tag.contains("Color", 99)) {
            int color = tag.getInt("Color");
            return (float) (color >> 16 & 255) / 255.0F;
        }
        return 1.0F;
    }

    public static float getColorB(@Nullable CompoundTag tag) {
        if (tag != null && tag.contains("Color", 99)) {
            int color = tag.getInt("Color");
            return (float) (color >> 8 & 255) / 255.0F;
        }
        return 1.0F;
    }

    public static float getColorA(@Nullable CompoundTag tag) {
        if (tag != null && tag.contains("Color", 99)) {
            int color = tag.getInt("Color");
            return (float) (color & 255) / 255.0F;
        }
        return 1.0F;
    }
}
