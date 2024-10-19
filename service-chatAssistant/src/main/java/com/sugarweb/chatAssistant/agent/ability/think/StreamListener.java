package com.sugarweb.chatAssistant.agent.ability.think;

/**
 * SteamThinkHandler
 *
 * @author xxd
 * @since 2024/10/19 14:02
 */
public interface StreamListener {

    void onNext(ThinkContext thinkContext, String token);

    void onComplete(ThinkContext thinkContext);

    void onError(ThinkContext thinkContext, Throwable error);

}
