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
        // applied priority from my own perspective.

        String categoryName = ticket.getCategory().getCategoryName();

        switch(categoryName.toLowerCase()){
            case "bug":
                ticket.setPriority("High");
                ticketRepository.save(ticket);
                break;
            case "feature":
                ticket.setPriority("Medium");
                ticketRepository.save(ticket);
                break;
            case "billing":
                ticket.setPriority("High");
                ticketRepository.save(ticket);
                break;
            case "account":
                ticket.setPriority("High");
                ticketRepository.save(ticket);
                break;
            case "other":
                ticket.setPriority("Low");
                ticketRepository.save(ticket);
                break;
            default:
                System.out.println("Something went wrong, while saving a ticket!");
        }

    }
}
