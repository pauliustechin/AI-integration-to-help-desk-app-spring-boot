package com.psem.Spring.boot.with.Ollama.service;

import com.psem.Spring.boot.with.Ollama.model.Ticket;
import com.psem.Spring.boot.with.Ollama.payload.TicketDTO;
import com.psem.Spring.boot.with.Ollama.payload.TicketResponse;

public interface TicketService {
    void createTicket(Ticket ticket);

    TicketResponse getAllTickets();

    TicketDTO getTicketById(Long ticketId);

    TicketDTO findTicketByCategoryId(Long categoryId);
}
