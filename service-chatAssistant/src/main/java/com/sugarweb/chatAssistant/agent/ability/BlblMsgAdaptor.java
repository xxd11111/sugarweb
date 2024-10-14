package com.sugarweb.chatAssistant.agent.ability;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.domain.po.ChatMessageInfo;
import com.sugarweb.chatAssistant.domain.po.PromptTemplateInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * BlblMsgAdapt
 *
 * @author xxd
 * @version 1.0
 */
public class BlblMsgAdaptor {

    public static String assemblySingleMsg(Object o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (o instanceof BlblDmMsg dmMsg) {
            return StrUtil.format("时间：{}，收到一条互动消息，用户：{}，内容：{}\n", dmMsg.getTime().format(formatter), dmMsg.getUsername(), dmMsg.getContent());
        } else if (o instanceof BlblGiftMsg giftMsg) {
            return StrUtil.format("时间：{}，收到用户送的礼物，用户：{}，礼物名：{}，价格：{}，数量：{}\n", giftMsg.getTime().format(formatter), giftMsg.getUsername(), giftMsg.getGifyName(), giftMsg.getGiftPrice(), giftMsg.getGiftNum());
        } else if (o instanceof BlblLikeMsg likeMsg) {
            return StrUtil.format("时间：{}，收到用户点赞，用户：{}，点赞量：{}\n", likeMsg.getTime().format(formatter), likeMsg.getUsername(), likeMsg.getLikeNum());
        } else if (o instanceof BlblEnterRoomMsg enterRoomMsg) {
            return StrUtil.format("时间：{}，用户进入房间，用户：{}\n", enterRoomMsg.getTime().format(formatter), enterRoomMsg.getUsername());
        } else if (o instanceof BlblCountMsg countMsg) {
            return StrUtil.format("时间：{}，房价定时统计，累计观看人数：{}，正在观看人数：{}，累计点赞数：{}\n", countMsg.getTime().format(formatter), countMsg.getWatchedCount(), countMsg.getWatchingCount(), countMsg.getLikeCount());
        }
        return "";
    }


    public static String assemblyMultiMsg(List<Object> msgList) {
        StringBuilder multiText = new StringBuilder();
        for (Object msg : msgList) {
            String text = BlblMsgAdaptor.assemblySingleMsg(msg);
            if (StrUtil.isNotEmpty(text)) {
                multiText.append(text);
            }
        }
        return multiText.toString();
    }

}
