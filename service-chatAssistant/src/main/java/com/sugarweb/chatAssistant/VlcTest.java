package com.sugarweb.chatAssistant;

import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.media.callback.CallbackMedia;
import uk.co.caprica.vlcj.media.callback.seekable.RandomAccessFileMedia;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.File;

/**
 * VlcTest测试
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class VlcTest {
    public static void main(String[] args) throws InterruptedException {

        AudioPlayerComponent audioPlayerComponent;
        audioPlayerComponent = new AudioPlayerComponent();
        audioPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
            }
        });

        String filePath = "你的音频文件";
        CallbackMedia media = new RandomAccessFileMedia(new File(filePath));
        //注意此方法是异步执行，调用vlc播放(这一步要严格保证没问题)，否则死锁
        audioPlayerComponent.mediaPlayer().media().play(media);

        Thread.currentThread().join();
    }
}
