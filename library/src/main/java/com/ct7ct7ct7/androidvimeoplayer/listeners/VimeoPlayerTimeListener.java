package com.ct7ct7ct7.androidvimeoplayer.listeners;

import androidx.annotation.Nullable;

import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayer;

public interface VimeoPlayerTimeListener {
    void onCurrentSecond(@Nullable VimeoPlayer player, float second);
}
