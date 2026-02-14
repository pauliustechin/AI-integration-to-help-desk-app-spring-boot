package com.psem.Spring.boot.with.Ollama.service;

import com.psem.Spring.boot.with.Ollama.model.Ticket;
import com.psem.Spring.boot.with.Ollama.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void createTicket(Ticket ticket) {

        // Since priority is subjective question which depends on many factors,
        // applied priority from my perspective.

        String categoryName = ticket.getCategory().getCategoryName();

        switch(categoryName.toLowerCase()){
            case "bug":
                ticket.setPriority("High");
                break;
            case "feature":
                ticket.setPriority("Medium");
                break;
            case "billing":
                ticket.setPriority("High");
                break;
            case "account":
                ticket.setPriority("High");
                break;
            case "other":
                ticket.setPriority("Low");
                break;
            default:
                ticketRepository.save(ticket);
        }

    }
}
