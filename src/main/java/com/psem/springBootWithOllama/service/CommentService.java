package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.model.Comment;
import com.psem.springBootWithOllama.payload.CommentRequest;
import com.psem.springBootWithOllama.payload.CommentResponse;

public interface CommentService {
    Comment createComment(CommentRequest commentRequest);

    CommentResponse getAllTickets();

    void deleteComment(Long commentId);
}
