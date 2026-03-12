package com.yupi.config;

import com.yupi.service.AiCodeHelperService;
import com.yupi.tools.InterviewQuestionTool;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AiCodeHelperFactory {

    @Resource
    private ChatModel qwenChatModel;

    @Resource
    private ContentRetriever contentRetriever;

    @Bean
    public ChatMemory chatMemory() {
        // 简单的窗口记忆，保留最近 10 条消息
        return MessageWindowChatMemory.withMaxMessages(10);
    }


    @Bean
    @Primary
    public AiCodeHelperService aiCodeHelperService(ChatMemory chatMemory) {
        // 使用 LangChain4j AI Service + RAG（ContentRetriever）
        return AiServices.builder(AiCodeHelperService.class)
                .chatModel(qwenChatModel)
                .chatMemory(chatMemory)
               // .contentRetriever(contentRetriever)
                .tools(new InterviewQuestionTool())
                .build();
    }
}