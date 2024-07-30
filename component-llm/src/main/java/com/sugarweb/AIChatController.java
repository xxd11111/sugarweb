package com.sugarweb;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
class AIChatController {

	private final ChatService chatService;

	@GetMapping("/ai/chat")
	public String chat(@RequestParam(value = "message", defaultValue = "讲个笑话") String question) {
		return chatService.chat("1", question);
	}

}