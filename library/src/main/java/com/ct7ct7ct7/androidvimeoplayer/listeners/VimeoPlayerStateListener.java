package com.ct7ct7ct7.androidvimeoplayer.listeners;

import androidx.annotation.Nullable;

import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayer;

public interface VimeoPlayerStateListener {
    void onPlaying(@Nullable VimeoPlayer player, float duration);

    void onPaused(@Nullable VimeoPlayer player, float seconds);

    void onEnded(@Nullable VimeoPlayer player, float duration);
}
