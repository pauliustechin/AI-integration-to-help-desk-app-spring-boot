package com.psem.Spring.boot.with.Ollama.service;

import com.psem.Spring.boot.with.Ollama.model.Comment;
import com.psem.Spring.boot.with.Ollama.payload.CommentDTO;
import com.psem.Spring.boot.with.Ollama.payload.CommentRequest;
import com.psem.Spring.boot.with.Ollama.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Comment createComment(CommentRequest commentRequest) {

        Comment comment = modelMapper.map(commentRequest, Comment.class);

        return commentRepository.save(comment);
    }
}
