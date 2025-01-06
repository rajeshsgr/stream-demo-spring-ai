package com.raj.nola.stream.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.RateLimit;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ResponseStreamer {

    private final ChatClient chatClient;


    public ResponseStreamer(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("/storyGen")
    public Flux<String> storytWithStream(@RequestParam(defaultValue = "Tell a story in less than 100 words") String message) {
        // 1. Get a prompt object from the chat client
        return chatClient.prompt()
                // 2. Set the user's input as the prompt
                .user(message)
                // 3. Stream the response from the chat client
                .stream()
                // 4. Extract and return the content of the streamed response
                .content();
    }

    @GetMapping("/storyNmetaData")
    public Flux<String> storyWithStreamMetaData(@RequestParam(defaultValue = "Tell a story in less than 100 words") String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .chatResponse()
                .doOnNext(chatResponse -> {
                    // Extract metadata from the ChatResponse
                    ChatResponseMetadata metadata = chatResponse.getMetadata();
                    RateLimit rateLimit = metadata.getRateLimit();
                    Usage usage = metadata.getUsage();

                    // Print metadata to the console
                    System.out.println("Model: " + metadata.getModel());
                    System.out.println("Requests Limit: " + rateLimit.getRequestsLimit());
                    System.out.println("Requests Remaining: " + rateLimit.getRequestsRemaining());
                    System.out.println("Tokens Reset: " + rateLimit.getTokensReset());
                    System.out.println("Tokens Limit: " + rateLimit.getTokensLimit());
                    System.out.println("Tokens Remaining: " + rateLimit.getTokensRemaining());
                    System.out.println("Prompt Tokens: " + usage.getPromptTokens());
                    System.out.println("Generation Tokens: " + usage.getGenerationTokens());
                })
                // Map the Flux<ChatResponse> to Flux<String> to return only the content
                .map(chatResponse -> chatResponse.getResult().getOutput().getContent());


    }


}
