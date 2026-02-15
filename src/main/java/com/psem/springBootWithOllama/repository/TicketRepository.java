package com.psem.springBootWithOllama.repository;

import com.psem.springBootWithOllama.model.Category;
import com.psem.springBootWithOllama.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // jpa naming convention, no need to write sql query
    List<Ticket> findByCategory(Category category);
}
