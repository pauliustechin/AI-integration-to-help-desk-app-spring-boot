package com.psem.springBootWithOllama.repository;

import com.psem.springBootWithOllama.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
