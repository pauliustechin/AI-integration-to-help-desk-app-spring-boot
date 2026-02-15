package com.psem.springBootWithOllama.repository;

import com.psem.springBootWithOllama.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
