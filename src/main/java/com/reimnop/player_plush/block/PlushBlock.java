package com.reimnop.player_plush.block;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.MapCodec;
import com.reimnop.player_plush.ItemRegistry;
import com.reimnop.player_plush.block_entity.PlushBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PlushBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<PlushBlock> CODEC = simpleCodec(PlushBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public PlushBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.isShiftKeyDown() && player.getItemInHand(hand).getItem() instanceof PlayerHeadItem) {
            if (!level.isClientSide) {
                // Copy player head item SkullOwner tag to BlockEntity
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof PlushBlockEntity plushBlockEntity) {
                    ItemStack heldStack = player.getItemInHand(hand);
                    CompoundTag tag = heldStack.getTag();
                    if (tag != null) {
                        CompoundTag newTag = new CompoundTag();
                        if (tag.contains("SkullOwner")) {
                            newTag.put("Owner", tag.get("SkullOwner"));
                        }
                        plushBlockEntity.load(newTag);
                        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(FACING, context.getHorizontalDirection().getOpposite());
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
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof PlushBlockEntity plushBlockEntity) {
                CompoundTag blockEntityTag = plushBlockEntity.saveWithoutMetadata();
                ItemStack stack = new ItemStack(this);
                CompoundTag tag = stack.getOrCreateTag();

                if (blockEntityTag.contains("Owner", Tag.TAG_COMPOUND)) {
                    tag.put("Owner", blockEntityTag.getCompound("Owner"));
                }
                if (blockEntityTag.contains("Owner", Tag.TAG_STRING)) {
                    tag.putString("Owner", blockEntityTag.getString("Owner"));
                }
                if (blockEntityTag.contains("Color", Tag.TAG_INT)) {
                    tag.putInt("Color", blockEntityTag.getInt("Color"));
                }

                ItemEntity itemEntity = new ItemEntity(level, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, stack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
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
