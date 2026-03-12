package com.yupi.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 简单 RAG 配置：加载 resources/docs 下的文档，写入内存向量库，并提供 ContentRetriever。
 */
@Configuration
public class RagConfig {

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    public ContentRetriever contentRetriever(
            EmbeddingStore<TextSegment> embeddingStore
    ) {
        // 1. 加载文档（如果没有 docs 目录，会得到空列表）
        List<Document> documents =
                FileSystemDocumentLoader.loadDocuments("src/main/resources/docs");

        if (documents != null && !documents.isEmpty()) {
            System.out.println("RAG: found " + documents.size()
                    + " document(s) under src/main/resources/docs, start ingest...");
            // 2. 将文档写入内存向量库（使用 EmbeddingStoreIngestor 简化处理）
            EmbeddingStoreIngestor.ingest(documents, embeddingStore);
            System.out.println("RAG: ingestion finished.");
        } else {
            System.out.println("RAG: no documents found under src/main/resources/docs");
        }

        // 3. 构造基于向量库的 ContentRetriever
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .maxResults(5)
                .build();
    }
}

