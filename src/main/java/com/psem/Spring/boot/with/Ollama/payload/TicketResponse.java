package com.psem.Spring.boot.with.Ollama.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {

    List<TicketDTO> content;

}
