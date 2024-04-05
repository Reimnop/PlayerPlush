package com.reimnop.player_plush.block_entity;

import com.mojang.authlib.GameProfile;
import com.reimnop.player_plush.BlockEntityRegistry;
import com.reimnop.player_plush.client.FetchedGameProfileCache;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlushBlockEntity extends BlockEntity {
    @Nullable
    private String ownerName;

    @Nullable
    private GameProfile ownerProfile;

    private float colorR = 1.0F;
    private float colorG = 1.0F;
    private float colorB = 1.0F;
    private float colorA = 1.0F;

    public PlushBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.PLUSH_BLOCK_ENTITY, pos, blockState);
    }

    @Override
    @NotNull
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    @NotNull
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public float getColorR() {
        return colorR;
    }

    public float getColorG() {
        return colorG;
    }

    public float getColorB() {
        return colorB;
    }

    public float getColorA() {
        return colorA;
    }

    public void setOwnerName(@Nullable String ownerName) {
        this.ownerName = ownerName;
        setChanged();
    }

    public void setOwnerProfile(@Nullable GameProfile ownerProfile) {
        this.ownerProfile = ownerProfile;
        setChanged();
    }

    public void setColor(float r, float g, float b, float a) {
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        this.colorA = a;
        setChanged();
    }

    @Nullable
    public GameProfile getProfile() {
        if (ownerProfile != null) {
            return ownerProfile;
        } else if (ownerName != null) {
            FetchedGameProfileCache cache = FetchedGameProfileCache.get();
            if (cache == null)
                return null;
            return cache.getProfile(ownerName);
        }
        return null;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if (tag.contains("Owner", Tag.TAG_STRING)) {
            this.ownerName = tag.getString("Owner");
        } else if (tag.contains("Owner", Tag.TAG_COMPOUND)) {
            this.ownerProfile = NbtUtils.readGameProfile(tag.getCompound("Owner"));
        }

        if (tag.contains("Color", Tag.TAG_INT)) {
            int color = tag.getInt("Color");
            this.colorR = (float) (color >> 24 & 255) / 255.0F;
            this.colorG = (float) (color >> 16 & 255) / 255.0F;
            this.colorB = (float) (color >> 8 & 255) / 255.0F;
            this.colorA = (float) (color & 255) / 255.0F;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        GameProfile profile = getProfile();
        if (profile != null) {
            tag.put("Owner", NbtUtils.writeGameProfile(new CompoundTag(), profile));
        } else if (ownerName != null) {
            tag.putString("Owner", ownerName);
        }

        int color = ((int) (colorR * 255.0F) << 24) | ((int) (colorG * 255.0F) << 16) | ((int) (colorB * 255.0F) << 8) | (int) (colorA * 255.0F);
        tag.putInt("Color", color);
    }
}
