package com.psem.Spring.boot.with.Ollama.repository;

import com.psem.Spring.boot.with.Ollama.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
