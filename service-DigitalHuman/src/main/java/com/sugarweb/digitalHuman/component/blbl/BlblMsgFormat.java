package com.sugarweb.digitalHuman.component.blbl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.entity.BlblUser;

import java.time.format.DateTimeFormatter;

/**
 * BlblMsgAdapt
 *
 * @author xxd
 * @version 1.0
 */
public class BlblMsgFormat {

    /**
     * 相当于写死的提示词
     */
    public static String getFormatContent(Object o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (o instanceof BlblDmMsg dmMsg) {
            return StrUtil.format("时间：{}，收到一条互动消息，用户：{}，内容：{}", dmMsg.getTime().format(formatter), dmMsg.getUsername(), dmMsg.getContent());
        } else if (o instanceof BlblGiftMsg giftMsg) {
            return StrUtil.format("时间：{}，收到用户送的礼物，用户：{}，礼物名：{}，价格：{}，数量：{}", giftMsg.getTime().format(formatter), giftMsg.getUsername(), giftMsg.getGifyName(), giftMsg.getGiftPrice(), giftMsg.getGiftNum());
        } else if (o instanceof BlblLikeMsg likeMsg) {
            return StrUtil.format("时间：{}，收到用户点赞，用户：{}，点赞量：{}", likeMsg.getTime().format(formatter), likeMsg.getUsername(), likeMsg.getLikeNum());
        } else if (o instanceof BlblEnterRoomMsg enterRoomMsg) {
            return StrUtil.format("时间：{}，用户进入房间，用户：{}", enterRoomMsg.getTime().format(formatter), enterRoomMsg.getUsername());
        } else if (o instanceof BlblCountMsg countMsg) {
            return StrUtil.format("时间：{}，房间定时统计，累计观看人数：{}，正在观看人数：{}，累计点赞数：{}", countMsg.getTime().format(formatter), countMsg.getWatchedCount(), countMsg.getWatchingCount(), countMsg.getLikeCount());
        }
        return "";
    }


    public static BlblUser getUser(Object o) {
        String uid = "";
        if (o instanceof BlblDmMsg dmMsg) {
            uid = dmMsg.getBlblUid();
        } else if (o instanceof BlblGiftMsg giftMsg) {
            uid = giftMsg.getBlblUid();
        } else if (o instanceof BlblLikeMsg likeMsg) {
            uid = likeMsg.getBlblUid();
        } else if (o instanceof BlblEnterRoomMsg enterRoomMsg) {
            uid = enterRoomMsg.getBlblUid();
        }
        return Db.getById(uid, BlblUser.class);
    }

}
