package com.sugarweb.chatAssistant.infra;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

/**
 * vlc音频播放工具类
 */
public class VlcUtil {

    private final AudioPlayerComponent mediaPlayerComponent;

    private static class Inner{
        private static VlcUtil instance = new VlcUtil();
    }

    public static void playAudio(String filePath){
        Inner.instance.start(filePath);
    }

    // public static void main(String[] args) {
    //     VlcUtil.playAudio("D:\\ChatTTS-ui-v1.0\\static\\wavs\\124352_use6.09s-audio0s-seed1031.pt-te0.1-tp0.701-tk20-textlen19-06732-merge.wav");
    //     try {
    //         Thread.currentThread().join();
    //     } catch (InterruptedException e) {
    //     }
    // }

    private VlcUtil() {
        mediaPlayerComponent = new AudioPlayerComponent();
        mediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                exit(0);
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                exit(1);
            }
        });
    }

    private void start(String mrl) {
        mediaPlayerComponent.mediaPlayer().media().play(mrl);
    }

    private void exit(int result) {
        // It is not allowed to call back into LibVLC from an event handling thread, so submit() is used
        mediaPlayerComponent.mediaPlayer().submit(new Runnable() {
            @Override
            public void run() {
                mediaPlayerComponent.mediaPlayer().release();
                System.exit(result);
            }
        });
    }
}