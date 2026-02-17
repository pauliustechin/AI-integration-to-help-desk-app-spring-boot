package com.psem.springBootWithOllama.controller;

import com.psem.springBootWithOllama.model.Answer;
import com.psem.springBootWithOllama.payload.AnswerDTO;
import com.psem.springBootWithOllama.payload.AnswerResponse;
import com.psem.springBootWithOllama.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnswerController {

    @Autowired
    private AnswerService answerService;


    @PostMapping("/ticket/{ticketId}/answer")
    public ResponseEntity<AnswerDTO> addAnswer(@PathVariable Long ticketId,
                                               @RequestBody Answer answer){

        AnswerDTO answerDTO = answerService.addAnswer(answer, ticketId);

        return new ResponseEntity<>(answerDTO,HttpStatus.OK);
    }

    @GetMapping("/answers")
    public ResponseEntity<AnswerResponse> getAllAnswers(){

        AnswerResponse answerResponse = answerService.getAllAnswers();

        return new ResponseEntity<>(answerResponse,HttpStatus.OK);
    }
}
