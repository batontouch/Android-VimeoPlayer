package com.ct7ct7ct7.androidvimeoplayer.listeners;

import androidx.annotation.Nullable;

import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayer;

public interface VimeoPlayerErrorListener {
    void onError(@Nullable VimeoPlayer player, String message, String method, String name);
}
