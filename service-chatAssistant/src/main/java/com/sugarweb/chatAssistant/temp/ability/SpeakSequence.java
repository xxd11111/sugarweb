package com.sugarweb.chatAssistant.temp.ability;

import java.util.*;

/**
 * SpeakSequence
 * 用来解决聊天内容分片播放时序问题
 *
 * @author xxd
 * @version 1.0
 */
public class SpeakSequence {

    private Map<Long, List<String>> splitIdMap = new HashMap<>();

    private Queue<Long> chatIdQueue = new LinkedList<>();

    private List<Long> waitingChatIdList = new ArrayList<>();

    private long currentChatId() {
        return 0;
    }

    private long next() {
        return 0;
    }

    public long registerChatId() {
        return 0;
    }

    public int registerSplitId(long chatId) {
        return 0;
    }


}
