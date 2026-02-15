package com.psem.Spring.boot.with.Ollama.service;

import com.psem.Spring.boot.with.Ollama.model.Comment;
import com.psem.Spring.boot.with.Ollama.payload.CommentRequest;
import com.psem.Spring.boot.with.Ollama.payload.CommentResponse;

public interface CommentService {
    Comment createComment(CommentRequest commentRequest);

    CommentResponse getAllTickets();
}
