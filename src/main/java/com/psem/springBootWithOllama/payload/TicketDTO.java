package com.psem.springBootWithOllama.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Long ticketId;
    private String title;
    private String priority;
    private String webUrl;
    private String summary;

    private Long categoryId;
    private Long commentId;


}
