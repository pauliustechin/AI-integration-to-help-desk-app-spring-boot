package com.psem.springBootWithOllama.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    private String username;

    private String answer;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
