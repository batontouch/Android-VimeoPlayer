package com.ct7ct7ct7.androidvimeoplayer.listeners;

import androidx.annotation.Nullable;

import com.ct7ct7ct7.androidvimeoplayer.model.TextTrack;
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayer;

public interface VimeoPlayerReadyListener {
    void onReady(@Nullable VimeoPlayer player, String title, float duration, TextTrack[] textTrackArray);

    void onInitFailed(@Nullable VimeoPlayer player);
}
