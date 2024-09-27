package com.sugarweb.chatAssistant.controller;

import com.sugarweb.chatAssistant.application.RagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 许向东
 * @version 1.0
 */
@RestController
@RequestMapping("rag")
@Tag(description = "RAG服务", name = "rag")
public class ChatController {

    @Resource
    private RagService ragService;

    @PostMapping("chat")
    @Operation(description = "rag chat1", summary = "rag chat2")
    public Object chat(@RequestParam String question) {

        return "hello world";
    }

}
