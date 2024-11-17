package com.sugarweb.digitalHuman.infra.llm.thought;

/**
 * SteamThinkHandler
 *
 * @author xxd
 * @since 2024/10/19 14:02
 */
public interface StreamListener {

    void onNext(ThoughtContext thoughtContext, String token);

    void onComplete(ThoughtContext thoughtContext);

    void onError(ThoughtContext thoughtContext, Throwable error);

}
