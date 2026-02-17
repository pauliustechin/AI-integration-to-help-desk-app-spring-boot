package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.exception.ResourceNotFoundException;
import com.psem.springBootWithOllama.model.Answer;
import com.psem.springBootWithOllama.model.Ticket;
import com.psem.springBootWithOllama.payload.AnswerDTO;
import com.psem.springBootWithOllama.payload.AnswerResponse;
import com.psem.springBootWithOllama.repository.AnswerRepository;
import com.psem.springBootWithOllama.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



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

        // if answer successfully saved in database, set ticketAnswered to true and save it in database.
        ticket.setAnswered(true);
        ticketRepository.save(ticket);

        AnswerDTO answerDTO = modelMapper.map(answer, AnswerDTO.class);

        // set comment id retrieved from a ticket
        answerDTO.setCommentId(ticket.getComment().getCommentId());

        return answerDTO;
    }

    @Override
    public AnswerResponse getAllAnswers() {

        List<Answer> answers = answerRepository.findAll();

        List<AnswerDTO> answerDTOS = answers.stream().map(answer ->
                modelMapper.map(answer, AnswerDTO.class)
        ).toList();

        // Loop through answerDTOS list to set commentId.
        // Necessary to follow which comment is answered in a front end.
        List<AnswerDTO> updatedAnswerDTOs = answerDTOS.stream().map(answerDTO -> {

            Ticket ticket = ticketRepository.findById(answerDTO.getTicketId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ticket", answerDTO.getTicketId()));

            Long commentId = ticket.getComment().getCommentId();
            answerDTO.setCommentId(commentId);

            return answerDTO;

        }).toList();

        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setContent(updatedAnswerDTOs);

        return answerResponse;
    }
}
