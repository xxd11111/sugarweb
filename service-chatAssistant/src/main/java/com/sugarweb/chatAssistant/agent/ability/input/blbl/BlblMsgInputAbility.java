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

    private String cookie = "buvid3=9D364EC2-5E90-CD2D-1673-7A5764DA1FEB22057infoc; buvid4=F4FF10CB-EF04-06EA-088C-26B9554DC3C622810-023012111-KFBfGLWm3YRJM50PziW5Rg%3D%3D; rpdid=|(YYlJl~RmJ0J'uY~Rk~klkJ; buvid_fp_plain=undefined; CURRENT_BLACKGAP=0; enable_web_push=DISABLE; header_theme_version=CLOSE; b_nut=100; _uuid=8BD467DD-5562-D1C2-96A9-6E2237437CA567978infoc; is-2022-channel=1; FEED_LIVE_VERSION=V_WATCHLATER_PIP_WINDOW3; DedeUserID=20047313; DedeUserID__ckMd5=a3663c8815a28502; CURRENT_QUALITY=116; LIVE_BUVID=AUTO9117279467884429; PVID=1; CURRENT_FNVAL=4048; fingerprint=b6e4d2afbc3bd4672f0b7b6b8b23b7c0; SESSDATA=2685ee1a%2C1744637268%2Cdc181%2Aa1CjBjfsT9VwXU2C5nN5OdVkMPoSmmRpZIiKUDkjSZVhTxcEY22YfV33R5AaH5ImU3Rz0SVjR5d0dDVmFjUWJtLWhMZmhvTnZaSS1qREpQSEozYmludlhDV0M5dFlsQkh5RXc2d2hFRFlxMzFlX1dNcG9GQ1ZYY01NVlEyTjYwc2NHblFmbFNHZ2tRIIEC; bili_jct=cf9b668e1717481315dc65261c3a6988; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjkzNDQ0NjksImlhdCI6MTcyOTA4NTIwOSwicGx0IjotMX0.fw5VRbRhlctvuW7_b1J8CL-77P2bU8R--JirtQrydjs; bili_ticket_expires=1729344409; buvid_fp=b6e4d2afbc3bd4672f0b7b6b8b23b7c0; b_lsid=54647B46_1929FD9ADA1; bmg_af_switch=1; bmg_src_def_domain=i1.hdslb.com; sid=flinb4ka; bp_t_offset_20047313=989647734438887424; home_feed_column=4; browser_resolution=1060-2101";

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
