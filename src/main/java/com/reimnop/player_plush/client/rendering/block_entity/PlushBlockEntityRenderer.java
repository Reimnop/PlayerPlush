package com.reimnop.player_plush.client.rendering.block_entity;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.reimnop.player_plush.block.PlushBlock;
import com.reimnop.player_plush.block_entity.PlushBlockEntity;
import com.reimnop.player_plush.client.PlushRenderHelper;
import com.reimnop.player_plush.client.rendering.model.PlushPose;
import com.reimnop.player_plush.util.MathUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PlushBlockEntityRenderer implements BlockEntityRenderer<PlushBlockEntity> {
    public PlushBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PlushBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        GameProfile profile = blockEntity.getProfile();
        PlayerSkin skin = getSkin(profile);

        ResourceLocation texture = skin.texture();
        @Nullable ResourceLocation cape = skin.capeTexture();

        float r = blockEntity.getColorR();
        float g = blockEntity.getColorG();
        float b = blockEntity.getColorB();
        float a = blockEntity.getColorA();

        poseStack.pushPose();
        poseStack.translate(0.5D, -0.3D, 0.5D);
        poseStack.scale(0.6F, 0.6F, 0.6F);

        // Get block state
        BlockState blockState = blockEntity.getBlockState();

        // Set rotation
        poseStack.mulPose(Axis.YN.rotation(blockState.getValue(PlushBlock.FACING).toYRot() * MathUtil.DEG_2_RAD));

        poseStack.translate(0.0D, 0.0D, -0.2D);

        PlushRenderHelper.render(
                PlushPose.SITTING,
                texture,
                cape,
                skin.model() == PlayerSkin.Model.SLIM, poseStack,
                buffer,
                packedLight,
                packedOverlay,
                r,
                g,
                b,
                a);
        poseStack.popPose();
    }

    private static PlayerSkin getSkin(@Nullable GameProfile gameProfile) {
        if (gameProfile != null) {
            Minecraft minecraft = Minecraft.getInstance();
            SkinManager skinManager = minecraft.getSkinManager();
            return skinManager.getInsecureSkin(gameProfile);
        }
        return DefaultPlayerSkin.get(Util.NIL_UUID);
    }
}
