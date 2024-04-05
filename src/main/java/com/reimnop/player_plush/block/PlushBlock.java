package com.reimnop.player_plush.block;

import com.mojang.authlib.GameProfile;
import com.reimnop.player_plush.block_entity.PlushBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PlushBlock extends Block implements EntityBlock {
    public PlushBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlushBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof PlushBlockEntity plushBlockEntity))
            return;

        // Get stack tag
        @Nullable CompoundTag tag = stack.getTag();
        if (tag == null)
            return;

        // Set owner name
        if (tag.contains("Owner", Tag.TAG_STRING)) {
            plushBlockEntity.setOwnerName(tag.getString("Owner"));
        }

        // Set owner profile
        if (tag.contains("Owner", Tag.TAG_COMPOUND)) {
            GameProfile profile = NbtUtils.readGameProfile(tag.getCompound("Owner"));
            plushBlockEntity.setOwnerProfile(profile);
        }

        // Set color
        if (tag.contains("Color", Tag.TAG_INT)) {
            int color = tag.getInt("Color");
            float r = (float) (color >> 24 & 255) / 255.0F;
            float g = (float) (color >> 16 & 255) / 255.0F;
            float b = (float) (color >> 8 & 255) / 255.0F;
            float a = (float) (color & 255) / 255.0F;
            plushBlockEntity.setColor(r, g, b, a);
        }
    }
}
