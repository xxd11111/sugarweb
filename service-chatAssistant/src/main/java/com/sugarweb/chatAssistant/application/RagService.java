package com.sugarweb.chatAssistant.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * TODO
 *
 * @author xxd
 * @since 2024/9/26 21:34
 */
@Slf4j
public class RagService {

    public static void main(String[] args) {
        OllamaApi ollamaApi = new OllamaApi("http://localhost:11434");

// Sync request
        var request = OllamaApi.ChatRequest.builder("qwen2.5:3b")
                .withStream(false) // not streaming
                .withMessages(List.of(
                        OllamaApi.Message.builder(OllamaApi.Message.Role.SYSTEM)
                                .withContent("You are a geography teacher. You are talking to a student.")
                                .build(),
                        OllamaApi.Message.builder(OllamaApi.Message.Role.USER)
                                .withContent("What is the capital of Bulgaria and what is the size? "
                                        + "What is the national anthem?")
                                .build()))
                .withOptions(OllamaOptions.create().withTemperature(0.9))
                .build();

        OllamaApi.ChatResponse response = ollamaApi.chat(request);
        System.out.println("==============");
        System.out.println(response);
        System.out.println("==============");

        // Streaming request
        var request2 = OllamaApi.ChatRequest.builder("qwen2.5:3b")
                .withStream(true) // streaming
                .withMessages(List.of(OllamaApi.Message.builder(OllamaApi.Message.Role.USER)
                        .withContent("What is the capital of Bulgaria and what is the size? " + "What is the national anthem?")
                        .build()))
                .withOptions(OllamaOptions.create().withTemperature(0.9).toMap())
                .build();

        System.out.println("==============");
        Flux<OllamaApi.ChatResponse> streamingResponse = ollamaApi.streamingChat(request2);
        System.out.println("==============");
        streamingResponse.subscribe(a-> System.out.println(a));
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // private static OllamaChatModel model = OllamaChatModel.builder()
    //         .logRequests(true)
    //         .logResponses(true)
    //         .baseUrl("http://localhost:11434")
    //         .modelName("qwen2.5:3b")
    //         .build();
    //
    // public Object chat(String promptId, String memoryId, String question) {
    //     List<ChatMessageInfo> chatMessageInfoList = getChatMessageList(memoryId);
    //     String promptTemplate = getPromptTemplate(promptId);
    //     String prompt = assemblePrompt(promptTemplate, question, chatMessageInfoList, new ArrayList<>());
    //     String generate = model.generate(prompt);
    //     System.out.println(generate);
    //     return generate;
    // }
    //
    // public String assemblePrompt(String promptTemplate, String question, List<ChatMessageInfo> chatMessageInfoList, List<String> context) {
    //     String contextStr = JSONUtil.toJsonStr(context);
    //     String chatMessageStr = JSONUtil.toJsonStr(chatMessageInfoList);
    //
    //     return StrUtil.replace(promptTemplate, "{question}", question)
    //             .replace("{documents}", contextStr)
    //             .replace("{context}", chatMessageStr);
    // }
    //
    // private List<ChatMessageInfo> getChatMessageList(String memoryId) {
    //     return new ArrayList<>();
    // }
    //
    // private String getPromptTemplate(String promptId) {
    //     return """
    //             现在你是直播聊天机器人，请根据事件，提示上下文，提示资料来响应内容给观众，回答内容要求简洁，活泼。
    //             事件：
    //             --------------------------------
    //             {question}
    //             --------------------------------
    //             提示上下文：
    //             --------------------------------
    //             无
    //             --------------------------------
    //             提示资料：
    //             --------------------------------
    //             无
    //             --------------------------------
    //             """;
    // }

}
