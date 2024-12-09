package com.sugarweb.digitalHuman.infra;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecution;
import dev.langchain4j.service.tool.ToolExecutor;

import java.util.List;
import java.util.Map;

public class ServiceWithToolsExample {

    // Please also check CustomerSupportApplication and CustomerSupportApplicationTest
    // from spring-boot-example module

    static class Calculator {

        @Tool("计算字符串的长度")
        int stringLength(String s) {
            System.out.println("Called stringLength with s='" + s + "'");
            return s.length();
        }

        @Tool("计算两个数字的和")
        int add(int a, int b) {
            System.out.println("Called add with a=" + a + ", b=" + b);
            return 2 * a + b;
        }

        @Tool("计算一个数字的平方根")
        double sqrt(int x) {
            System.out.println("Called sqrt with x=" + x);
            return Math.sqrt(x);
        }
    }



    public static void main(String[] args) {
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("getWeather")
                .description("返回给定城市的天气预报")
                .parameters(JsonObjectSchema.builder()
                        .addStringProperty("city", "应返回天气预报的城市")
                        .addEnumProperty("temperatureUnit", List.of("摄氏度", "华氏温度"))
                        .required("city") // the required properties should be specified explicitly
                        .build())
                .build();
        // "What will the weather be like in London tomorrow?"

        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.193.151:11434")
                .modelName("qwen2.5:7b")
                .build();
        List<ToolSpecification> toolSpecifications = List.of(toolSpecification);
        UserMessage userMessage = UserMessage.from("伦敦明天的天气怎么样？然后4+5等于多少？");
        Response<AiMessage> response = model.generate(List.of(userMessage), toolSpecifications);
        AiMessage aiMessage = response.content();
        ToolExecutor toolExecutor = (toolExecutionRequest, memoryId) -> {
            DefaultToolExecutor defaultToolExecutor = new DefaultToolExecutor(new Calculator(), toolExecutionRequest);
            return defaultToolExecutor.execute(toolExecutionRequest, memoryId);
        };

        String result = "明天伦敦可能会下雨";
        if (aiMessage.hasToolExecutionRequests()){
            List<ToolExecutionRequest> toolExecutionRequests = aiMessage.toolExecutionRequests();
            ToolExecutionRequest first = toolExecutionRequests.getFirst();
            ToolExecutionResultMessage toolExecutionResultMessage = ToolExecutionResultMessage.from(first, result);
            List<ChatMessage> messages = List.of(userMessage, aiMessage, toolExecutionResultMessage);
            Response<AiMessage> response2 = model.generate(messages, toolSpecifications);
            System.out.println(response2);
        }else {
            System.out.println(response);
        }

    }
}