package com.techie.springai.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final OllamaChatModel ollamaChatModel;
    private final VectorStore vectorStore;

    public ChatController(OllamaChatModel ollamaChatModel, VectorStore vectorStore) {
        this.ollamaChatModel = ollamaChatModel;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/my")
    public ResponseEntity<String> chat() {
        System.out.println("✅ /my endpoint called");
        String message = "who are the main charectors of the story?";

        String response = ChatClient.builder(ollamaChatModel)
                .build()
                .prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore ))
                .user(message)
                .call()
                .content();

        System.out.println("🤖 Model Response: " + response);

        // 👇 Return immediately as HTTP response
        return ResponseEntity.ok(response);
    }




    @PostMapping("/test")
    public String chatTEST(@RequestBody String message) {
        System.out.println("Received: " + message);
        var client = ChatClient.builder(ollamaChatModel).build();
        System.out.println("Client built");
        var prompt = client.prompt().advisors(new QuestionAnswerAdvisor(vectorStore)).user(message);
        System.out.println("Prompt prepared");
        return prompt.call().content();
    }

}
