package com.psem.Spring.boot.with.Ollama.controller;

import com.psem.Spring.boot.with.Ollama.model.Comment;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private final OllamaChatModel chatModel;

    @Autowired
    public ChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody Comment comment){

        String message = comment.getMessage();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                Map<String, String> basicAnswer = Map.of("generation",
                        this.chatModel.call("Is following sentence is a question, answer must contain only 1 word (yes or no)? " + message));

                return basicAnswer.get("generation").toLowerCase();
        });

        future1.thenAccept(result -> {
            System.out.println(result);
            if(result.contains("yes")){
                CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
                    Map<String, String> categoryAnswer = Map.of("generation",
                            this.chatModel.call("Which word from the list [bug, feature, billing, account] would " +
                                    "describe following sentece the most accurately " +
                                    "(pick word 'other' if you can't describe it with a given word from a list)? " + message));

                    return categoryAnswer.get("generation").toLowerCase();
                });

                future2.thenAccept(categoryAnswer -> {
                    System.out.println(categoryAnswer);

                    CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
                        Map<String, String> summary = Map.of("generation",
                                this.chatModel.call("Please provide short summary from 3 to 4 words of the following sentence " + message));
                        System.out.println(summary.get("generation").toLowerCase());
                        return summary.get("generation").toLowerCase();
                    });

                });
            }
        });


        // provide an answer depending on
        try {
            String answer = future1.get();

            if(answer.contains("yes")){
                return new ResponseEntity<>("Thank You for question. We will get back to you shortly.", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Thank you for taking the time to share your thoughts.", HttpStatus.OK);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}