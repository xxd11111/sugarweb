package com.sugarweb.digitalHuman.component.llm.thought;

/**
 * SteamThinkHandler
 *
 * @author xxd
 * @since 2024/10/19 14:02
 */
public interface StreamThoughtListener {

    void onNext(ThoughtRequest thoughtRequest, String token);

    void onComplete(ThoughtRequest thoughtRequest);

    void onError(ThoughtRequest thoughtRequest, Throwable error);

}
