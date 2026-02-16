package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.model.Answer;
import com.psem.springBootWithOllama.payload.AnswerDTO;

public interface AnswerService {
    AnswerDTO addAnswer(Answer answer, Long ticketId);
}
