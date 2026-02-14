package com.psem.Spring.boot.with.Ollama.model;

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
    private Long ticketId;

    private String title;

    private String priority;

    private String webUrl;

    private String summary;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

}
