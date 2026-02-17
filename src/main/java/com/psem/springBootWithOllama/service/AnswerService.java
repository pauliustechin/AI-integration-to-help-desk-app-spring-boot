package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.model.Answer;
import com.psem.springBootWithOllama.payload.AnswerDTO;
import com.psem.springBootWithOllama.payload.AnswerResponse;

public interface AnswerService {
    AnswerDTO addAnswer(Answer answer, Long ticketId);

    AnswerResponse getAllAnswers();
}
