package com.psem.springBootWithOllama.controller;

import com.psem.springBootWithOllama.model.Category;
import com.psem.springBootWithOllama.model.Comment;
import com.psem.springBootWithOllama.model.Ticket;
import com.psem.springBootWithOllama.payload.CommentDTO;
import com.psem.springBootWithOllama.payload.CommentRequest;
import com.psem.springBootWithOllama.payload.CommentResponse;
import com.psem.springBootWithOllama.repository.CommentRepository;
import com.psem.springBootWithOllama.service.CategoryService;
import com.psem.springBootWithOllama.service.CommentService;
import com.psem.springBootWithOllama.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api")
public class CommentController {

    private final OllamaChatModel chatModel;

    @Autowired
    public CommentController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Tag(name="Comment API", description = "APIs for managing comments and issue a ticket by default if needed")
    @Operation(summary = "Get all comments")
    @GetMapping("/comments")
    public ResponseEntity<CommentResponse> getAllComments(){

        CommentResponse commentResponse = commentService.getAllComments();

        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @Tag(name="Comment API")
    @Operation(summary = "Add new comment")
    @PostMapping("/comments")
    public ResponseEntity<?> addCommentIssueTicket(@RequestBody CommentRequest comment){

        String message = comment.getMessage();
        Comment savedComment = commentService.createComment(comment);
        CommentDTO commentDTO = modelMapper.map(savedComment, CommentDTO.class);

        Ticket ticket = new Ticket();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                Map<String, String> basicAnswer = Map.of("generation",
                        // ask AI if provided message is a question or a statement
                        this.chatModel.call("Is following sentence is a question, answer must contain only 1 word (yes or no)? " + message));

                return basicAnswer.get("generation").toLowerCase();
        });


        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try{
                String isTicket = future1.get();
                // if provided message is a question, create new ticket and set required information from a comment.
                if(isTicket.contains("yes")){
                ticket.setWebUrl(comment.getWebUrl());
                ticket.setComment(savedComment);

                    Map<String, String> categoryAnswer = Map.of("generation",
                            // ask AI to assign a message one out of 5 possible categories
                            this.chatModel.call("Answer in one word, which word from the list [bug, feature, billing, account, other] " +
                                    "describes the following sentece the most accurately? " + message));

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

                    // pass AI answer about category and add new category, if it doesn't exist.
                    Category category = categoryService.checkIfCategoryExists(categoryAnswer);

                    // since checkIfCategoryExists method returns a category, set category for ticket
                    ticket.setCategory(category);

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
//                            int summaryLength = summary.length();
                            // if summary is too long, the bigger chance that end of it will be more meaningful.
//                            String shortSummary = summary.substring(summaryLength - 50);
                            String shortSummary = summary.substring(0, 50);
                            ticket.setSummary(shortSummary);
                            ticketService.createTicket(ticket);
                        } else {
                            ticket.setSummary(summary);
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
            // if comment is a statement not a question, provide response with http status code 201.
            else{
                commentDTO.setAnswered(true);
                return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
            }

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Tag(name="Comment API")
    @Operation(summary = "Delete comment")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){

        commentService.deleteComment(commentId);

        return new ResponseEntity<>("Comment with ID: " + commentId +" was successfully deleted!", HttpStatus.OK);
    }

}