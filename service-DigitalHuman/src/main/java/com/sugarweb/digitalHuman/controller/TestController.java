package com.sugarweb.digitalHuman.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.sugarweb.framework.common.R;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.output.Response;
import lombok.Data;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("ai")
@SaIgnore
public class TestController {
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestParam String question) {
        SseEmitter emitter = new SseEmitter();
        OllamaStreamingChatModel chatModel = OllamaStreamingChatModel.builder()
                .baseUrl("http://192.168.193.151:11434")
                .modelName("qwen2.5:3b")
                .build();
        chatModel.generate(question, new StreamingResponseHandler<>() {
            String answer = "";

            @Override
            public void onNext(String token) {
                answer = answer + token;
                AiResponse aiResponse = new AiResponse();
                aiResponse.setEnd(false);
                aiResponse.setText(answer);
                try {
                    emitter.send(SseEmitter.event().data(R.data(aiResponse)));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }

            @Override
            public void onError(Throwable error) {
                try {
                    emitter.send(SseEmitter.event().data(R.error(error.getMessage())));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                AiResponse aiResponse = new AiResponse();
                aiResponse.setEnd(true);
                aiResponse.setText(response.content().text());
                try {
                    emitter.send(SseEmitter.event().data(R.data(aiResponse)));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
                emitter.complete();
            }
        });
        return emitter;
    }

    @Data
    public class AiResponse {
        private String text;
        private Boolean end;
    }

    @PostMapping(value = "/block")
    public R block(@RequestParam String question) {
        OllamaChatModel chatModel = OllamaChatModel.builder()
                .baseUrl("http://192.168.193.151:11434")
                .modelName("qwen2.5:3b")
                .build();
        AiResponse aiResponse = new AiResponse();
        String generate = chatModel.generate(question);
        aiResponse.setText(generate);
        return R.data(aiResponse);
    }

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS) // 设置读取超时为0，保持连接打开
                .build();

        // 创建FormData参数
        RequestBody formBody = new FormBody.Builder()
                .add("question", "你好")
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.193.153:18080/ai/stream")
                .post(formBody)
                .build();
        EventSource.Factory factory = EventSources.createFactory(client);
        EventSourceListener listener = new EventSourceListener() {
            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                System.out.println("onClosed");
            }

            @Override
            public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
                System.out.println("onEvent: " + data);
            }

            @Override
            public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable okhttp3.Response response) {
                try {
                    String string = response.body().string();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("onFailure: " + t);
            }

            @Override
            public void onOpen(@NotNull EventSource eventSource, @NotNull okhttp3.Response response) {
                System.out.println("onClosed");
            }
        };

        EventSource eventSource = factory.newEventSource(request, listener);

        Request request2 = new Request.Builder()
                .url("http://192.168.193.153:18080/ai/block")
                .post(new FormBody.Builder()
                        .add("question", "你好")
                        .build())
                .build();
        try {
            okhttp3.Response execute = client.newCall(request2).execute();
            String string = execute.body().string();
            System.out.println(string);
        }catch (Exception e){
            //
        }

        // 保持主线程运行，以便接收事件
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
