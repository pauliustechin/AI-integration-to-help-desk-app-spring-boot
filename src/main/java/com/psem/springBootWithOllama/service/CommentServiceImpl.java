package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.model.Comment;
import com.psem.springBootWithOllama.payload.CommentDTO;
import com.psem.springBootWithOllama.payload.CommentRequest;
import com.psem.springBootWithOllama.payload.CommentResponse;
import com.psem.springBootWithOllama.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public CommentResponse getAllTickets() {

        List<Comment> comments = commentRepository.findAll();

        List<CommentDTO> commentDTOS= comments.stream().map(comment ->
            modelMapper.map(comment, CommentDTO.class)
        ).toList();

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(commentDTOS);

        return commentResponse;
    }
}
