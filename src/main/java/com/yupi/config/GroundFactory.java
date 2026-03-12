package com.yupi.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroundFactory {
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        // 使用内存向量库，适合小规模数据
        return new InMemoryEmbeddingStore<>();
    }
}
