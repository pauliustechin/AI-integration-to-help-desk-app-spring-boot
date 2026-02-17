package com.psem.springBootWithOllama.controller;

import com.psem.springBootWithOllama.payload.TicketDTO;
import com.psem.springBootWithOllama.payload.TicketResponse;
import com.psem.springBootWithOllama.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Tag(name="Ticket API", description = "APIs for receiving data about tickets")
    @Operation(summary = "Get all tickets")
    @GetMapping("/tickets")
    public ResponseEntity<TicketResponse> getAllTickets(){

        TicketResponse ticketResponse = ticketService.getAllTickets();

        return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
    }

    @Tag(name="Ticket API")
    @Operation(summary = "Get ticket by ticketId")
    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long ticketId){

        TicketDTO ticketDTO = ticketService.getTicketById(ticketId);

        return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
    }

    @Tag(name="Ticket API")
    @Operation(summary = "Get ticket by category")
    @GetMapping("/category/{categoryId}/tickets")
    public ResponseEntity<TicketResponse> getTicketByCategoryId(@PathVariable Long categoryId){

        TicketResponse ticketResponse = ticketService.findTicketByCategoryId(categoryId);

        return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
    }

}
