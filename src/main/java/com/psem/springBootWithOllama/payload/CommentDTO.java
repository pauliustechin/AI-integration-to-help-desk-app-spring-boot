package com.psem.springBootWithOllama.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psem.springBootWithOllama.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long commentId;
    private String message;
    private String title;
    @JsonIgnore
    private Ticket ticket;
    private Long ticketId;
    private boolean answered;

}