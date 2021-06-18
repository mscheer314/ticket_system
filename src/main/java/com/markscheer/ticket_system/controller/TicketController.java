package com.markscheer.ticket_system.controller;

import com.markscheer.ticket_system.model.Ticket;
import com.markscheer.ticket_system.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// I don't know what this line does.
//@CrossOrigin(origins = "https://localhost:8081")
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("")
    public ResponseEntity<List<Ticket>> getAllTickets(@RequestParam(required = false) String title) {
        try {
            List<Ticket> tickets = new ArrayList<>();

            if (title == null) {
                tickets.addAll(ticketRepository.findAll());
            } else {
                tickets.addAll(ticketRepository.findByTitleContaining(title));
            }

            if (tickets.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        try {
            Ticket _ticket = ticketRepository
                    .save(new Ticket(ticket.getTitle(), ticket.getDescription(), ticket.getCreatedOn()
                            , ticket.getResolutionDate()));
            return new ResponseEntity<>(_ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}