package com.psem.Spring.boot.with.Ollama.controller;

import com.psem.Spring.boot.with.Ollama.model.Category;
import com.psem.Spring.boot.with.Ollama.model.Comment;
import com.psem.Spring.boot.with.Ollama.model.Ticket;
import com.psem.Spring.boot.with.Ollama.payload.CommentDTO;
import com.psem.Spring.boot.with.Ollama.payload.CommentRequest;
import com.psem.Spring.boot.with.Ollama.service.CategoryService;
import com.psem.Spring.boot.with.Ollama.service.CommentService;
import com.psem.Spring.boot.with.Ollama.service.TicketService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody CommentRequest comment){


        String message = comment.getMessage();
        Comment savedComment = commentService.createComment(comment);
        CommentDTO commentDTO = modelMapper.map(savedComment, CommentDTO.class);
        Ticket ticket = new Ticket();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                Map<String, String> basicAnswer = Map.of("generation",
                        this.chatModel.call("Is following sentence is a question, answer must contain only 1 word (yes or no)? " + message));

                return basicAnswer.get("generation").toLowerCase();
        });


        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try{
                String isTicket = future1.get();
                // if provided message is a question, create new ticket and set received information from a comment.
                if(isTicket.contains("yes")){
                ticket.setTitle(comment.getTitle());
                ticket.setWebUrl(comment.getWebUrl());
                ticket.setComment(savedComment);

                System.out.println("Ticket po future1: " + ticket.toString());
                    Map<String, String> categoryAnswer = Map.of("generation",
                            this.chatModel.call("Which word from the list [bug, feature, billing, account] would " +
                                    "describe following sentece the most accurately " +
                                    "(pick word 'other' if you can't describe it with a given word from a list)? " + message));

                    return categoryAnswer.get("generation").toLowerCase();
                }
                else{
                    return null;
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {

            try{
                String categoryAnswer = future2.get();
                if(categoryAnswer != null){
                    System.out.println(categoryAnswer);

                    // pass AI answer about category and add new category, if it doesn't exist.
                    Category category = categoryService.checkIfCategoryExists(categoryAnswer);

                    // since checkIfCategoryExists method returns a category, set category for ticket
                    ticket.setCategory(category);

                    System.out.println(ticket.toString());
                    Map<String, String> summary = Map.of("generation",
                            this.chatModel.call("Please summarize following sentece and provide answer not longer than 5 words. " + message));

                    return summary.get("generation").toLowerCase();
                }

                else{
                    return null;
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
                });

        // check if ticket needs to be issued and provide response with http code 202 as soon as ticket is saved.
        try {
            String isTicket = future1.get();

            if(isTicket.contains("yes")){
                try{
                    // since AI answer can be unreliable, substring summary to appropriate length to prevent from an error
                    // while saving ticket in database
                    String summary = future3.get();
                    if(summary != null){

                        if(summary.length() > 50){
                            ticket.setSummary(summary.substring(50));
                            ticketService.createTicket(ticket);
                        } else {
                            ticketService.createTicket(ticket);
                        }
                        return new ResponseEntity<>(commentDTO, HttpStatus.ACCEPTED);
                    }
                    else{
                        return new ResponseEntity<>("Something went wrong while issuing a ticket", HttpStatus.BAD_REQUEST);
                    }

                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
            }

            }
            // if comment is a statement not a question, provide response with http status code 200.
            else{
                return new ResponseEntity<>(commentDTO, HttpStatus.OK);
            }

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}