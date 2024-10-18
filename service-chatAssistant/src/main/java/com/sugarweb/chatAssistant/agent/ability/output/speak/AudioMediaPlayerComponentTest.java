package com.sugarweb.chatAssistant.agent.ability.output.speak;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

public class AudioMediaPlayerComponentTest extends VlcjEnv {

    /**
     * Media player component.
     */
    private final AudioPlayerComponent audioMediaPlayerComponent;

    /**
     * Application entry point.
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        String mrl = "C:\\Users\\xxd\\mp3.wav";

        // In this test, we must keep an object reference here otherwise the media
        // player will become eligible for garbage collection immediately, causing
        // a potentially fatal JVM crash - this is just an artefact of this test,
        // ordinarily an application would be keeping a reference to the component
        // anyway
        AudioMediaPlayerComponentTest test = new AudioMediaPlayerComponentTest();
        test.start(mrl);

        // Since there is no UI, we must join here to prevent the application from
        // exiting and destroying the media player
        Thread.currentThread().join();
    }

    /**
     * Create a new test.
     */
    private AudioMediaPlayerComponentTest() {
        audioMediaPlayerComponent = new AudioPlayerComponent();
        audioMediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.exit(0);
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                System.exit(1);
            }
        });
    }

    /**
     * Start playing media.
     *
     * @param mrl mrl
     */
    private void start(String mrl) {
        // One line of vlcj code to play the media...
        audioMediaPlayerComponent.mediaPlayer().media().play(mrl);
    }

    private void release() {
        audioMediaPlayerComponent.release();
    }
}