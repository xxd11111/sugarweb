package com.sugarweb.chatAssistant.temp.ability;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.temp.BaseMsg;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.live.chat.client.bilibili.client.BilibiliLiveChatClient;
import tech.ordinaryroad.live.chat.client.bilibili.config.BilibiliLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.bilibili.listener.IBilibiliMsgListener;
import tech.ordinaryroad.live.chat.client.bilibili.netty.handler.BilibiliBinaryFrameHandler;
import tech.ordinaryroad.live.chat.client.codec.bilibili.msg.*;
import tech.ordinaryroad.live.chat.client.commons.client.enums.ClientStatusEnums;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 消息收集器
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class BlblMsgAwareAbility {

    private String yourSelfUid = "20047313";

    private int roomId = 2470538;

    private BilibiliLiveChatClient client;

    private final CopyOnWriteArrayList<BaseMsg> msgList = new CopyOnWriteArrayList<>();

    public void aware() {
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
                String eventMsg = StrUtil.format("事件:收到用户发送的弹幕;用户名:{};弹幕内容:{};", msg.getUsername(), msg.getContent());
                BaseMsg baseMsg = BaseMsg.builder()
                        .uid(msg.getUid())
                        .username(msg.getUsername())
                        .msgType("danmu")
                        .content(eventMsg)
                        .time(LocalDateTime.now())
                        .msgWeight(0.0f)
                        .build();
                msgList.add(baseMsg);
            }

            @Override
            public void onGiftMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SendGiftMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                String eventMsg = StrUtil.format("事件:收到用户赠送的礼物;用户名:{};礼物名:{};礼物价格:{};礼物数量:{};", msg.getUsername(), msg.getGiftName(), msg.getGiftPrice(), msg.getGiftPrice());
            }

            @Override
            public void onSuperChatMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SuperChatMessageMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                String eventMsg = StrUtil.format("事件:收到醒目留言;留言用户:{};留言内容:{}", msg.getUsername(), msg.getContent());
            }

            @Override
            public void onEnterRoomMsg(InteractWordMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                String eventMsg = StrUtil.format("事件:用户进入直播间;用户名:{};", msg.getUsername());
            }

            @Override
            public void onLikeMsg(BilibiliBinaryFrameHandler binaryFrameHandler, LikeInfoV3ClickMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                String eventMsg = StrUtil.format("事件:用户点赞;用户名:{};", msg.getUsername());
            }

            @Override
            public void onLiveStatusMsg(BilibiliBinaryFrameHandler binaryFrameHandler, BilibiliLiveStatusChangeMsg msg) {
                String eventMsg = StrUtil.format("事件:状态变化监听;变化行为:{};", msg.getLiveStatusAction());
            }

            @Override
            public void onRoomStatsMsg(BilibiliBinaryFrameHandler binaryFrameHandler, BilibiliRoomStatsMsg msg) {
                String eventMsg = StrUtil.format("事件:统计信息;累计点赞数:{};当前观看人数:{},累计观看人数:{}", msg.getLikedCount(), msg.getWatchingCount(), msg.getWatchedCount());
            }
        });

        // 添加客户端连接状态回调
        client.addStatusChangeListener((evt, oldStatus, newStatus) -> {
            if (newStatus == ClientStatusEnums.CONNECTED) {
                log.info("blbl websocket连接成功");
            }
        });
        client.connect();
    }

    private boolean ignoreUid(String uid) {
        return StrUtil.equals(uid, yourSelfUid);
    }

    public void stop() {
        client.disconnect(true);
    }

    public List<BaseMsg> getMsg() {
        return msgList;
    }

}
