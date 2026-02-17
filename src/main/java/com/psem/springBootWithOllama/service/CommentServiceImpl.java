package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.exception.ResourceNotFoundException;
import com.psem.springBootWithOllama.model.Comment;
import com.psem.springBootWithOllama.model.Ticket;
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
    public CommentResponse getAllComments() {

        List<Comment> comments = commentRepository.findAll();

        List<CommentDTO> commentDTOS= comments.stream().map(comment ->

            modelMapper.map(comment, CommentDTO.class)

        ).toList();

        // Loop through commentDTOs List to check if comment has ticket and if ticket is answered.
        // This is needed in frontend.
        List<CommentDTO> updatedComments = commentDTOS.stream().map(commentDTO -> {
           Ticket ticket = commentDTO.getTicket();
           if(ticket != null && ticket.getAnswered()){
               commentDTO.setAnswered(true);
           }
           else if(ticket != null){
               commentDTO.setAnswered(false);
        // set comment as answered, since it's a statement.
           } else {
               commentDTO.setAnswered(true);
           }

           return commentDTO;

        }).toList();

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(updatedComments);

        return commentResponse;
    }

    @Override
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));

        commentRepository.delete(comment);

    }
}
