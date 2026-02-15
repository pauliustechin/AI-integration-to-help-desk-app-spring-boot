package com.psem.springBootWithOllama.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long commentId;

    private String message;
    private String title;

    @OneToOne(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Ticket ticket;

    public void setTicket(Ticket ticket){
        ticket.setComment(this);
        this.ticket = ticket;
    }


}
