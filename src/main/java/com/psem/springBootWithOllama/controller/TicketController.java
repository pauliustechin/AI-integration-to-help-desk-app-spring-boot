package com.psem.springBootWithOllama.controller;

import com.psem.springBootWithOllama.payload.TicketDTO;
import com.psem.springBootWithOllama.payload.TicketResponse;
import com.psem.springBootWithOllama.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public ResponseEntity<TicketResponse> getAllTickets(){

        TicketResponse ticketResponse = ticketService.getAllTickets();

        return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long ticketId){

        TicketDTO ticketDTO = ticketService.getTicketById(ticketId);

        return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/tickets")
    public ResponseEntity<TicketResponse> getTicketByCategoryId(@PathVariable Long categoryId){

        TicketResponse ticketResponse = ticketService.findTicketByCategoryId(categoryId);

        return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
    }

}
