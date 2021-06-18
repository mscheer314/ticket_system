package com.markscheer.ticket_system.repository;

import com.markscheer.ticket_system.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByTitleContaining(String title);
}