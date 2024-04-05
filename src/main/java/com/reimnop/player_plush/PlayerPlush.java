package com.reimnop.player_plush;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerPlush implements ModInitializer {

    public static final String MODID = "player_plush";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Player Plush says hello!");

        BlockRegistry.register();
        BlockEntityRegistry.register();
        ItemRegistry.register();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
