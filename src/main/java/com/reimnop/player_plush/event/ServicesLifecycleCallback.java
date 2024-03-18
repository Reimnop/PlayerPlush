package com.reimnop.player_plush.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.Services;

public interface ServicesLifecycleCallback {
    Event<ServicesLifecycleCallback> INIT = EventFactory.createArrayBacked(ServicesLifecycleCallback.class, (listeners) -> (services) -> {
        for (ServicesLifecycleCallback listener : listeners) {
            listener.onServicesLifecycle(services);
        }
    });

    Event<ServicesLifecycleCallback> DEINIT = EventFactory.createArrayBacked(ServicesLifecycleCallback.class, (listeners) -> (services) -> {
        for (ServicesLifecycleCallback listener : listeners) {
            listener.onServicesLifecycle(services);
        }
    });

    void onServicesLifecycle(Services services);
}
