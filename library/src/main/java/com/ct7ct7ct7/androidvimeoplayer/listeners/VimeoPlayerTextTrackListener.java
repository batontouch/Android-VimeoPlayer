package com.ct7ct7ct7.androidvimeoplayer.listeners;

import androidx.annotation.Nullable;

import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayer;

public interface VimeoPlayerTextTrackListener {
    void onTextTrackChanged(@Nullable VimeoPlayer player, String kind, String label, String language);
}
