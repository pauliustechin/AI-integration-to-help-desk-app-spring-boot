package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.exception.ResourceNotFoundException;
import com.psem.springBootWithOllama.model.Answer;
import com.psem.springBootWithOllama.model.Ticket;
import com.psem.springBootWithOllama.payload.AnswerDTO;
import com.psem.springBootWithOllama.repository.AnswerRepository;
import com.psem.springBootWithOllama.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnswerServiceImpl implements AnswerService{

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AnswerDTO addAnswer(Answer answer, Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", ticketId));

        answer.setTicket(ticket);
        Answer savedAnswer = answerRepository.save(answer);

        ticket.setAnswered(true);
        ticketRepository.save(ticket);

        AnswerDTO answerDTO = modelMapper.map(answer, AnswerDTO.class);

        return answerDTO;
    }
}
