package com.psem.Spring.boot.with.Ollama.controller;

import com.psem.Spring.boot.with.Ollama.utils.IsNumeric;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private final OllamaChatModel chatModel;

    @Autowired
    public ChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/generate")
    public void generate(@RequestBody String message){

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                Map<String, String> basicAnswer = Map.of("generation",
                        this.chatModel.call("Is following sentence is a question, answer must contain only 1 word (yes or no)? " + message));
            System.out.println(basicAnswer);
                return basicAnswer.get("generation").toLowerCase();
        });

        future.thenAccept(result -> {
            if(result.contains("yes")){
                CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
                    Map<String, String> rating = Map.of("generation",
                            this.chatModel.call("Rate from 1 to 5, how hard to answer following question? " + message));

                    return rating.get("generation").toLowerCase();
                });

                future2.thenAccept(rate -> {

                    System.out.println("rate: " + rate);

                    IsNumeric isNumber = new IsNumeric(rate);
                    System.out.println(isNumber.isNumeric());

                    if(isNumber.isNumeric()){
                        System.out.println(rate);
                        System.out.println(this.chatModel.call("In which questions category would you add this question, tell me category name only? " + message));
                    }
                    else{
                        System.out.println("my question");
                        System.out.println(this.chatModel.call(message));
                    }
                });
            }
        });
    }

//    @GetMapping("/ai/generateStream")
//    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        Prompt prompt = new Prompt(new UserMessage(message));
//        return this.chatModel.stream(prompt);
//    }

}