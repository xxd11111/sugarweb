package com.sugarweb.chatAssistant.agent.ability;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.application.PromptService;
import com.sugarweb.chatAssistant.domain.po.MessageInfo;
import com.sugarweb.chatAssistant.domain.po.PromptInfo;
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
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        PromptInfo promptInfo = promptService.getSystemPrompt(promptId);
        String templateContent = promptInfo.getContent();

        PromptTemplate systemPromptTemplate = new PromptTemplate(templateContent);
        Prompt systemPrompt = systemPromptTemplate.apply(contextVariables);
        return new SystemMessage(systemPrompt.text());
    }

    private UserMessage getUserMessage(String promptId, Map<String, String> contextVariables) {
        PromptInfo promptInfo = promptService.getUserPrompt(promptId);
        PromptTemplate userPromptTemplate = new PromptTemplate(promptInfo.getContent());
        Prompt userPrompt = userPromptTemplate.apply(contextVariables);
        return new UserMessage(userPrompt.text());
    }

    @Transactional(rollbackFor = Exception.class)
    public void streamThink(ThinkContent thinkContent, StreamingResponseHandler<AiMessage> extHandler) {
        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        MessageInfo systemMessageInfo = thinkContent.getSystemMessage();
        if (systemMessageInfo != null) {
            ChatMessage systemMessage = new SystemMessage(systemMessageInfo.getContent());
            messageList.add(systemMessage);
        }

        //第二步，获取ai对话历史消息
        List<MessageInfo> historyMessage = thinkContent.getHistoryMessage();
        if (historyMessage != null && !historyMessage.isEmpty()) {
            for (MessageInfo messageInfo : historyMessage) {
                ChatMessage chatMessage = getChatMessage(messageInfo);
                messageList.add(chatMessage);
            }
        }

        //第三步，获取当前提问的消息
        String question = thinkContent.getCurrentQuestion().getContent();
        messageList.add(new UserMessage(question));

        chatLanguageModel.generate(messageList, extHandler);

    }

    private MessageInfo createChatMessageInfo(String type, String question, String memoryId) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMemoryId(memoryId);
        messageInfo.setContent(question);
        messageInfo.setMsgType(type);
        messageInfo.setCreateTime(LocalDateTime.now());
        messageInfo.setUpdateTime(LocalDateTime.now());
        return messageInfo;
    }

    private ChatMessage getChatMessage(MessageInfo messageInfo) {
        if ("user".equals(messageInfo.getMsgType())) {
            return new UserMessage(messageInfo.getContent());
        } else if ("ai".equals(messageInfo.getMsgType())) {
            return new AiMessage(messageInfo.getContent());
        } else if ("system".equals(messageInfo.getMsgType())) {
            return new SystemMessage(messageInfo.getContent());
        }
        throw new IllegalArgumentException(StrUtil.format("不支持的消息类型,messageId:{}", messageInfo.getMsgId()));
    }


}
