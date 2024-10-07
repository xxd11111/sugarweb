package com.sugarweb.chatAssistant.infra;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.application.SingleUserRagPipeline;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.live.chat.client.bilibili.client.BilibiliLiveChatClient;
import tech.ordinaryroad.live.chat.client.bilibili.config.BilibiliLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.bilibili.listener.IBilibiliMsgListener;
import tech.ordinaryroad.live.chat.client.bilibili.netty.handler.BilibiliBinaryFrameHandler;
import tech.ordinaryroad.live.chat.client.codec.bilibili.msg.*;
import tech.ordinaryroad.live.chat.client.commons.client.enums.ClientStatusEnums;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class BlblClientServer implements ApplicationRunner {

    @Resource
    private SingleUserRagPipeline singleUserRagPipeline;

    private ChatTtsClient chatTtsClient = new ChatTtsClient();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        run();
    }

    String stageId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    public void run() {
        // 1. 创建配置
        BilibiliLiveChatClientConfig config = BilibiliLiveChatClientConfig.builder()
                // // TODO 消息转发地址
                // .forwardWebsocketUri("")
                // // TODO 浏览器Cookie（一大串都要）
                .cookie("")
                // TODO 直播间id（支持短id）
                .roomId(2470538)
                .build();


        // 2. 创建Client并传入配置、添加消息回调
        BilibiliLiveChatClient client = new BilibiliLiveChatClient(config, new IBilibiliMsgListener() {
            @Override
            public void onDanmuMsg(BilibiliBinaryFrameHandler binaryFrameHandler, DanmuMsgMsg msg) {
                long start = System.currentTimeMillis();
                String uid = msg.getUid();
                if (StrUtil.equals(uid, "20047313")) {
                    // return;
                }
                String eventMsg = StrUtil.format("事件:收到用户发送的弹幕;用户名:{};弹幕内容:{};", msg.getUsername(), msg.getContent());
                String r = StrUtil.format("{},您的消息正在回复中。", msg.getUsername());
                DmUtil.sendDm(r);
                String chat = singleUserRagPipeline.bilibiliAiChat(eventMsg, msg.getUid(), stageId);
                msgTtsAndWs(chat);

                long end = System.currentTimeMillis();
                System.out.println("-----------------------------------------------耗时：" + (end - start));
                System.out.println(eventMsg);
                System.out.println(chat);
            }

            @Override
            public void onGiftMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SendGiftMsg msg) {
                long start = System.currentTimeMillis();
                String eventMsg = StrUtil.format("事件:收到用户赠送的礼物;用户名:{};礼物名:{};礼物价格:{};礼物数量:{};", msg.getUsername(), msg.getGiftName(), msg.getGiftPrice(), msg.getGiftPrice());
                String r = StrUtil.format("{},您的消息正在回复中。", msg.getUsername());
                DmUtil.sendDm(r);
                String chat = singleUserRagPipeline.bilibiliAiChat(eventMsg, msg.getUid(), stageId);
                msgTtsAndWs(chat);
                long end = System.currentTimeMillis();
                System.out.println("-----------------------------------------------耗时：" + (end - start));
                System.out.println(eventMsg);
                System.out.println(chat);
            }

            @Override
            public void onSuperChatMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SuperChatMessageMsg msg) {
                String eventMsg = StrUtil.format("事件:收到醒目留言;留言用户:{};留言内容:{}", msg.getUsername(), msg.getContent());
            }

            @Override
            public void onEnterRoomMsg(InteractWordMsg msg) {
                String eventMsg = StrUtil.format("事件:用户进入直播间;用户名:{};", msg.getUsername());
                String chat = singleUserRagPipeline.bilibiliAiChat(eventMsg, msg.getUid(), stageId);
                System.out.println("-----------------------------------------------");
                System.out.println(eventMsg);
                System.out.println(chat);
            }

            @Override
            public void onLikeMsg(BilibiliBinaryFrameHandler binaryFrameHandler, LikeInfoV3ClickMsg msg) {
                String eventMsg = StrUtil.format("事件:用户点赞;用户名:{};", msg.getUsername());
                String chat = singleUserRagPipeline.bilibiliAiChat(eventMsg, msg.getUid(), stageId);
                System.out.println("-----------------------------------------------");
                System.out.println(eventMsg);
                System.out.println(chat);
            }

            @Override
            public void onLiveStatusMsg(BilibiliBinaryFrameHandler binaryFrameHandler, BilibiliLiveStatusChangeMsg msg) {
                String eventMsg = StrUtil.format("事件:状态变化监听;变化行为:{};", msg.getLiveStatusAction());
                // System.out.println(eventMsg);
            }

            @Override
            public void onRoomStatsMsg(BilibiliBinaryFrameHandler binaryFrameHandler, BilibiliRoomStatsMsg msg) {
                String eventMsg = StrUtil.format("事件:统计信息;累计点赞数:{};当前观看人数:{},累计观看人数:{}", msg.getLikedCount(), msg.getWatchingCount(), msg.getWatchedCount());
                // System.out.println(eventMsg);
            }
        });

        // 添加客户端连接状态回调
        client.addStatusChangeListener((evt, oldStatus, newStatus) -> {
            if (newStatus == ClientStatusEnums.CONNECTED) {
                // TODO 要发送的弹幕内容，请注意控制发送频率；框架内置支持设置发送弹幕的最少时间间隔，小于时将忽略该次发送
                System.out.println("程序连接触发");
            }
        });
        DmUtil.client = client;
        // 3. 开始监听直播间
        client.connect();
    }

    private void msgTtsAndWs(String msg) {
        TtsResponse tts = chatTtsClient.tts(TtsRequest.builder()
                .text(msg)
                .voice("1031.pt")
                .build());
        if (tts.success()) {
            String filePath = Optional.ofNullable(tts.getAudio_files())
                    .map(List::getFirst)
                    .map(TtsAudioFile::getUrl)
                    .orElse("");
            if (StrUtil.isNotEmpty(filePath)) {
                VlcUtil.playAudio(filePath);
            }
        }
    }

    public static class DmUtil {

        public static BilibiliLiveChatClient client;

        public static void sendDm(String content) {
            // client.sendDanmu(content);
        }
    }
}