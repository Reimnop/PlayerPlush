package com.reimnop.player_plush.client.rendering.item;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.reimnop.player_plush.client.rendering.model.PlushModel;
import com.reimnop.player_plush.client.rendering.model.PlushModelPart;
import com.reimnop.player_plush.client.rendering.model.PlushPose;
import com.reimnop.player_plush.item.PlushItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class PlushItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private static final PlushModel FULL_MODEL = new PlushModel(64, 64, false);
    private static final PlushModel SLIM_MODEL = new PlushModel(64, 64, true);

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        // Get game profile
        CompoundTag tag = stack.getTag();
        GameProfile gameProfile = tag != null ? PlushItem.getProfile(tag) : null;

        // Get player skin
        PlayerSkin skin = getSkin(gameProfile);

        matrices.pushPose();
        if (mode == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            Minecraft minecraft = Minecraft.getInstance();
            AbstractClientPlayer player = minecraft.player;
            PlayerRenderer playerRenderer = (PlayerRenderer) minecraft
                    .getEntityRenderDispatcher()
                    .getRenderer(player);

            matrices.pushPose();
            matrices.translate(-0.08D, 0.25D, 0.6D);
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI, 0.0D, 0.0D, 1.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI / 6.0D, 0.0D, 1.0D, 0.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(-Math.PI * 0.5D, 1.0D, 0.0D, 0.0D)));
            playerRenderer.renderLeftHand(matrices, vertexConsumers, light, player);
            matrices.popPose();

            matrices.pushPose();
            matrices.translate(0.08D, 0.25D, 0.6D);
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI, 0.0D, 0.0D, 1.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(-Math.PI / 6.0D, 0.0D, 1.0D, 0.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(-Math.PI * 0.525, 1.0D, 0.0D, 0.0D)));
            playerRenderer.renderRightHand(matrices, vertexConsumers, light, player);
            matrices.popPose();

            matrices.translate(-0.5D, 0.25D, -0.85D);
        }

        matrices.translate(0.5D, 0.75D, 0.5D);
        matrices.scale(0.75F, 0.75F, 0.75F);
        matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI, 1.0D, 0.0D, 0.0D)));

        matrices.pushPose();
        if (mode == ItemDisplayContext.GUI) {
            matrices.translate(0.125D, 0.125D, 0.0D);
            matrices.scale(0.7F, 0.7F, 0.7F);
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI / 4.0F, 1.0D, 0.0D, 0.0D)));
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI / 4.0F, 0.0D, 1.0D, 0.0D)));
        } else if (mode == ItemDisplayContext.GROUND) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        } else if (mode == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || mode == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
            matrices.translate(0.0D, -0.5D, 0.0D);
        }

        PlushModel model = skin.model() == PlayerSkin.Model.SLIM ? SLIM_MODEL : FULL_MODEL;
        model.applyPose(PlushPose.SITTING);
        ModelPart cloak = Objects.requireNonNull(model.getModelPart(PlushModelPart.CLOAK));

        float r = PlushItem.getColorR(tag);
        float g = PlushItem.getColorG(tag);
        float b = PlushItem.getColorB(tag);
        float a = PlushItem.getColorA(tag);

        // Render main skin
        ResourceLocation skinTexture = skin.texture();
        RenderType renderType = getRenderType(skinTexture);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderType);
        cloak.skipDraw = true;
        model.render(matrices, vertexConsumer, light, overlay, r, g, b, a);

        // Render cape
        ResourceLocation capeTexture = skin.capeTexture();
        if (capeTexture != null) {
            RenderType capeRenderType = getRenderType(capeTexture);
            VertexConsumer capeVertexConsumer = vertexConsumers.getBuffer(capeRenderType);

            matrices.pushPose();
            matrices.translate(0.0F, 0.0F, 0.125F);
            matrices.mulPose(new Quaternionf(new AxisAngle4d(Math.PI, 0.0D, 1.0D, 0.0D)));
            cloak.skipDraw = false;
            cloak.render(matrices, capeVertexConsumer, light, overlay, r, g, b, a);
            matrices.popPose();
        }

        matrices.popPose();
        matrices.popPose();
    }

    private static RenderType getRenderType(ResourceLocation resourceLocation) {
        return RenderType.itemEntityTranslucentCull(resourceLocation);
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
