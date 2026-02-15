package com.psem.Spring.boot.with.Ollama.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    private Long commentId;
    private String message;
    private String title;
    private String webUrl;

}
