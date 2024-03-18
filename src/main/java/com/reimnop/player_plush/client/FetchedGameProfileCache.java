package com.reimnop.player_plush.client;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.ProfileResult;
import net.minecraft.Util;
import net.minecraft.server.Services;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FetchedGameProfileCache {
    @Nullable
    private static FetchedGameProfileCache instance;

    @Nullable
    public static FetchedGameProfileCache get() {
        return instance;
    }

    public static void initialize(Services services) {
        if (instance != null)
            return;
        instance = new FetchedGameProfileCache(services);
    }

    public static void clear() {
        instance = null;
    }

    private final LoadingCache<String, CompletableFuture<Optional<GameProfile>>> loadingCache;

    private FetchedGameProfileCache(Services services) {
        loadingCache = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(10L))
                .maximumSize(256L)
                .build(new CacheLoader<>() {
                    @NotNull
                    public CompletableFuture<Optional<GameProfile>> load(@NotNull String name) {
                        return loadProfile(name, services);
                    }
                });
    }

    private CompletableFuture<Optional<GameProfile>> loadProfile(String name, Services services) {
        return services.profileCache().getAsync(name).thenApplyAsync(optional -> {
            if (optional.isPresent()) {
                UUID uuid = optional.get().getId();
                ProfileResult profileResult = services.sessionService().fetchProfile(uuid, true);
                return profileResult != null ? Optional.ofNullable(profileResult.profile()) : optional;
            } else {
                return Optional.empty();
            }
        }, Util.backgroundExecutor());
    }

    @Nullable
    public GameProfile getProfile(String name) {
        try {
            return loadingCache
                    .get(name)
                    .getNow(Optional.empty())
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
