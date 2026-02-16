package com.psem.springBootWithOllama.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private Long answerId;
    private String username;
    private String answer;
    private Long ticketId;

}
