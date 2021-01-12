package com.ct7ct7ct7.androidvimeoplayer.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;

import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerErrorListener;
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerReadyListener;
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerStateListener;
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerTextTrackListener;
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerTimeListener;
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerVolumeListener;
import com.ct7ct7ct7.androidvimeoplayer.model.PlayerState;
import com.ct7ct7ct7.androidvimeoplayer.model.TextTrack;
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayer;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class JsBridge {
    private final Handler mainThreadHandler;
    private final ArrayList<VimeoPlayerReadyListener> readyListeners;
    private final ArrayList<VimeoPlayerStateListener> stateListeners;
    private final ArrayList<VimeoPlayerTextTrackListener> textTrackListeners;
    private final ArrayList<VimeoPlayerTimeListener> timeListeners;
    private final ArrayList<VimeoPlayerVolumeListener> volumeListeners;
    private final ArrayList<VimeoPlayerErrorListener> errorListeners;


    public float currentTimeSeconds = 0;
    public PlayerState playerState = PlayerState.UNKNOWN;
    public float volume = 1;
    @NonNull
    private final WeakReference<VimeoPlayer> playerWeakReference;

    public JsBridge(VimeoPlayer player) {
        readyListeners = new ArrayList<>();
        stateListeners = new ArrayList<>();
        textTrackListeners = new ArrayList<>();
        timeListeners = new ArrayList<>();
        volumeListeners = new ArrayList<>();
        errorListeners = new ArrayList<>();
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.playerWeakReference = new WeakReference<>(player);
    }

    public void removeLastReadyListener(VimeoPlayerReadyListener readyListener) {
        this.readyListeners.remove(readyListener);
    }

    public void addReadyListener(VimeoPlayerReadyListener readyListener) {
        this.readyListeners.add(readyListener);
    }

    public void addStateListener(VimeoPlayerStateListener stateListener) {
        this.stateListeners.add(stateListener);
    }

    public void addTextTrackListener(VimeoPlayerTextTrackListener textTrackListener) {
        this.textTrackListeners.add(textTrackListener);
    }

    public void addTimeListener(VimeoPlayerTimeListener timeListener) {
        this.timeListeners.add(timeListener);
    }

    public void addVolumeListener(VimeoPlayerVolumeListener volumeListener) {
        this.volumeListeners.add(volumeListener);
    }

    public void addErrorListener(VimeoPlayerErrorListener errorListener) {
        this.errorListeners.add(errorListener);
    }


    @JavascriptInterface
    public void sendVideoCurrentTime(float seconds) {
        currentTimeSeconds = seconds;
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerTimeListener timeListener : timeListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    timeListener.onCurrentSecond(player, currentTimeSeconds);
                }
            });
        }
    }

    @JavascriptInterface
    public void sendError(final String message, final String method, final String name) {
        Log.d("VimeoPlayer", "JsBridge:error " + message + " /method: " + method + " /name: " + name);
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerErrorListener errorListener : errorListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    errorListener.onError(player, message, method, name);
                }
            });
        }
    }


    @JavascriptInterface
    public void sendReady(final String title, final float duration, final String tracksJson) {
        this.playerState = PlayerState.READY;
        final TextTrack[] textTrackArray = new Gson().fromJson(tracksJson, TextTrack[].class);
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerReadyListener readyListener : readyListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    readyListener.onReady(player, title, duration, textTrackArray);
                }
            });
        }
    }

    @JavascriptInterface
    public void sendInitFailed() {
        Log.d("VimeoPlayer", "JsBridge initFailed");
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerReadyListener readyListener : readyListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    readyListener.onInitFailed(player);
                }
            });
        }
    }


    @JavascriptInterface
    public void sendPlaying(final float duration) {
        playerState = PlayerState.PLAYING;
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerStateListener stateListener : stateListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    stateListener.onPlaying(player, duration);
                }
            });
        }
    }


    @JavascriptInterface
    public void sendPaused(final float seconds) {
        playerState = PlayerState.PAUSED;
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerStateListener stateListener : stateListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    stateListener.onPaused(player, seconds);
                }
            });
        }
    }

    @JavascriptInterface
    public void sendEnded(final float duration) {
        playerState = PlayerState.ENDED;
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerStateListener stateListener : stateListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    stateListener.onEnded(player, duration);
                }
            });
        }
    }


    @JavascriptInterface
    public void sendVolumeChange(final float volume) {
        this.volume = volume;
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerVolumeListener volumeListener : volumeListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    volumeListener.onVolumeChanged(player, volume);
                }
            });
        }
    }

    @JavascriptInterface
    public void sendTextTrackChange(final String kind, final String label, final String language) {
        final VimeoPlayer player = playerWeakReference.get();
        for (final VimeoPlayerTextTrackListener textTrackListener : textTrackListeners) {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    textTrackListener.onTextTrackChanged(player, kind, label, language);
                }
            });
        }
    }

}
