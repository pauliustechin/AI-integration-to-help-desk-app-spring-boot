package com.psem.springBootWithOllama.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_id")
    private Long ticketId;

    private String priority;

    private String webUrl;

    private String summary;

    private Boolean answered;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name="comment_id")
    private Comment comment;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Answer answer;

}
