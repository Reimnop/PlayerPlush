package com.reimnop.player_plush;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

import java.util.logging.Logger;

public class PlayerPlush implements ModInitializer {

    public static final String MODID = "player_plush";
    public static final Logger LOGGER = Logger.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Player Plush says hello!");

        ItemRegistry.register();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
