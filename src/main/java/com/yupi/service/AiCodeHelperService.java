package com.yupi.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface AiCodeHelperService {

    //@SystemMessage(fromResource = "system-prompt.txt")
    public Result<String> chat(String message);

    Flux<String> chatStream(@MemoryId int memoryId, @UserMessage String userMessage);
}
