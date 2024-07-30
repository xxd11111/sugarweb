package com.sugarweb;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * TODO
 *
 * @author 许向东
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final ChatMemory chatMemory;

    public String chat(String chatId, String question) {

        var systemPromptTemplate = """
                你是一个乐于助人的助手，与用户谈论一组文档中包含的主题。
                使用文档部分的信息提供准确的答案。
                如果不确定或在文档部分找不到答案，只需说明您不知道答案，不要提及文档部分。
                                
                文档:
                // {documents}
                """;

        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(question).withTopK(5));

        String content = similarDocuments.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()));

        String content1 = chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(systemPromptTemplate)
                        .param("documents", content)
                )
                // .advisors(new PromptChatMemoryAdvisor(chatMemory),
                //         new MessageChatMemoryAdvisor(chatMemory), // CHAT MEMORY
                        // new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()),
                        // new SimpleLoggerAdvisor())
                // .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                //         .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .user(question)
                .call()
                .content();


        return content1;


    }

}
