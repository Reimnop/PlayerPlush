package com.reimnop.player_plush.client.rendering.item;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.reimnop.player_plush.client.PlushRenderHelper;
import com.reimnop.player_plush.client.rendering.model.PlushPose;
import com.reimnop.player_plush.item.PlushItem;
import com.reimnop.player_plush.util.PlushNbtHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4d;
import org.joml.Math;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class PlushItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        // Prepare matrices
        matrices.pushPose();
        if (mode == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            Minecraft minecraft = Minecraft.getInstance();
            AbstractClientPlayer player = minecraft.player;
            PlayerRenderer playerRenderer = (PlayerRenderer) minecraft
                    .getEntityRenderDispatcher()
                    .getRenderer(player);

            matrices.pushPose();
            matrices.translate(-0.08F, 0.25F, 0.6F);
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI, 0.0D, 0.0D, 1.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI / 6.0D, 0.0D, 1.0D, 0.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(-Math.PI * 0.5D, 1.0D, 0.0D, 0.0D)));
            playerRenderer.renderLeftHand(matrices, vertexConsumers, light, player);
            matrices.popPose();

            matrices.pushPose();
            matrices.translate(0.08F, 0.25F, 0.6F);
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI, 0.0D, 0.0D, 1.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(-Math.PI / 6.0D, 0.0D, 1.0D, 0.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(-Math.PI * 0.525, 1.0D, 0.0D, 0.0D)));
            playerRenderer.renderRightHand(matrices, vertexConsumers, light, player);
            matrices.popPose();

            matrices.translate(-0.5F, -0.375F, -0.85F);
        }

        matrices.translate(0.5F, 0.0F, 0.5F);

        if (mode == ItemDisplayContext.GUI) {
            matrices.translate(-0.05F, -0.05F, 0.0F);
            matrices.scale(0.55F, 0.55F, 0.55F);
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI / 4.0F, 1.0D, 0.0D, 0.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI / 4.0F, 0.0D, 1.0D, 0.0D)));
        } else if (mode == ItemDisplayContext.GROUND) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        } else if (mode == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || mode == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
            matrices.translate(0.0D, -0.5D, 0.0D);
        } else {
            matrices.scale(0.75F, 0.75F, 0.75F);
        }

        // Get game profile
        CompoundTag tag = stack.getTag();
        GameProfile gameProfile = tag != null ? PlushNbtHelper.getProfile(tag) : null;

        // Get player skin
        PlayerSkin skin = getSkin(gameProfile);

        float r = PlushNbtHelper.getColorR(tag);
        float g = PlushNbtHelper.getColorG(tag);
        float b = PlushNbtHelper.getColorB(tag);
        float a = PlushNbtHelper.getColorA(tag);

        // Render plush
        PlushRenderHelper.render(
                PlushPose.SITTING,
                skin.texture(),
                skin.capeTexture(),
                skin.model() == PlayerSkin.Model.SLIM,
                matrices,
                vertexConsumers,
                light,
                overlay,
                r,
                g,
                b,
                a);

        matrices.popPose();
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
