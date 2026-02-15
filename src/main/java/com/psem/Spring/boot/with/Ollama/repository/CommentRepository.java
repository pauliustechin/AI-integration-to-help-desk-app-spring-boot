package com.psem.Spring.boot.with.Ollama.repository;

import com.psem.Spring.boot.with.Ollama.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
