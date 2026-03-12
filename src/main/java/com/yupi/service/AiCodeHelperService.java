package com.yupi.service;

import dev.langchain4j.service.SystemMessage;

public interface AiCodeHelperService {

    @SystemMessage(fromResource = "system-prompt.txt")
    public String chat(String message);

}
