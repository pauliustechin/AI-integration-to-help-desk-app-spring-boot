package com.psem.Spring.boot.with.Ollama.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private String message;
    private String title;
    private String webUrl;

}
