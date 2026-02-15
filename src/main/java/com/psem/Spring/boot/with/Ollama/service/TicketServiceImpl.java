package com.psem.Spring.boot.with.Ollama.service;

import com.psem.Spring.boot.with.Ollama.exception.ResourceNotFoundException;
import com.psem.Spring.boot.with.Ollama.model.Ticket;
import com.psem.Spring.boot.with.Ollama.payload.TicketDTO;
import com.psem.Spring.boot.with.Ollama.payload.TicketResponse;
import com.psem.Spring.boot.with.Ollama.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TicketResponse getAllTickets() {

        List<Ticket> tickets = ticketRepository.findAll();

        List<TicketDTO> ticketDTOS = tickets.stream().map(ticket ->{
                    TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
                    // to prevent from repetitive columns in database, get title from comments table.
                    ticketDTO.setTitle(ticket.getComment().getTitle());
                    return ticketDTO;
                }
                ).toList();

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setContent(ticketDTOS);

        return ticketResponse;
    }

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

    @Override
    public TicketDTO getTicketById(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", ticketId));

        TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
        ticketDTO.setTitle(ticket.getComment().getTitle());

        return ticketDTO;
    }

    @Override
    public TicketDTO findTicketByCategoryId(Long categoryId) {
        return null;
    }
}
