package com.sugarweb.chatAssistant.infra;

import uk.co.caprica.vlcj.player.base.EventApi;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

/**
 * vlc音频播放工具类
 */
public class VlcUtil {

    private final AudioPlayerComponent mediaPlayerComponent;

    private boolean isPlaying = false;

    private static class Inner {
        private static final VlcUtil instance = new VlcUtil();
    }

    public static void playAudio(String filePath) {
        Inner.instance.start(filePath);
    }

    private VlcUtil() {
        mediaPlayerComponent = new AudioPlayerComponent();
        MediaPlayer mediaPlayer = mediaPlayerComponent.mediaPlayer();
        EventApi events = mediaPlayer.events();
        events.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                isPlaying = false;
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                isPlaying = false;
            }
        });
    }

    private void start(String mrl) {
        mediaPlayerComponent.mediaPlayer().media().play(mrl);
    }

    private void exit(int result) {
        // It is not allowed to call back into LibVLC from an event handling thread, so submit() is used
        mediaPlayerComponent.mediaPlayer().submit(() -> {
            mediaPlayerComponent.mediaPlayer().release();
            System.exit(result);
        });
    }
}