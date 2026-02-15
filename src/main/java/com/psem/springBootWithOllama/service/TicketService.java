package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.model.Ticket;
import com.psem.springBootWithOllama.payload.TicketDTO;
import com.psem.springBootWithOllama.payload.TicketResponse;

public interface TicketService {
    void createTicket(Ticket ticket);

    TicketResponse getAllTickets();

    TicketDTO getTicketById(Long ticketId);

    TicketResponse findTicketByCategoryId(Long categoryId);
}
