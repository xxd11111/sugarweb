package com.sugarweb.chatAssistant.agent.ability;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.live.chat.client.bilibili.client.BilibiliLiveChatClient;
import tech.ordinaryroad.live.chat.client.bilibili.config.BilibiliLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.bilibili.listener.IBilibiliMsgListener;
import tech.ordinaryroad.live.chat.client.bilibili.netty.handler.BilibiliBinaryFrameHandler;
import tech.ordinaryroad.live.chat.client.codec.bilibili.msg.*;
import tech.ordinaryroad.live.chat.client.commons.client.enums.ClientStatusEnums;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * blbl消息收集器
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class BlblMsgAwareAbility {

    private String yourSelfUid = "20047313";

    private int roomId = 2470538;

    private BilibiliLiveChatClient client;

    private final BlockingQueue<Object> msgList = new LinkedBlockingQueue<>();

    public void init() {
        // 1. 创建配置
        BilibiliLiveChatClientConfig config = BilibiliLiveChatClientConfig.builder()
                // 消息转发地址
                // .forwardWebsocketUri("")
                // 浏览器Cookie（一大串都要）,发送弹幕时候需要填写
                // .cookie("")
                //直播间id
                .roomId(roomId)
                .build();

        // 2. 创建Client并传入配置、添加消息回调
        client = new BilibiliLiveChatClient(config, new IBilibiliMsgListener() {
            @Override
            public void onDanmuMsg(BilibiliBinaryFrameHandler binaryFrameHandler, DanmuMsgMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                BlblDmMsg blblDmMsg = BlblDmMsg.builder()
                        .blblUid(msg.getUid())
                        .username(msg.getUsername())
                        .content(msg.getContent())
                        .time(LocalDateTime.now())
                        .build();
                msgList.add(blblDmMsg);
            }

            @Override
            public void onGiftMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SendGiftMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                BlblGiftMsg gift = BlblGiftMsg.builder()
                        .blblUid(msg.getUid())
                        .username(msg.getUsername())
                        .giftNum(msg.getGiftId())
                        .gifyName(msg.getGiftName())
                        .giftPrice(msg.getGiftCount() + "")
                        .time(LocalDateTime.now())
                        .build();
                msgList.add(gift);
            }

            @Override
            public void onSuperChatMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SuperChatMessageMsg msg) {
                //留言不处理
            }

            @Override
            public void onEnterRoomMsg(InteractWordMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                BlblEnterRoomMsg blblDmMsg = BlblEnterRoomMsg.builder()
                        .blblUid(msg.getUid())
                        .username(msg.getUsername())
                        .time(LocalDateTime.now())
                        .build();
                msgList.add(blblDmMsg);
            }

            @Override
            public void onLikeMsg(BilibiliBinaryFrameHandler binaryFrameHandler, LikeInfoV3ClickMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                BlblLikeMsg blblDmMsg = BlblLikeMsg.builder()
                        .blblUid(msg.getUid())
                        .username(msg.getUsername())
                        .likeNum(msg.getClickCount() + "")
                        .time(LocalDateTime.now())
                        .build();
                msgList.add(blblDmMsg);
            }

            @Override
            public void onLiveStatusMsg(BilibiliBinaryFrameHandler binaryFrameHandler, BilibiliLiveStatusChangeMsg msg) {
                String eventMsg = StrUtil.format("事件:状态变化监听;变化行为:{};", msg.getLiveStatusAction());
            }

            @Override
            public void onRoomStatsMsg(BilibiliBinaryFrameHandler binaryFrameHandler, BilibiliRoomStatsMsg msg) {
                msgList.add(BlblCountMsg.builder()
                        .watchingCount(msg.getWatchedCount())
                        .watchedCount(msg.getLikedCount())
                        .likeCount(msg.getLikedCount())
                        .time(LocalDateTime.now())
                        .build());
            }
        });

        // 添加客户端连接状态回调
        client.addStatusChangeListener((evt, oldStatus, newStatus) -> {
            if (newStatus == ClientStatusEnums.CONNECTED) {
                log.info("blbl websocket连接成功");
            }
        });
    }

    public void start() {
        if (client == null) {
            throw new FrameworkException("BlblMsgAwareAbility client is null");
        }
        client.connect();
    }

    public void stop() {
        client.disconnect(true);
    }

    private boolean ignoreUid(String uid) {
        return StrUtil.equals(uid, yourSelfUid);
    }

    public List<Object> consumeMsg() {
        ArrayList<Object> result = new ArrayList<>();
        msgList.drainTo(result);
        return result;
    }

}
