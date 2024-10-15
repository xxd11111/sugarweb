package com.sugarweb.chatAssistant.agent.ability;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.application.PromptService;
import com.sugarweb.chatAssistant.domain.po.ChatMsg;
import com.sugarweb.chatAssistant.domain.po.PromptTemplateInfo;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
public class ThinkAbility {

    @Resource
    private MemoryAbility memoryAbility;

    private PromptService promptService = new PromptService();
    @Resource
    private StreamingChatLanguageModel chatLanguageModel;
    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    private SystemMessage getSystemMessage(String promptId, Map<String, String> contextVariables) {
        PromptTemplateInfo promptTemplateInfo = promptService.getSystemPrompt(promptId);
        String templateContent = promptTemplateInfo.getContent();

        PromptTemplate systemPromptTemplate = new PromptTemplate(templateContent);
        Prompt systemPrompt = systemPromptTemplate.apply(contextVariables);
        return new SystemMessage(systemPrompt.text());
    }

    private UserMessage getUserMessage(String promptId, Map<String, String> contextVariables) {
        PromptTemplateInfo promptTemplateInfo = promptService.getUserPrompt(promptId);
        PromptTemplate userPromptTemplate = new PromptTemplate(promptTemplateInfo.getContent());
        Prompt userPrompt = userPromptTemplate.apply(contextVariables);
        return new UserMessage(userPrompt.text());
    }

    @Transactional(rollbackFor = Exception.class)
    public void streamThink(ThinkContent thinkContent, StreamingResponseHandler<AiMessage> extHandler) {
        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        ChatMsg systemChatMsg = thinkContent.getSystemMessage();
        if (systemChatMsg != null) {
            ChatMessage systemMessage = new SystemMessage(systemChatMsg.getContent());
            messageList.add(systemMessage);
        }

        //第二步，获取ai对话历史消息
        List<ChatMsg> historyMessage = thinkContent.getHistoryMessage();
        if (historyMessage != null && !historyMessage.isEmpty()) {
            for (ChatMsg chatMsg : historyMessage) {
                ChatMessage chatMessage = getChatMessage(chatMsg);
                messageList.add(chatMessage);
            }
        }

        //第三步，获取当前提问的消息
        String question = thinkContent.getCurrentQuestion().getContent();
        messageList.add(new UserMessage(question));

        chatLanguageModel.generate(messageList, extHandler);

    }



    private ChatMessage getChatMessage(ChatMsg chatMsg) {
        if (ChatRole.USER.getCode().equals(chatMsg.getChatRole())) {
            return new UserMessage(chatMsg.getContent());
        } else if (ChatRole.ASSISTANT.getCode().equals(chatMsg.getChatRole())) {
            return new AiMessage(chatMsg.getContent());
        } else if (ChatRole.SYSTEM.getCode().equals(chatMsg.getChatRole())) {
            return new SystemMessage(chatMsg.getContent());
        }
        throw new IllegalArgumentException(StrUtil.format("不支持的消息类型,messageId:{}", chatMsg.getMsgId()));
    }


}
