package com.psem.Spring.boot.with.Ollama.controller;

import com.psem.Spring.boot.with.Ollama.model.Category;
import com.psem.Spring.boot.with.Ollama.model.Comment;
import com.psem.Spring.boot.with.Ollama.model.Ticket;
import com.psem.Spring.boot.with.Ollama.service.CategoryService;
import com.psem.Spring.boot.with.Ollama.service.TicketService;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TicketService ticketService;

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

                // if provided message is a question, create new ticket and set received information from a comment.
                Ticket ticket = new Ticket();
                ticket.setTitle(comment.getTitle());
                ticket.setWebUrl(comment.getWebUrl());

                System.out.println("Ticket po future1: " + ticket.toString());

                // generate answer which suppose to provide keyword about relevant category
                CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
                    Map<String, String> categoryAnswer = Map.of("generation",
                            this.chatModel.call("Which word from the list [bug, feature, billing, account] would " +
                                    "describe following sentece the most accurately " +
                                    "(pick word 'other' if you can't describe it with a given word from a list)? " + message));

                    return categoryAnswer.get("generation").toLowerCase();
                });


                future2.thenAccept(categoryAnswer -> {
                    System.out.println(categoryAnswer);

                    // pass AI answer about category and add new category, if it doesn't exist.
                    Category category = categoryService.checkIfCategoryExists(categoryAnswer);

                    // since checkIfCategoryExists method returns a category, set category for ticket
                    ticket.setCategory(category);

                    System.out.println(ticket.toString());

                    CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
                        Map<String, String> summary = Map.of("generation",
                                this.chatModel.call("Please provide short summary from 3 to 4 words of the following sentence " + message));

                        return summary.get("generation").toLowerCase();
                    });

                    // set summary for ticket from 3rd AI response and move forward to TicketServiceImpl to save it in database.
                    try{
                        String summary = future3.get();
                        System.out.println("Summary: " + summary);
                        ticket.setSummary(summary);
                        ticketService.createTicket(ticket);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                });
            }
        });


        // provide default answer if comment is statement, else provide informational message that ticket is being under investigation.
        try {
            String answer = future1.get();

            if(answer.contains("yes")){
                return new ResponseEntity<>("Thank You. Your ticket has been registered. " +
                        "We will provide an answer as soon as possible", HttpStatus.ACCEPTED);
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