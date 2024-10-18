package com.sugarweb.chatAssistant.agent.ability.input.blbl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.fasterxml.jackson.databind.JsonNode;
import com.sugarweb.chatAssistant.agent.ability.adaptor.ThinkInputAdaptor;
import com.sugarweb.chatAssistant.domain.BlblUser;
import com.sugarweb.framework.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.live.chat.client.bilibili.client.BilibiliLiveChatClient;
import tech.ordinaryroad.live.chat.client.bilibili.config.BilibiliLiveChatClientConfig;
import tech.ordinaryroad.live.chat.client.bilibili.listener.IBilibiliMsgListener;
import tech.ordinaryroad.live.chat.client.bilibili.netty.handler.BilibiliBinaryFrameHandler;
import tech.ordinaryroad.live.chat.client.codec.bilibili.msg.*;
import tech.ordinaryroad.live.chat.client.commons.client.enums.ClientStatusEnums;

import java.time.LocalDateTime;

/**
 * blbl消息收集器
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class BlblMsgInputAbility {

    private String yourSelfUid = "20047313";

    private int roomId = 2470538;

    private String cookie = "";

    private BilibiliLiveChatClient client;

    private final ThinkInputAdaptor thinkInputAdaptor;

    public BlblMsgInputAbility(ThinkInputAdaptor thinkInputAdaptor) {
        this.thinkInputAdaptor = thinkInputAdaptor;
        initBlblClient();
    }

    private void initBlblClient() {
        // 1. 创建配置
        BilibiliLiveChatClientConfig config = BilibiliLiveChatClientConfig.builder()
                // 消息转发地址
                // .forwardWebsocketUri("")
                // 浏览器Cookie（一大串都要）,发送弹幕时候需要必填，注意未配置cookie会导致5分钟后读取到脱敏用户名，脱敏uid
                .cookie(cookie)
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
                //todo 兼容点歌 等互动聊天功能
                BlblDmMsg blblDmMsg = BlblDmMsg.builder()
                        .blblUid(msg.getUid())
                        .username(msg.getUsername())
                        .content(msg.getContent())
                        .time(LocalDateTime.now())
                        .build();
                thinkInputAdaptor.add(blblDmMsg);
                log.info("danmuMsg:{}", blblDmMsg);
            }

            @Override
            public void onGiftMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SendGiftMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }

                BlblGiftMsg giftMsg = BlblGiftMsg.builder()
                        .blblUid(msg.getUid())
                        .username(msg.getUsername())
                        .giftNum(msg.getGiftId())
                        .gifyName(msg.getGiftName())
                        .giftPrice(msg.getGiftCount() + "")
                        .time(LocalDateTime.now())
                        .build();
                thinkInputAdaptor.add(giftMsg);
                log.info("giftMsg:{}", giftMsg);
            }

            @Override
            public void onSuperChatMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SuperChatMessageMsg msg) {
                //留言不处理
            }

            @Override
            public void onEnterRoomMsg(InteractWordMsg msg) {
                //todo 记录是否关注，是否有勋章
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                BlblUser blblUser = Db.getById(msg.getUid(), BlblUser.class);
                if (blblUser == null) {
                    blblUser = new BlblUser();
                    blblUser.setBlblUid(msg.getUid());
                    blblUser.setUsername(msg.getUsername());
                    blblUser.setCreateTime(LocalDateTime.now());
                    blblUser.setUpdateTime(LocalDateTime.now());
                    Db.save(blblUser);
                } else {
                    //更新用户信息
                    blblUser.setUsername(msg.getUsername());
                    blblUser.setUpdateTime(LocalDateTime.now());
                    Db.updateById(blblUser);
                }

                BlblEnterRoomMsg blblDmMsg = BlblEnterRoomMsg.builder()
                        .blblUid(msg.getUid())
                        .username(msg.getUsername())
                        .time(LocalDateTime.now())
                        .build();
                thinkInputAdaptor.add(blblDmMsg);
                log.info("enterRoomMsg:{}", blblDmMsg);
            }

            @Override
            public void onLikeMsg(BilibiliBinaryFrameHandler binaryFrameHandler, LikeInfoV3ClickMsg msg) {
                if (ignoreUid(msg.getUid())) {
                    return;
                }
                BlblLikeMsg blblLikeMsg = BlblLikeMsg.builder()
                        .blblUid(msg.getUid())
                        .username(msg.getUsername())
                        .likeNum(msg.getClickCount() + "")
                        .time(LocalDateTime.now())
                        .build();
                thinkInputAdaptor.add(blblLikeMsg);
                log.info("likeMsg:{}", blblLikeMsg);
            }

            @Override
            public void onLiveStatusMsg(BilibiliBinaryFrameHandler binaryFrameHandler, BilibiliLiveStatusChangeMsg msg) {
                String eventMsg = StrUtil.format("事件:状态变化监听;变化行为:{};", msg.getLiveStatusAction());
            }

            @Override
            public void onRoomStatsMsg(BilibiliBinaryFrameHandler binaryFrameHandler, BilibiliRoomStatsMsg msg) {
                //统计消息不做记录
                BlblCountMsg blblCountMsg = BlblCountMsg.builder()
                        .watchingCount(msg.getWatchedCount())
                        .watchedCount(msg.getWatchedCount())
                        .likeCount(msg.getLikedCount())
                        .time(LocalDateTime.now())
                        .build();
                JsonNode countNode = msg.getData().get("count_text");
                String countText = countNode.asText();
                JsonNode onlineCountNode = msg.getData().get("online_count_text");
                String onlineCountText = onlineCountNode.asText();
                // thinkInputAdaptor.add(blblCountMsg);
                log.info("blblCountMsg:{}", blblCountMsg);
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
        return false;
        // return StrUtil.equals(uid, yourSelfUid);
    }

}
