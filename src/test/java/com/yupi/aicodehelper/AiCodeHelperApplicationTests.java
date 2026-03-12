package com.yupi.aicodehelper;

import com.yupi.service.AiCodeHelperService;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AiCodeHelperApplicationTests {

    @Resource
    private AiCodeHelperService aiCodeHelper;

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    @Resource
    private ContentRetriever contentRetriever;

    @Test
    void contextLoads() {
        // 容器能启动即可
    }

    @Test
    void chat() {
        String result = aiCodeHelper.chat("请介绍一下这个 AI 编程小助手项目用了哪些技术栈。");
        System.out.println(result);
        assertNotNull(result);
    }

    /**
     * 简单 RAG 验证：确保 docs 被成功写入向量库，并且可以被检索到。
     */
    @Test
    void simpleRagValidation() {
        var query = dev.langchain4j.rag.query.Query.from("这个 AI 编程小助手项目用了哪些技术栈？");
        var retrieved = contentRetriever.retrieve(query);
        System.out.println("RAG retrieved segments: " + retrieved);
        assertNotNull(retrieved);
        assertFalse(retrieved.isEmpty(), "RAG 检索结果不应为空（需要 docs/demo-rag.md 被成功向量化）");
    }
}