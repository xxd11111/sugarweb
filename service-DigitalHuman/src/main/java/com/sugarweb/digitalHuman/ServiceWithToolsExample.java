package com.sugarweb.digitalHuman;

import dev.langchain4j.agent.tool.*;
import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.tool.DefaultToolExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceWithToolsExample {

    static class DemoTool {
        @Tool("这是一个用户注册的功能")
        String userRegistry(@P("用户名") String username,
                            @P("密码") String password,
                            @P("用户创建时间") String registerTime,
                            @P(value = "邮箱", required = false) String email) {
            System.out.println("注册方法调用：username='" + username + "' and password='" + password + "'" + " and email='" + email + "'" + " and registerTime='" + registerTime);
            return "注册成功";
        }

        @Tool("这是一个获取现在时间的功能")
        String now() {
            System.out.println("获取时间方法调用");
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        }

        @Tool("这是一个行为输出的功能")
        String animation(@P("动画列表，1.坐，2.起身，3.走路，4.躺着") String animationType) {
            System.out.println("动画类型：" + animationType);
            return "动画类型:" + animationType;
        }

    }

    public static void main(String[] args) {
        // ollama模型
        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.193.151:11434")
                .modelName("qwen2.5:7b")
                .build();
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("getWeather")
                .description("返回给定城市的天气预报")
                .parameters(JsonObjectSchema.builder()
                        .addStringProperty("city", "应返回天气预报的城市")
                        .addEnumProperty("temperatureUnit", List.of("摄氏度", "华氏温度"))
                        .required("city") // the required properties should be specified explicitly
                        .build())
                .build();
        // 工具调用配置
        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(DemoTool.class);
        toolSpecifications.add(toolSpecification);
        List<ChatMessage> messages = new ArrayList<>();
        SystemMessage from = SystemMessage.from("""
                参考以下示例，表达你的想法和输出你的行为。
                
                示例1：
                问题：
                天空乌云密布，马上要下雨了。
                回答：
                这个天气我还是坐在家里玩玩游戏吧，不太想出门了。
                ---
                动画类型:1
                
                示例2：
                问题：
                有人给你送了个礼物。
                回答：
                哇，你真好，礼物里有什么。
                ---
                动画类型:2
                
                """);
        messages.add(from);
        UserMessage userMessage = UserMessage.from("天上有很多云");
        messages.add(userMessage);
        // 调用模型

        ChatResponse response = model.chat(ChatRequest.builder()
                .messages(messages)
                .toolSpecifications(toolSpecifications)
                .build());
        AiMessage aiMessage = response.aiMessage();
        System.out.println(aiMessage);
        messages.add(aiMessage);
        AiMessage functionCallMessage = functionExecute(aiMessage, model, messages, toolSpecifications);
    }

    public static AiMessage functionExecute(AiMessage aiMessage, OllamaChatModel model, List<ChatMessage> historyMessage, List<ToolSpecification> toolSpecifications) {
        if (!aiMessage.hasToolExecutionRequests()) {
            return aiMessage;
        }
        List<ToolExecutionRequest> toolExecutionRequests = aiMessage.toolExecutionRequests();
        for (ToolExecutionRequest toolExecutionRequest : toolExecutionRequests) {
            // 执行工具调用
            DefaultToolExecutor toolExecutor = new DefaultToolExecutor(new DemoTool(), toolExecutionRequest);
            String result = toolExecutor.execute(toolExecutionRequest, null);
            ToolExecutionResultMessage toolExecutionResultMessage = ToolExecutionResultMessage.from(toolExecutionRequest, result);
            historyMessage.add(toolExecutionResultMessage);
        }
        // 再次调用模型
        ChatResponse newResponse = model.chat(ChatRequest.builder()
                .messages(historyMessage)
                .toolSpecifications(toolSpecifications)
                .build());
        AiMessage newAiMessage = newResponse.aiMessage();
        System.out.println(newAiMessage);
        return functionExecute(newAiMessage, model, historyMessage, toolSpecifications);
    }
}