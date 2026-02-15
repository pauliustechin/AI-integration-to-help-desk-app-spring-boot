package com.psem.Spring.boot.with.Ollama.service;

import com.psem.Spring.boot.with.Ollama.model.Comment;
import com.psem.Spring.boot.with.Ollama.payload.CommentRequest;

public interface CommentService {
    Comment createComment(CommentRequest commentRequest);
}
